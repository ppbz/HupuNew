package com.sjtu.hupu.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.sjtu.hupu.util.Utils;

public class DashBoardView  extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final float OPEN_ANGLE = 120f;


    public DashBoardView(Context context) {
        super(context);
        initView();
    }

    public DashBoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DashBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        paint.setStrokeWidth(Utils.dp2px(3f));
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画弧
        canvas.drawArc(getWidth() / 2f - Utils.dp2px(150f), getHeight() / 2f - Utils.dp2px(150f),
                getWidth() / 2f + Utils.dp2px(150f), getHeight() / 2f + Utils.dp2px(150f),
                90f + OPEN_ANGLE / 2f, 360f - OPEN_ANGLE,false,paint);
    }
}
