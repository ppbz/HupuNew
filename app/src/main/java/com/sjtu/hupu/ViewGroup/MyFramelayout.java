package com.sjtu.hupu.ViewGroup;

import android.widget.FrameLayout;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sjtu.hupu.bean.LineBean;

import java.util.ArrayList;

public class MyFramelayout extends FrameLayout {
    private final int screenWidth;
    private final int screenHeight;
    private Paint paint;
    private int lastX;
    private int lastY;

    private float startX;
    private float startY;
    private float stopX;
    private float stopY;
    public boolean isDraw;
    public ArrayList<LineBean> arrayListLines;

    public MyFramelayout(@NonNull Context context) {
        this(context, null);
    }

    public MyFramelayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyFramelayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        DisplayMetrics dis = this.getResources().getDisplayMetrics();
        screenWidth = dis.widthPixels;
        screenHeight = dis.heightPixels;
        initPaint();


        arrayListLines = new ArrayList<>();
        setWillNotDraw(false);
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        for (int i = 0; i < arrayListLines.size(); i++) {
            LineBean lineBean = arrayListLines.get(i);
            int lines = lineBean.line;
            if (lines == 1) {
                canvas.drawLine((lineBean.imageView1.getLeft() + lineBean.imageView1.getWidth() / 2),
                        (lineBean.imageView1.getTop() + lineBean.imageView1.getHeight() / 2),
                        (lineBean.imageView.getLeft() + lineBean.imageView.getWidth() / 2),
                        (lineBean.imageView.getTop() + lineBean.imageView.getHeight() / 2), paint);
            } else {

                for (int j = 0; j < lineBean.line; j++) {

                    canvas.drawLine((lineBean.imageView1.getLeft() + lineBean.imageView1.getWidth() * (j+1) / (lines+1)),
                            (lineBean.imageView1.getTop() + lineBean.imageView1.getHeight() * (j+1  ) / (lines+1)),
                            (lineBean.imageView.getLeft() + lineBean.imageView.getWidth() * (j+1 ) / (lines+1)),
                            (lineBean.imageView.getTop() + lineBean.imageView.getHeight() * (j+1  ) / (lines+1)), paint);
                }

            }

        }
        if (isDraw) {
            canvas.drawLine(startX, startY, stopX, stopY, paint);
        /*    canvas.drawCircle(startX, startY, 10f, paint);
            canvas.drawCircle(stopX, stopY, 10f, paint);*/
            canvas.restore();
        }

    }

    public void drawLine(int startX1, int startY1, int stopX1, int stopY1) {
        startX = startX1;
        startY = startY1;
        stopX = stopX1;
        stopY = stopY1;
        invalidate();
    }


    public void drawLine(float startX1, float startY1, float stopX1, float stopY1) {
        startX = startX1;
        startY = startY1;
        stopX = stopX1;
        stopY = stopY1;
        invalidate();
    }
}
