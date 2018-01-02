package com.android.cy.pullrecyclerview.view.linescroll;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

import com.android.cy.pullrecyclerview.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhxh on 2018/1/2.
 */

public class CYDKLineChart extends View {
    /**
     * 坐标轴 原点 x点y点
     */
    private PointF pOrigin;
    private PointF pRight;
    private PointF pTop;

    private Bitmap bitmapD;
    private Bitmap bitmapDD;
    private Bitmap bitmapK;

    private float yHeightPerValue;

    private float canvasWight;
    private float canvasHeight;
    private float density;

    private Map<String, KLItemData> dataMap1; //坐标轴里面的点
    private List<Float> yList; // Y轴上点  从小到大排列
    private List<String> xList; // X轴上点


    private List<KLItemData> dataList1;
    private int dataNum;
    private int yNum;

    private Paint paintTextGrey;
    private Paint paintLineCandle;
    private Paint paintLineGrey;
    private Paint paintArea;

    private float minY;
    private float maxY;

    private static final int intervals = 87;
    private float xOffset;
    private float xDayOffset;
    private int xDayNum;

    private Canvas canvas;
    private boolean isAnim;

    private boolean isShowArea;

    private List<String> colorList;

    int lineType;


    //当前触摸的数据索引
    private int currentTouchPos = 0;
    //是否显示光标
    private boolean isShowCursor = false;
    private Paint mPaint = new Paint();
    private float currentCursorX = 0f;

    boolean isShowLastLine = true;
    boolean isFirstOpen = true;

    boolean isShowLineOnly = false;

    private int currentTouchStopIndex = -1;

    public CYDKLineChart(Context context, int lineType) {
        super(context);

        this.lineType = lineType;

        density = CommonDataManager.displayMetrics.density;

        canvasWight = CommonDataManager.screenWight - 30 * density;
        canvasHeight = density * 160;

        paintTextGrey = new Paint();
        paintTextGrey.setAntiAlias(true);
        paintTextGrey.setColor(0xFF8997a5);
        paintTextGrey.setStrokeWidth(2 * density);
        paintTextGrey.setTextSize(10f * density);

        paintLineCandle = new Paint();
        paintLineCandle.setAntiAlias(true);
        paintLineCandle.setColor(0xffff3b30);
        paintLineCandle.setStrokeWidth(1f * density);

        paintLineGrey = new Paint();
        paintLineGrey.setAntiAlias(true);
        paintLineGrey.setColor(Color.parseColor("#dfdfdf"));
        paintLineGrey.setStrokeWidth(0.5f * density);
        paintLineGrey.setPathEffect(new DashPathEffect(new float[]{10, 5}, 0));
        paintLineGrey.setStyle(Paint.Style.STROKE);

        paintArea = new Paint();
        paintArea.setAntiAlias(true);

        /**
         * 初始化数据
         */

        pOrigin = new PointF();
        pRight = new PointF();
        pTop = new PointF();

        if (lineType == 1) {
            pOrigin.set(43 * density, canvasHeight * 0.79f);
            pRight.set(canvasWight - 10 * density, canvasHeight * 0.79f);
            pTop.set(43 * density, canvasHeight * 0.10f);
        } else {
            pOrigin.set(33 * density, canvasHeight * 0.79f);
            pRight.set(canvasWight - 10 * density, canvasHeight * 0.79f);
            pTop.set(33 * density, canvasHeight * 0.10f);
        }

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                isFirstOpen = false;

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    isShowLastLine = false;

                    if (null != getParent())
                        getParent().requestDisallowInterceptTouchEvent(true);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    isShowLastLine = true;

                    if (null != getParent())
                        getParent().requestDisallowInterceptTouchEvent(false);
                }
                return false;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        this.currentTouchPos = getCurrentTouchPosByCoordinate(event.getX());

        if (currentTouchPos < 0)
            currentTouchPos = 0;

