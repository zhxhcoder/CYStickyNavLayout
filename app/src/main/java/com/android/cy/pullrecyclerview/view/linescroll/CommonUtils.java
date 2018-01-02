package com.android.cy.pullrecyclerview.view.linescroll;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.ClipboardManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhxh on 2018/1/2.
 */

public class CommonUtils {

    /**
     * 判断字符串是否为空
     *
     * @param checkStr 被验证的字符串
     * @return boolean 如果为空,返回true,否则,返回false
     */
    public static boolean isNull(String checkStr) {

        boolean result = false;

        if (null == checkStr) {

            result = true;
        } else {
            if (checkStr.length() == 0) {

                result = true;
            }
        }
        return result;
    }

    public static boolean isNull(List<?> list) {

        boolean result = false;

        if (null == list) {

            result = true;
        } else {
            if (list.size() == 0) {

                result = true;
            }
        }
        return result;
    }

    public static String getStr(String checkStr) {

        String result;

        if (null == checkStr) {

            result = "";
        } else {

            result = checkStr;
        }

        return result;
    }

    /**
     * 判断字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNum(String str) {
        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }

    /**
     * 将字节数组转换为UTF-8字符串
     *
     * @param source 字节数据源
     * @return String UTF-8字符串
     * @throws UnsupportedEncodingException
     */
    public static String toUTF8Str(byte[] source) throws Exception {

        if (source != null) {

            try {

                return new String(source, "UTF-8");

            } catch (UnsupportedEncodingException e) {

                throw new Exception("Unsupported UTF8 Encoding Exception" + e);

            }

        }

        return null;

    }

    /***
     * 获取URLEncoder值
     * @param value
     * @return
     */
    public static String toUTF8Str(String value) {

        String tempValue = "";

        if (null == value)
            return tempValue;

        try {

            tempValue = URLEncoder.encode(value, "utf-8");

        } catch (Exception ex) {

            tempValue = "";
        }

        return tempValue;
    }

    public static String toStr(byte[] source, String encoding) throws Exception {

        if (source != null) {

            try {

                return new String(source, encoding);

            } catch (UnsupportedEncodingException e) {

                throw new Exception("Unsupported Encoding Exception" + e);

            }

        }

        return null;

    }

    /**
     * 根据给定规则拆分字符串
     *
     * @param source 待拆分字符串
     * @param split  拆分规则
     */
    public static String[] split(String source, String split) {

        if (isNull(source))
            return null;

        if (isNull(split))
            return new String[]{source};

        Vector<String> vector = new Vector<String>();

        int startIndex = 0;

        int endIndex = -1;

        while (true) {

            if ((endIndex = source.indexOf(split, startIndex)) != -1) {

                vector.add(source.substring(startIndex, endIndex));

                startIndex = endIndex + split.length();

            } else {

                if (startIndex <= source.length()) {

                    vector.add(source.substring(startIndex));

                }

                break;
            }

        }

        return vector.toArray(new String[vector.size()]);
    }

    public static String getLastTime() {

        SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");

        long time = System.currentTimeMillis();

        if (0 == time)
            return "";

        String text = mDateFormat.format(new Date(time));

        mDateFormat = null;

        return text;
    }

    /***
     * 保留两位小数
     * @param value
     * @return
     */
    public static String getDecimal(float value) {

        String result = "";

        try {

            DecimalFormat decimal = new DecimalFormat("##0.00");
            result = decimal.format(value);

        } catch (Exception ex) {
            ex.printStackTrace();
            result = "";
        }

        return result;
    }

    /***
     * 保留两位小数
     * @param value
     * @return
     */
    public static String getDecimal(double value) {

        String result = "";

        try {

            DecimalFormat decimal = new DecimalFormat("##0.00");
            result = decimal.format(value);

        } catch (Exception ex) {
            ex.printStackTrace();
            result = "";
        }

        return result;
    }

    /***
     * 保留两位小数
     * @param value
     * @return
     */
    public static String getDecimal(String value) {

        String result = "";

        try {

            double tempValue = Double.parseDouble(value);

            DecimalFormat decimal = new DecimalFormat("##0.00");
            result = decimal.format(tempValue);

        } catch (Exception ex) {
            ex.printStackTrace();
            result = "";
        }

        return result;
    }

