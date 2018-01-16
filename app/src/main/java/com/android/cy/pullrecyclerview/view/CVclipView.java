package com.android.cy.pullrecyclerview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zhxh on 2018/1/16.
 */

public class CVclipView extends View {

    Rect clip = new Rect();

    public CVclipView(Context context) {
        super(context);
        int[] colors = {0xff000000, 0xffff0000, 0xffffffff};
        Drawable d = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
        setBackgroundDrawable(d);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        invalidate(x, y, x + 10, y + 10);
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(w, w * 4);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.getClipBounds(clip);
        Log.d("CYCYCYCY", "onDraw clip 高: " + clip.height());
    }
}