        if (currentTouchPos <= dataList1.size() - 1 && currentTouchPos >= 0) {

            isShowCursor = true;
            drawCursor(event.getX());
            //drawRect(event.getX());
        }
        if (currentTouchPos > dataList1.size() - 1) {
            currentTouchPos = dataList1.size() - 1;
        }
        if (MotionEvent.ACTION_UP == event.getAction()
                || MotionEvent.ACTION_CANCEL == event.getAction()) {

            cleanCursor();

        }
        return true;

    }

    private int getCurrentTouchPosByCoordinate(float x) {
        int index = (int) ((x - pOrigin.x) / xOffset);
        return index;
    }

    private void drawCursor(float x) {

        currentCursorX = x;
        invalidate();
    }

    private void cleanCursor() {

        this.currentTouchPos = -1;
        isShowCursor = false;
        invalidate();
    }

    public void setViewData(List<KLItemData> dateList1, boolean isAnim, boolean isShowArea, List<String> colorList) {

        this.isAnim = isAnim;
        this.isShowArea = isShowArea;
        this.dataList1 = dateList1;
        this.colorList = colorList;
        dataNum = dateList1.size();

        xOffset = (pRight.x - pOrigin.x) / dataNum;

        if (dataNum == 0) {
            return;
        }

        dataMap1 = new HashMap<String, KLItemData>();
        yList = new ArrayList<>();
        xList = new ArrayList<>();

        /**
         * "highp": "2187",
         "openp": "1995",
         "lowp": "1988",
         "nowv": "2187",
         */

        for (KLItemData data : dateList1) {

            dataMap1.put(data.getTimes(), data);
        }

        yNum = 5;

        minY = getMinValue(dateList1) * 0.9f;
        maxY = getMaxValue(dateList1) * 1.1f;

        yHeightPerValue = (pOrigin.y - pTop.y) / (maxY - minY);

        for (int i = 0; i < yNum; i++) {
            yList.add(minY + (i) * (maxY - minY) / (yNum - 1));
        }


        xList.add(dateList1.get(dataNum - 1).getTimes().substring(0, 4) + "/" + dateList1.get(dataNum - 1).getTimes().substring(4, 6) + "/" + dateList1.get(dataNum - 1).getTimes().substring(6, 8));
        xList.add(dateList1.get((dataNum - 1) / 2).getTimes().substring(0, 4) + "/" + dateList1.get((dataNum - 1) / 2).getTimes().substring(4, 6) + "/" + dateList1.get((dataNum - 1) / 2).getTimes().substring(6, 8));
        xList.add(dateList1.get(0).getTimes().substring(0, 4) + "/" + dateList1.get(0).getTimes().substring(4, 6) + "/" + dateList1.get(0).getTimes().substring(6, 8));


        xDayNum = xList.size();

        bitmapD = BitmapFactory.decodeResource(getResources(), R.drawable.ic_flag_d);
        bitmapDD = BitmapFactory.decodeResource(getResources(), R.drawable.ic_flag_dd);
        bitmapK = BitmapFactory.decodeResource(getResources(), R.drawable.ic_flag_k);

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (dataNum == 0) {
            return;
        }
        this.canvas = canvas;


        /**
         * 区域
         */

        if (isShowArea) {

            List<String> typeList = new ArrayList<>();

            List<KLItemData> areaIndexList = new ArrayList<>(); //TODO x轴数字

            for (int i = 0; i < dataNum; i++) {

                if (!CommonUtils.isNull(dataList1.get(i).getAreaType())) {
                    if (!isInList(typeList, dataList1.get(i).getAreaType())) {
                        typeList.add(dataList1.get(i).getAreaType());
                    }
                }
            }

            for (int i = 0; i < typeList.size(); i++) {
                int sum = 0;
                int startI = 0;
                String areaType = "";
                int signFlag = 0;
                KLItemData item = new KLItemData();

                for (int j = 0; j < dataNum; j++) {

                    if (typeList.get(i).equals(dataList1.get(j).getAreaType())) {
                        sum = sum + 1;
                        if (startI == 0) {
                            startI = j;
                        }

                        if (0 != dataList1.get(j).getSignFlag()) {
                            signFlag = dataList1.get(j).getSignFlag();
                        }

                        areaType = typeList.get(i);
                    }
                }
                item.setSignFlag(signFlag);
                item.setAreaType(areaType);
                item.setStartI(startI);
                item.setLengthI(sum);
                areaIndexList.add(item);
            }

            for (int i = 0; i < areaIndexList.size(); i++) {

                String showArea = areaIndexList.get(i).getAreaType().replace("区间", "");

                if (1 == areaIndexList.get(i).getSignFlag()) {
                    paintArea.setColor(Color.parseColor("#ffe4e4"));
                } else if (-1 == areaIndexList.get(i).getSignFlag()) {
                    paintArea.setColor(Color.parseColor("#ddf3e4"));
                } else {
                    paintArea.setColor(Color.parseColor("#ddf3e4"));
                }

                if (areaIndexList.size() >= colorList.size()) {

                    if ("D".equals(colorList.get(i))) {
                        paintArea.setColor(Color.parseColor("#ffe4e4"));
                    } else if ("K".equals(colorList.get(i))) {
                        paintArea.setColor(Color.parseColor("#ddf3e4"));
                    } else {
                        paintArea.setColor(Color.parseColor("#ddf3e4"));
                    }

                }

                paintArea.setStrokeWidth(areaIndexList.get(i).getLengthI() * xOffset + 2 * density);

//                canvas.drawLine(pOrigin.x + (areaIndexList.get(i).getStartI() + areaIndexList.get(i).getLengthI() / 2) * xOffset,
//                        pTop.y
//                        , pOrigin.x + (areaIndexList.get(i).getStartI() + areaIndexList.get(i).getLengthI() / 2) * xOffset,
//                        pOrigin.y
//                        , paintArea);

                canvas.drawRect(
                        pOrigin.x + areaIndexList.get(i).getStartI() * xOffset
                        , pTop.y
                        , pOrigin.x + (areaIndexList.get(i).getStartI() + areaIndexList.get(i).getLengthI()) * xOffset
                        , pOrigin.y
                        , paintArea

                );

                canvas.drawText(showArea, pOrigin.x + (areaIndexList.get(i).getStartI() + areaIndexList.get(i).getLengthI() / 2) * xOffset - 3 * density, pOrigin.y + 12 * density, paintTextGrey);

            }

        }

        /**
         * 画横线 和文字 从下往上
         */
        float yOffset = (pOrigin.y - pTop.y) / (yNum - 1);
        Path path = new Path();

        for (int i = 0; i < yNum; i++) {

            path.reset();
            path.moveTo(pOrigin.x, pOrigin.y - i * yOffset);
            path.lineTo(pRight.x, pRight.y - i * yOffset);
            canvas.drawPath(path, paintLineGrey);

            canvas.drawText(String.format("%.2f", yList.get(i) / 100),
                    pOrigin.x - 33 * density,
                    pOrigin.y - i * yOffset + 3 * density,
                    paintTextGrey);
        }


        /**
         * 画x轴下面的日期 从右向左
         */
        xDayOffset = (pRight.x - pOrigin.x) / (4 - 1) - 5 * density;

        for (int i = 0; i < xDayNum; i++) {

            String strData = xList.get(xDayNum - 1 - i);

            if (i == 2) {
                canvas.drawText(strData,
                        pRight.x - paintTextGrey.measureText(strData),
                        pOrigin.y + 33 * density,
                        paintTextGrey);

                path.reset();
                path.moveTo(pRight.x, pOrigin.y);
                path.lineTo(pRight.x, pTop.y);
                //canvas.drawPath(path, paintLineGrey);

            } else if (i == 1) {
                canvas.drawText(strData,
                        (pOrigin.x + pRight.x) / 2 - paintTextGrey.measureText(strData) / 2,
                        pOrigin.y + 33 * density,
                        paintTextGrey);

                path.reset();
                path.moveTo((pOrigin.x + pRight.x) / 2, pOrigin.y);
                path.lineTo((pOrigin.x + pRight.x) / 2, pTop.y);
                canvas.drawPath(path, paintLineGrey);

            } else if (i == 0) {
                canvas.drawText(strData,
                        pOrigin.x,
                        pOrigin.y + 33 * density,
                        paintTextGrey);

                path.reset();
                path.moveTo(pOrigin.x, pOrigin.y);
                path.lineTo(pOrigin.x, pTop.y);
                //canvas.drawPath(path, paintLineGrey);

            }

        }


        /*************************************************************************/
        /**
         * 点 线
         */

        if (isAnim) {
            for (int i = 0; i < progress; i++) {
                drawItemData(dataMap1, dataList1, i, paintLineCandle);
            }
            getHandler().postDelayed(runnable, intervals);

        } else {
            for (int i = 0; i < dataNum; i++) {
                drawItemData(dataMap1, dataList1, i, paintLineCandle);
            }
        }


        /************************************/
        //触摸时
        if (isShowCursor) {

            KLItemData currentStock;

            if (currentTouchPos <= dataList1.size() - 1) {
                currentStock = dataList1.get(currentTouchPos);
            } else {
                currentStock = dataList1.get(dataList1.size() - 1);
            }

            if (currentStock.getSignFlag() == 1 || currentStock.getSignFlag() == -1) {
                //画光标
                currentTouchStopIndex = currentTouchPos;

                isShowLineOnly = false;
                drawCursorText(currentCursorX, currentStock);
            } else {
                isShowLineOnly = true;
                drawCursorText(currentCursorX, currentStock);

            }
        }
    }


    private void drawCursorText(float currentCursorX, KLItemData currentStock) {
        Path path = new Path();

        mPaint.setStrokeWidth(1f * density);
        mPaint.setPathEffect(new DashPathEffect(new float[]{10, 5}, 0));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xff8997A5);

        float lineCursorX = currentCursorX;

        if (lineCursorX < pOrigin.x) {
            lineCursorX = pOrigin.x;
        } else if (lineCursorX > pRight.x) {
            lineCursorX = pRight.x;
        }

        path.reset();
        path.moveTo(lineCursorX, getDataYvalue(maxY));
        path.lineTo(lineCursorX + 1, getDataYvalue(minY));
        canvas.drawPath(path, mPaint);


        if (isShowLineOnly) {
            return;
        }

        mPaint.reset();
        mPaint.setColor(0xFF458CF5);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(9 * density);
        mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        String timeStr = stampToDate(currentStock.getTimes()).replace(" 00:00", "");
        String priceStr = String.valueOf(Float.parseFloat(currentStock.getNowv()) / 100);

        String text = timeStr + " 价格" + priceStr;

        float textCursorX = currentCursorX - mPaint.measureText(text) / 2;

        if (textCursorX < pOrigin.x) {
            textCursorX = pOrigin.x;
        } else if (textCursorX > pRight.x - mPaint.measureText(text)) {
            textCursorX = pRight.x - mPaint.measureText(text);
        }

        canvas.drawText(text, textCursorX, 9 * density, mPaint);
    }

    private int progress = 0;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (progress == dataNum) {
                return;
            }
            progress++;
            invalidate();
        }
    };


    private float getDataYvalue(float dateProfit) {

        float yValue = pOrigin.y - yHeightPerValue * (dateProfit - minY);

        return yValue;
    }

    public float getMaxValue(List<KLItemData> dataList) {

        float maxValue = Float.parseFloat(dataList.get(0).getHighp());

        for (KLItemData data : dataList) {

            if (Float.parseFloat(data.getHighp()) >= maxValue) {
                maxValue = Float.parseFloat(data.getHighp());
            }

        }
        return maxValue;

    }

    public float getMinValue(List<KLItemData> dataList) {

        float minValue = Float.parseFloat(dataList.get(0).getLowp());

        for (KLItemData data : dataList) {

            if (Float.parseFloat(data.getLowp()) <= minValue) {
                minValue = Float.parseFloat(data.getLowp());
            }
        }

        return minValue;

    }

    private void drawItemData(Map<String, KLItemData> dataMap, List<KLItemData> dataList, int i, Paint paintLine) {

        if (pOrigin.x + i * xOffset > pRight.x) {
            return;
        }

        if (Float.parseFloat(dataList.get(i).getOpenp()) < Float.parseFloat(dataList.get(i).getNowv())) {
            //涨
            paintLine.setColor(0xFFff4c51);
        } else if (Float.parseFloat(dataList.get(i).getOpenp()) > Float.parseFloat(dataList.get(i).getNowv())) {
            //跌
            paintLine.setColor(0xFF22bb71);
        } else if (Float.parseFloat(dataList.get(i).getOpenp())
                == Float.parseFloat(dataList.get(i).getNowv())
                && Float.parseFloat(dataList.get(i).getHighp())
                == Float.parseFloat(dataList.get(i).getLowp())
                && Float.parseFloat(dataList.get(i).getNowv())
                > Float.parseFloat(dataList.get(i).getPreclose())) {
            //涨停
            paintLine.setColor(0xFFff4c51);
        } else if (Float.parseFloat(dataList.get(i).getOpenp())
                == Float.parseFloat(dataList.get(i).getNowv())
                && Float.parseFloat(dataList.get(i).getHighp())
                == Float.parseFloat(dataList.get(i).getLowp())
                && Float.parseFloat(dataList.get(i).getNowv())
                < Float.parseFloat(dataList.get(i).getPreclose())) {
            //跌停
            paintLine.setColor(0xFF22bb71);
        } else if (Float.parseFloat(dataList.get(i).getOpenp())
                == Float.parseFloat(dataList.get(i).getNowv())
                && Float.parseFloat(dataList.get(i).getHighp())
                == Float.parseFloat(dataList.get(i).getLowp())
                && Float.parseFloat(dataList.get(i).getNowv())
                == Float.parseFloat(dataList.get(i).getPreclose())) {
            //停牌
            paintLine.setColor(0xFF999999);
        } else {
            paintLine.setColor(0xFFff4c51);
        }

        paintLine.setStrokeWidth(0.5f * density);
        canvas.drawLine(pOrigin.x + i * xOffset,
                getDataYvalue(Float.parseFloat(dataMap.get(dataList.get(i).getTimes()).getHighp()))
                , pOrigin.x + i * xOffset,
                getDataYvalue(Float.parseFloat(dataMap.get(dataList.get(i).getTimes()).getLowp()))
                , paintLine);

        paintLine.setStrokeWidth(2f * density);
        canvas.drawLine(pOrigin.x + i * xOffset,
                getDataYvalue(Float.parseFloat(dataMap.get(dataList.get(i).getTimes()).getOpenp()))
                , pOrigin.x + i * xOffset,
                getDataYvalue(Float.parseFloat(dataMap.get(dataList.get(i).getTimes()).getNowv()) + 0.5f * density)
                , paintLine);


        if (-1 == dataList.get(i).getSignFlag()) {

            canvas.drawBitmap(bitmapK,
                    pOrigin.x + i * xOffset - bitmapK.getWidth() / 2 + 0.5f * density,
                    getDataYvalue(Float.parseFloat(dataMap.get(dataList.get(i).getTimes()).getHighp())) - bitmapK.getHeight() / 2 - bitmapK.getHeight() / 2 - 3 * density,
                    paintLine);

        } else if (1 == dataList.get(i).getSignFlag()) {

            if (dataList.get(i).isResistance()) {

                if (isFirstOpen) {
                    currentTouchStopIndex = i;
                }

                canvas.drawBitmap(bitmapDD,
                        pOrigin.x + i * xOffset - bitmapDD.getWidth() / 2 + 0.5f * density,
                        getDataYvalue(Float.parseFloat(dataMap.get(dataList.get(i).getTimes()).getLowp())) + bitmapDD.getHeight() / 2 - 3 * density,
                        paintLine);
            } else {
                canvas.drawBitmap(bitmapD,
                        pOrigin.x + i * xOffset - bitmapD.getWidth() / 2 + 0.5f * density,
                        getDataYvalue(Float.parseFloat(dataMap.get(dataList.get(i).getTimes()).getLowp())) + bitmapK.getHeight() / 2 - 3 * density,
                        paintLine);
            }

        }

        if (currentTouchStopIndex == i) {
            if (isShowLastLine) {
                isShowLineOnly = false;
                drawCursorText(pOrigin.x + i * xOffset, dataList.get(i));
            }
        }
    }

    boolean isInList(List<String> list, String item) {

        for (String data : list
                ) {
            if (data.equals(item)) {
                return true;
            }
        }

        return false;
    }

    public static String stampToDate(String dateStr) {
        return DateTime.getInstance(dateStr).getYear() + "/" + DateTime.getInstance(dateStr).getMonthWith2Numbers() + "/" + DateTime.getInstance(dateStr).getDayWith2Numbers()
                + " " + CommonUtils.timeRepairZero(DateTime.getInstance(dateStr).getHour()) + ":" + CommonUtils.timeRepairZero(DateTime.getInstance(dateStr).getMinute());
    }
}