    /**
     * 保留n位小数
     *
     * @param value
     * @param number 几位
     * @return
     */
    public static String getNDecimal(String value, int number) {

        String result = "";

        try {

            double tempValue = Double.parseDouble(value);
//			"##0.000"三位
//			Log.i("test", "number"+number);
            String orignNum = "##0.0";
            for (int i = 1; i < number; i++) {

                orignNum += "0";
            }
//			Log.i("test", "orignNum"+orignNum);
            DecimalFormat decimal = new DecimalFormat(orignNum);
            result = decimal.format(tempValue);

        } catch (Exception ex) {
//			Log.i("test", "exception");
            ex.printStackTrace();
            result = "";
        }

        return result;
    }

    /**
     * 是否包含特殊字符
     *
     * @param str
     * @return
     */
    public static boolean isSpechars(String str) {

        // 只允许字母和数字
        // String regEx = "[^a-zA-Z0-9]";

        // 清除掉所有特殊字符
        String regEx = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);

        return matcher.matches();
    }

    /***
     * 是否是数字字符
     * @param str
     * @return
     */
    public static boolean isNumChar(String str) {

        String regEx = "[^a-zA-Z0-9]";

        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);

        return matcher.matches();
    }

    /***
     * 空格
     * @param str
     * @return
     */
    public static boolean isSpace(String str) {

        String regEx = "\\s";

        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);

        return matcher.find();
    }

    public static boolean isMobileNO(String mobiles) {

//        Pattern p = Pattern
//                .compile("^((13[0-9])|(15[^4,//D])|(18[0,5-9]))//d{8}$");
//        Matcher m = p.matcher(mobiles);
//
//        return m.matches();

        if (CommonUtils.isNull(mobiles))
            return false;

        if (mobiles.length() == 11) {

            return true;
        }

        return false;
    }

    public static String getImgUrl(String accountId) {

        StringBuffer sb = new StringBuffer();

        try {

            sb.append("http://www.niuguwang.com/SaveYieldPhoto/");
            sb.append(accountId);
            sb.append(".png");
            sb.append("?");
            sb.append("i=");
            sb.append(System.currentTimeMillis());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return sb.toString();
    }

    public static String ToDBC(String input) {

        if (isNull(input)) {
            return input;
        }

        char[] c = input.toCharArray();

        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }

        return new String(c);
    }

    public static String StringFilter(String str) {

        Matcher matcher = null;

        try {

            str = str.replaceAll("【", "[").replaceAll("】", "]")
                    .replaceAll("！", "!");// 替换中文标号
            String regEx = "[『』]"; // 清除掉特殊字符
            Pattern p = Pattern.compile(regEx);
            matcher = p.matcher(str);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return matcher.replaceAll("").trim();
    }

    /***
     * 时间
     * @return
     */
    public static String getTimeStr() {

        try {

            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");

            Date date = new Date();

            String dateStr = df.format(date);

            date = null;
            df = null;

            return dateStr;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "";
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getCurrentDate() {

        SimpleDateFormat mDateFormat = new SimpleDateFormat("yy-MM-dd");

        long time = System.currentTimeMillis();

        String text = mDateFormat.format(new Date(time));

        mDateFormat = null;

        return text;
    }

    /**
     * 月日
     *
     * @param dateStr
     * @return
     */
    public static String getFormatData(String dateStr) {

        return DateTime.getInstance(dateStr).getMonthWith2Numbers() + "-" + DateTime.getInstance(dateStr).getDayWith2Numbers();
    }

    /**
     * 年月日
     *
     * @param dateStr
     * @return
     */
    public static String getFormatData1(String dateStr) {

        return DateTime.getInstance(dateStr).getYear() + "-" + DateTime.getInstance(dateStr).getMonthWith2Numbers() + "-" + DateTime.getInstance(dateStr).getDayWith2Numbers();
    }

    /**
     * 实现文本复制功能
     *
     * @param content
     */
    public static void copy(String content, Context context) {

        if (CommonUtils.isNull(content))
            return;

        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }

    /**
     * 实现粘贴功能
     *
     * @param context
     * @return
     */
    public static String paste(Context context) {

        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        return cmb.getText().toString().trim();
    }

    /***
     * 获取sdk版本
     * @return
     */
    public static int getSDKVersion() {

        int version = 0;

        try {

            version = android.os.Build.VERSION.SDK_INT;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return version;
    }

    /***
     * 显示键盘
     * @param context
     * @param editText
     */
    public static void showInputManager(Context context, EditText editText) {

        try {

            InputMethodManager imm = (InputMethodManager) context.getSystemService
                    (Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /***
     * 隐藏键盘
     * @param context
     * @param editText
     */
    public static void hideInputManager(Context context, EditText editText) {

        try {

            InputMethodManager imm = (InputMethodManager) context.getSystemService
                    (Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String base64Str(String value) {

        String tempValue = value;

        try {

            tempValue = Base64.encodeToString(value.getBytes(), 0);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return tempValue;
    }

    /***
     * 生成UUID 当做imei无效时使用
     * @return
     */
    public static String buildUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    /**
     * 除法
     *
     * @param v1
     * @param v2
     * @param scale
     * @return
     */
    public static double divide(double v1, double v2, int scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 除法,指定取整模式
     *
     * @param v1
     * @param v2
     * @param scale
     * @return
     * @see RoundingMode
     */
    public static double divide(double v1, double v2, int scale, int roundMode) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, roundMode).doubleValue();
    }

    /**
     * 乘法,指定精度和取整模式
     *
     * @param v1
     * @param v2
     * @param scale
     * @return
     * @see RoundingMode
     */
    public static double multiply(double v1, double v2, int scale, int roundMode) {

        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));

        return b1.multiply(b2).setScale(scale, roundMode).doubleValue();
    }

    /**
     * 乘法
     *
     * @param v1
     * @param v2
     * @return
     */
    public static double multiply(double v1, double v2) {

        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));

        return b1.multiply(b2).doubleValue();
    }

    /***
     * 加法
     * @param v1
     * @param v2
     * @return
     */
    public static Double add(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return Double.valueOf(b1.add(b2).doubleValue());
    }

    /**
     * * 两个Double数相减 *
     *
     * @param v1
     * @param v2
     * @return Double
     */
    public static Double sub(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));

        return b1.subtract(b2).doubleValue();
    }

    /**
     * 比较日期大小
     *
     * @param startDate
     * @param endDate
     * @return true 起始日期小于截止日期 false 起始日期大于截止日期
     */
    public static boolean compareDate(String startDate, String endDate) {

        try {
            startDate = getFormatDate(startDate);
            endDate = getFormatDate(endDate);

            if (java.sql.Date.valueOf(startDate).after(java.sql.Date.valueOf(endDate))) {
                //起始日期大于结束日期
                return false;
            } else if (java.sql.Date.valueOf(startDate).equals(java.sql.Date.valueOf(endDate))) {

                return true;

            } else if (java.sql.Date.valueOf(startDate).before(java.sql.Date.valueOf(endDate))) {

                return true;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return true;
    }

    private static String getFormatDate(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        formatter.setLenient(false);
        Date newDate = formatter.parse(date);
        formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return formatter.format(newDate);
    }


    private static Uri newPictureUri(String path) {
        return Uri.fromFile(new File(path));
    }

    private static boolean isFileExists(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        File f = new File(path);
        if (!f.exists()) {
            return false;
        }
        return true;
    }

    /**
     * 根据时间字符串返回该时间是否为今天、昨天、前天
     * 否则原样返回
     *
     * @param dateStr
     * @return
     */
    public static String getDateAlias(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(dateStr);

            long intervalMilli = date.getTime() - new Date().getTime();
            int xcts = (int) (intervalMilli / (24 * 60 * 60 * 1000));
            switch (xcts) {
                case 0:
                    return "今天";
                case -1:
                    return "昨天";
                case -2:
                    return "前天";
                default:
                    break;
            }
            return dateStr;

        } catch (ParseException e) {
            return dateStr;
        }
    }

    /**
     * 隐藏部分手机号
     *
     * @param mobile
     * @param tv_mpbile
     */
    public static void hidePartMobile(String mobile, TextView tv_mpbile) {

        if (null != mobile && mobile.length() >= 11) {
            String startNum = mobile.substring(0, 3);
            String endNUm = mobile.substring(mobile.length() - 4, mobile.length());

            mobile = startNum + "****" + endNUm;
            tv_mpbile.setText(mobile);
        }
    }

    /**
     * 隐藏部分手机号
     *
     * @param mobile
     * @param tv_mpbile
     * @param headerText 前缀文本
     */
    public static void hidePartMobile(String mobile, TextView tv_mpbile, String headerText) {

        if (null != mobile && mobile.length() >= 11) {
            String startNum = mobile.substring(0, 3);
            String endNUm = mobile.substring(mobile.length() - 4, mobile.length());

            mobile = startNum + "****" + endNUm;
            tv_mpbile.setText(headerText + mobile);
        }
    }

    /**
     * 隐藏部分手机号
     *
     * @param mobile
     */
    public static String hidePartMobile(String mobile) {

        if (null != mobile && mobile.length() >= 11) {
            String startNum = mobile.substring(0, 3);
            String endNUm = mobile.substring(mobile.length() - 4, mobile.length());

            mobile = startNum + "****" + endNUm;
            return mobile;
        }
        return "";
    }

    /**
     * @param spString
     * @param pString
     * @return SpannableString    返回类型
     * @Title: spannableString
     * @Description: 字符串加高亮
     */
    public static SpannableString spannableString(String spString, String pString, int color) {
        // 创建一个 SpannableString对象
        SpannableString sp = new SpannableString(spString);
        Pattern p = Pattern.compile(pString);
        Matcher m = p.matcher(sp);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            // 设置高亮样式
            sp.setSpan(new ForegroundColorSpan(color), start, end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return sp;
    }

    public static List<String> getRegEx(String input, String regex) {
        List<String> stringList = new ArrayList<>();
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        while (m.find())
            stringList.add(m.group());

        return stringList;
    }

    public static boolean isPhone(String input) {

        String regex = "^1(3|4|5|7|8)[0-9]\\d{8}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);

        return m.find();
    }

    public static boolean isPattern(String input, String regex) {

        if (isNull(input) || isNull(regex)) {
            return false;
        }

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);

        return m.find();
    }


    /**
     * 调用拨号界面
     *
     * @param phone 电话号码
     */
    public static void call(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 将时间转化成月-日 时-分
     */
    public static String stampToDate(String dateStr) {
        return DateTime.getInstance(dateStr).getMonthWith2Numbers() + "-" + DateTime.getInstance(dateStr).getDayWith2Numbers()
                + " " + timeRepairZero(DateTime.getInstance(dateStr).getHour()) + ":" + timeRepairZero(DateTime.getInstance(dateStr).getMinute());
    }

    /**
     * 将时间转化成年月日
     */
    public static String stampToDate2(String dateStr) {
        return DateTime.getInstance(dateStr).getYear() + "-" + DateTime.getInstance(dateStr).getMonth()
                + "-" + DateTime.getInstance(dateStr).getDay();
    }

    /**
     * 将时间转化成年-月-日 时-分
     */
    public static String stampToDate3(String dateStr) {
        return DateTime.getInstance(dateStr).getYear() + "-" + DateTime.getInstance(dateStr).getMonthWith2Numbers() + "-" + DateTime.getInstance(dateStr).getDayWith2Numbers()
                + " " + timeRepairZero(DateTime.getInstance(dateStr).getHour()) + ":" + timeRepairZero(DateTime.getInstance(dateStr).getMinute());
    }

    //时间补0
    public static String timeRepairZero(int time) {
        String timeVlue = "";
        if (time < 10) {
            timeVlue = "0" + time;
        } else {
            timeVlue = time + "";
        }

        return timeVlue;
    }

    /*
     * 获取控件宽
     */
    public static int getWidth(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return (view.getMeasuredWidth());
    }

    /*
     * 获取控件高
     */
    public static int getHeight(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return (view.getMeasuredHeight());
    }

    /*
     * 设置控件所在的位置X，并且不改变宽高，
     * X为绝对位置，此时Y可能归0
     */
    public static void setLayoutX(View view, int x) {
        ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        margin.setMargins(x, margin.topMargin, x + margin.width, margin.bottomMargin);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
        view.setLayoutParams(layoutParams);
    }

    /*
     * 设置控件所在的位置Y，并且不改变宽高，
     * Y为绝对位置，此时X可能归0
     */
    public static void setLayoutY(View view, int y) {
        ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        margin.setMargins(margin.leftMargin, y, margin.rightMargin, margin.height);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
        view.setLayoutParams(layoutParams);
    }

    /*
     * 设置控件所在的位置XY，并且不改变宽高，
     * XY为绝对位置
     */
    public static void setLayout(View view, int x, int y) {
        ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        margin.setMargins(x, y, x + margin.width, y + margin.height);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
        view.setLayoutParams(layoutParams);
    }

    //获取状态栏高度
    public static int getstatusBarHeight(Context context) {
        //获取状态栏高度
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }

    /**
     * 计算单位，k：1000，m:100万
     */
    public static String transformKAndM(String value) {
        try {
            if (value.contains("K")) {
                value = value.replace("K", "");
                double valueDouble = Double.parseDouble(value) * 1000;
                return Integer.toString((int) valueDouble);
            } else if (value.contains("M")) {
                value = value.replace("M", "");
                double valueDouble = Double.parseDouble(value) * 1000000;
                return Integer.toString((int) valueDouble);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return value;
        }
        return value;
    }

    //判断是否装了微信
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    //打开微信
    public static void openWewixinApk(Context context) {
        Intent intent = new Intent();
        ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(cmp);
        context.startActivity(intent);
    }

    public static void copyToPaste(Context context, String info) {
        android.content.ClipboardManager cm = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(info);
    }
}
