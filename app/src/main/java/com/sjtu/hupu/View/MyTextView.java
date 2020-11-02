package com.sjtu.hupu.View;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.customview.widget.ViewDragHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.sjtu.hupu.Fragment.CommunityFragment;
import com.sjtu.hupu.ViewGroup.CustomerFlowLayout;
import com.sjtu.hupu.databinding.CommunityFragmentBinding;

public class MyTextView extends androidx.appcompat.widget.AppCompatTextView {
    private float startx;// down事件发生时，手指相对于view左上角x轴的距离
    private float starty;// down事件发生时，手指相对于view左上角y轴的距离
    private float endx; // move事件发生时，手指相对于view左上角x轴的距离
    private float endy; // move事件发生时，手指相对于view左上角y轴的距离
    private int left; // DragTV左边缘相对于父控件的距离
    private int top; // DragTV上边缘相对于父控件的距离
    private int right; // DragTV右边缘相对于父控件的距离
    private int bottom; // DragTV底边缘相对于父控件的距离
    private int hor; // 触摸情况下，手指在x轴方向移动的距离
    private int ver; // 触摸情况下，手指在y轴方向移动的距离
    private OnMoveActionListener mMove = null;

    //CommunityFragmentBinding comBinding;
    public MyTextView(Context context) {
        super(context);
        initView();
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView() {
        //comBinding = CommunityFragmentBinding.inflate(LayoutInflater.from(getContext()));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        CustomerFlowLayout parent = (CustomerFlowLayout) getParent();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 手指刚触摸到屏幕的那一刻，手指相对于View左上角水平和竖直方向的距离:startX startY
                startx = event.getX();
                starty = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                // 手指停留在屏幕或移动时，手指相对与View左上角水平和竖直方向的距离:endX endY
                endx = event.getX();
                endy = event.getY();
                // 获取此时刻 View的位置。
                left = getLeft();
                top = getTop();
                right = getRight();
                bottom = getBottom();
                // 手指移动的水平距离
                hor = (int) (endx - startx);
                // 手指移动的竖直距离
                ver = (int) (endy - starty);
                // 当手指在水平或竖直方向上发生移动时，重新设置View的位置（layout方法）
                /*if (hor != 0 || ver != 0) {
                    layout(left + hor, top + ver, right + hor, bottom + ver);
                }*/
                if(mMove != null) {
                    mMove.OnMove(left + hor, top + ver, right + hor, bottom + ver, this);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //如果直接requestDisallowInterceptTouchEven(true)会拦截所有事件都拦截，我们暂时需要的确实是这个效果
        //这是内部拦截法。由于viewpager2不能被重写，所以外部拦截法无效
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(event);
    }

    public interface OnMoveActionListener {
        public void OnMove(int l, int t, int right, int bottom, View view);
    }

    public void setOnMoveActionListener(OnMoveActionListener move) {
        mMove = move;
    }


}
