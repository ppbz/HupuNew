package com.sjtu.hupu.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.sjtu.hupu.R;

public class MyView extends View {
    private Context context;
    private float height;
    private float width;

    private Paint strokeAccentPaint = new Paint();

    public MyView(Context context) {
        super(context);
        this.context = context;
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = (float)h;
        width = (float)w;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(context, R.color.colorAccent));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        canvas.drawColor(ContextCompat.getColor(context,R.color.colorPrimary));
        canvas.drawCircle(300,200,100,paint);
        canvas.drawCircle(width - 300,200,100,paint);
        canvas.drawRect(10,10,width-10,height-180,paint);

        Path path = new Path();
        path.moveTo(width/2 - 100f,height/2 - 100f);
        path.lineTo(width/2 + 100f,height/2 + 100f);
        path.lineTo(width/2,height/2 + 100f);
        //path.close();
        canvas.drawPath(path,paint);

        path.moveTo(300,800);
        RectF rectF = new RectF(300,800,width-300,height-300);
        path.arcTo(rectF,0,180,true);
        canvas.drawPath(path,paint);




    }
}
