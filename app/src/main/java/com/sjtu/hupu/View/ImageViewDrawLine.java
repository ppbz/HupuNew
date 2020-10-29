package com.sjtu.hupu.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.sjtu.hupu.ViewGroup.MyFramelayout;
import com.sjtu.hupu.bean.ImageViewBean;
import com.sjtu.hupu.bean.LineBean;

import java.util.ArrayList;

public class ImageViewDrawLine extends AppCompatImageView {
    private final int screenWidth;
    private final int screenHeight;
    private Paint paint;
    private int lastX;
    private int lastY;
    public ArrayList<ImageViewBean> imageViewBeans;

    private float startX;
    private float startY;
    private float stopX;
    private float stopY;
    private boolean isLian;
    private MyFramelayout fl;
    private LineBean lineBean;

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public void setOnToDoListener(OnToDoListener onToDoListener) {
        this.onToDoListener = onToDoListener;
    }

    private OnToDoListener onToDoListener;


    interface OnToDoListener {
        void onLongClick(View v);

        void onTwoClick(View v);
    }

    public ImageViewDrawLine(Context context) {
        this(context, null);
    }

    public ImageViewDrawLine(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageViewDrawLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        DisplayMetrics dis = this.getResources().getDisplayMetrics();
        screenWidth = dis.widthPixels;
        screenHeight = dis.heightPixels;
        initPaint();
        imageViewBeans = new ArrayList<>();
        fl = (MyFramelayout) getParent();
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);
        paint.setColor(Color.RED);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!isLian) {
                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
                    if (onToDoListener != null) {
                        long time = System.currentTimeMillis();
                        if (time - lastClickTime < 500) {
                            onToDoListener.onTwoClick(this);
                        }
                        lastClickTime = time;
                    }

                } else {

                    startX = event.getRawX();
                    startY = event.getRawY();
                    fl.isDraw = true;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                MyFramelayout parent = (MyFramelayout) getParent();
                if (!isLian) {
                    int dx = (int) event.getRawX() - lastX;
                    int dy = (int) event.getRawY() - lastY;

                    int top = this.getTop() + dy;

                    int left = this.getLeft() + dx;

                    if (top <= 0) {
                        top = 0;
                    }
                    if (top >= screenHeight - this.getHeight()) {
                        top = screenHeight - this.getHeight();
                    }
                    if (left >= screenWidth - this.getWidth()) {
                        left = screenWidth - this.getWidth();
                    }

                    if (left <= 0) {
                        left = 0;
                    }
                    this.layout(left, top, left + this.getWidth(), top + this.getHeight());
                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
                } else {
                    stopX = event.getRawX();
                    stopY = event.getRawY();
                    fl.drawLine(startX, startY, stopX, stopY);
                }
                break;
            case MotionEvent.ACTION_UP:
                stopX = event.getRawX();
                stopY = event.getRawY();
                if (!isLian) {


                } else {


                    for (int i = 0; i < fl.getChildCount(); i++) {
                        ImageViewDrawLine childAt = (ImageViewDrawLine) fl.getChildAt(i);
                        if (stopY > childAt.getTop() && stopY < (childAt.getTop() + childAt.getHeight()) && stopX > childAt.getLeft()
                                && stopX < (childAt.getLeft() + childAt.getWidth()) && this.getId() != childAt.getId()) {

                            if (fl.arrayListLines.size() > 0) {
                                for (int j = 0; j < fl.arrayListLines.size(); j++) {
                                    LineBean lineBean = fl.arrayListLines.get(j);


                                    //满足同两个
                                    if ((lineBean.imageView.getId() == childAt.getId() && lineBean.imageView1.getId() == this.getId()) ||
                                            (lineBean.imageView.getId() == this.getId() && lineBean.imageView1.getId() == childAt.getId())) {
                                        if (lineBean.line > 4) {//设置一个控件与另一个相同控件最多的连接数
                                            Toast.makeText(getContext(), "不能连接更多了", Toast.LENGTH_SHORT).show();
                                            fl.isDraw = false;
                                            fl.invalidate();
                                            break;
                                        }
                                        lineBean.lineSt = lineBean.line + 1;
                                        lineBean.line = lineBean.lineSt;


                                        fl.isDraw = false;
                                        fl.invalidate();
                                        //添加跟自己相关的线的属性
                                        addImageBean(childAt, lineBean.line);
                                        break;
                                    } else if (j == fl.arrayListLines.size() - 1) {
                                        lineBean = new LineBean();
                                        lineBean.imageView = childAt;
                                        lineBean.imageView1 = this;
                                        lineBean.lineSt = 1;
                                        lineBean.line = 1;
                                        fl.arrayListLines.add(lineBean);




                                        fl.isDraw = false;
                                        fl.invalidate();
                                        //添加跟自己相关的线的属性
                                        addImageBean(childAt, lineBean.line);
                                        break;
                                    }
                                }
                            } else {
                                lineBean = new LineBean();
                                lineBean.imageView = childAt;
                                lineBean.imageView1 = this;
                                lineBean.lineSt = 1;
                                lineBean.line = 1;
                                fl.arrayListLines.add(lineBean);

                                fl.isDraw = false;
                                fl.invalidate();
                                addImageBean(childAt, lineBean.line);
                            }
                            break;
                        } else {
                            if (i == fl.getChildCount() - 1) {
                                //销毁自己的线

                                fl.drawLine(startX, startY, startX, startY);
                            }
                        }
                    }
                }
                break;

            case MotionEvent.ACTION_CANCEL:
                fl.isDraw = false;
                fl.invalidate();
                break;
        }


        return true;

    }

    private void addImageBean(ImageViewDrawLine childAt1, int line) {
        if (line > 1) {
            for (int i = 0; i < this.imageViewBeans.size(); i++) {
                if (imageViewBeans.get(i).id == childAt1.getId()) {
                    imageViewBeans.get(i).lines++;
                }
            }
            for (int i = 0; i < childAt1.imageViewBeans.size(); i++) {
                if (childAt1.imageViewBeans.get(i).id == this.getId()) {
                    childAt1.imageViewBeans.get(i).lines++;
                }
            }

        } else if (line == 1) {
            ImageViewBean imageViewBean = new ImageViewBean();
            imageViewBean.id = childAt1.getId();
            this.imageViewBeans.add(imageViewBean);


            ImageViewBean imageViewBean1 = new ImageViewBean();
            imageViewBean1.id = this.getId();
            childAt1.imageViewBeans.add(imageViewBean1);
        }

    }

    private void update(float stopX, float stopY) {

    }

    public void setLian(boolean isLianl) {
        isLian = isLianl;
    }

    public void setParnt(MyFramelayout fl1) {
        fl = fl1;
    }

    private static long lastClickTime;

    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
