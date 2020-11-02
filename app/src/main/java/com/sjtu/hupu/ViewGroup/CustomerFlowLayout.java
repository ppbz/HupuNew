package com.sjtu.hupu.ViewGroup;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sjtu.hupu.R;
import com.sjtu.hupu.View.MyTextView;
import com.sjtu.hupu.bean.LineBean;
import com.sjtu.hupu.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class CustomerFlowLayout extends ViewGroup {
    private List<String> mTags = new ArrayList<String>();
    public List<Rect> childrenBounds = new ArrayList<>();
    private boolean flag = false;
    private float r = 40f;

    public CustomerFlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public CustomerFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CustomerFlowLayout(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        //设置ViewGroup也能调用onDraw
        setWillNotDraw(false);

    }

    public void setFlag(boolean f) {
        this.flag = f;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        for(int i = 0; i < getChildCount(); i ++) {
            final MyTextView child = (MyTextView) getChildAt(i);
            child.setOnMoveActionListener(new MyTextView.OnMoveActionListener() {
                @Override
                public void OnMove(int l, int t, int right, int bottom, View view) {
                    for(int i = 0; i < getChildCount(); i ++) {

                        MyTextView mt = (MyTextView)getChildAt(i);
                        if(mt.equals(view)) {
                            Log.e("第几个view",i + "");
                            //childrenBounds.remove(i);
                            Rect childBound = childrenBounds.get(i);
                            childBound.left = l;
                            childBound.top = t;
                            childBound.right = right;
                            childBound.bottom = bottom;
                            requestLayout();
                            break;
                        }

                    }
                }
            });
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //当前ViewGroup的总高度
        int totalHeight= 0;
        //所有行中的最大宽度
        int maxLineWidth = 0;

        //当前行的最大高度
        int lineMaxHeight = 0;
        //当前行的总宽度
        int currentLineWidth = 0;

        //每个childView所占用的宽度
        int childViewWidthSpace = 0;
        //每个childView所占用的高度
        int childViewHeightSpace = 0;

        int count = getChildCount();
        MarginLayoutParams layoutParams;

        for(int i = 0; i < count; i++){
            View child = getChildAt(i);
            childrenBounds.add(new Rect());
            if(child.getVisibility() != View.GONE){//只有当这个View能够显示的时候才去测量
                //测量每个子View，以获取子View的宽和高
                measureChild(child, widthMeasureSpec, heightMeasureSpec);

                layoutParams = (MarginLayoutParams) child.getLayoutParams();

                childViewWidthSpace = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
                childViewHeightSpace = child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;

                if(currentLineWidth + childViewWidthSpace > widthSize){//表示如果当前行再加上现在这个子View，就会超出总的规定宽度，需要另起一行
                    totalHeight += lineMaxHeight;
                    if(maxLineWidth < currentLineWidth){//如果行的最长宽度发生了变化，更新保存的最长宽度
                        maxLineWidth = currentLineWidth;
                    }
                    currentLineWidth = childViewWidthSpace;//另起一行后，需要重置当前行宽
                    lineMaxHeight = childViewHeightSpace;
                }else{//表示当前行可以继续添加子元素
                    currentLineWidth += childViewWidthSpace;
                    if(lineMaxHeight < childViewHeightSpace){
                        lineMaxHeight = childViewHeightSpace;
                    }
                }
            }
        }

        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : maxLineWidth, heightMode == MeasureSpec.EXACTLY ? heightSize : totalHeight);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //hoLayout();
        if(!flag){
            circleLayout();
            flag = true;
        }else {
            doLayout();
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    public void setTags(List<String> tags){
        if(tags!= null){
            mTags.clear();
            //需要将原先坐标全部更新，清除掉后requestLayout会再次调用onMeasure onLayout onDraw
            childrenBounds.clear();
            this.removeAllViews();
            mTags.addAll(tags);
            for(int i = 0; i < mTags.size(); i++){
                MyTextView tv = new MyTextView(getContext());
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(10f);
                MarginLayoutParams lp = new MarginLayoutParams(MarginLayoutParams.WRAP_CONTENT, MarginLayoutParams.WRAP_CONTENT);
                lp.setMargins(15, 15, 15, 15);
//                lp.width = MarginLayoutParams.WRAP_CONTENT;
//                lp.height = MarginLayoutParams.WRAP_CONTENT;
                tv.setLayoutParams(lp);
                tv.setBackgroundResource(R.drawable.circle_background);
                /*
                 * setPadding一定要在setBackgroundResource后面使用才有效！！！
                 * http://stackoverflow.com/questions/18327498/setting-padding-for-textview-not-working
                 */
                tv.setPadding(15, 15, 15, 15);
                tv.setTextColor(Color.BLACK);

                tv.setText(mTags.get(i));

                tv.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(listener != null){
                            listener.onClick(v);
                        }
                    }
                });

                addView(tv);
            }
            requestLayout();
        }else {
            mTags.clear();
            requestLayout();
        }
    }

    private OnTagItemClickListener listener;
    public interface OnTagItemClickListener{
        public void onClick(View v);
    }
    public void setOnTagItemClickListener(OnTagItemClickListener l){
        listener = l;
    }

    /*@Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;

        return intercepted;
    }*/

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        float startX = 0;
        float startY = 0;
        if(getChildCount() > 0){
            startX = childrenBounds.get(0).left + Utils.dp2px(40f);
            startY = childrenBounds.get(0).top + Utils.dp2px(40f);
        }
        for(int i = 1; i < getChildCount(); i ++) {
            canvas.drawLine(startX,startY,
                    childrenBounds.get(i).left + Utils.dp2px(40f),
                    childrenBounds.get(i).top + Utils.dp2px(40f),paint);
        }

    }

    //平铺
    private void hoLayout() {
        //当前是第几行
        int currentLine = 1;
        //存放每一行的最大高度
        List<Integer> lineMaxHeightList = new ArrayList<Integer>();

        //每个childView所占用的宽度
        int childViewWidthSpace = 0;
        //每个childView所占用的高度
        int childViewHeightSpace = 0;

        //当前行的最大高度
        int lineMaxHeight = 0;
        //当前行的总宽度
        int currentLineWidth = 0;

        int count = getChildCount();
        MarginLayoutParams layoutParams;

        for(int i = 0; i < count; i++){
            //int cl= 0, ct = 0, cr = 0, cb = 0;
            MyTextView child = (MyTextView) getChildAt(i);
            Rect childBound = childrenBounds.get(i);
            if(child.getVisibility() != View.GONE){//只有当这个View能够显示的时候才去测量

                layoutParams = (MarginLayoutParams) child.getLayoutParams();
                childViewWidthSpace = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
                childViewHeightSpace = child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;

                System.out.println("getWidth()---->"+getWidth());

                if(currentLineWidth + childViewWidthSpace > getWidth()){//表示如果当前行再加上现在这个子View，就会超出总的规定宽度，需要另起一行
                    lineMaxHeightList.add(lineMaxHeight);//此时先将这一行的最大高度加入到集合中
                    //新的一行，重置一些参数
                    currentLine++;
                    currentLineWidth = childViewWidthSpace;
                    lineMaxHeight = childViewHeightSpace;

                    //cl = layoutParams.leftMargin;
                    childBound.left = layoutParams.leftMargin;
                    if(currentLine > 1){
                        for(int j = 0; j < currentLine - 1; j++){
                            //ct += lineMaxHeightList.get(j);
                            childBound.top += lineMaxHeightList.get(j);
                        }
                        //ct += layoutParams.topMargin ;
                        childBound.top += layoutParams.topMargin ;
                    }else{
                        //ct = layoutParams.topMargin;
                        childBound.top = layoutParams.topMargin;
                    }
                }else{//表示当前行可以继续添加子元素
                    //cl = currentLineWidth + layoutParams.leftMargin;
                    childBound.left = currentLineWidth + layoutParams.leftMargin;
                    if(currentLine > 1){
                        for(int j = 0; j < currentLine - 1; j++){
                            //ct += lineMaxHeightList.get(j);
                            childBound.top += lineMaxHeightList.get(j);
                        }
                        // ct += layoutParams.topMargin;
                        childBound.top += layoutParams.topMargin;
                    }else{
                        //ct = layoutParams.topMargin;
                        childBound.top = layoutParams.topMargin;
                    }
                    currentLineWidth += childViewWidthSpace;
                    if(lineMaxHeight < childViewHeightSpace){
                        lineMaxHeight = childViewHeightSpace;
                    }
                }

                //cr = cl + child.getMeasuredWidth();
                childBound.right = childBound.left + child.getMeasuredWidth();
                //cb = ct + child.getMeasuredHeight();
                childBound.bottom = childBound.top + child.getMeasuredHeight();

                //child.layout(cl, ct, cr, cb);
                child.layout(childBound.left, childBound.top, childBound.right, childBound.bottom);

            }


        }
    }

    private void circleLayout() {
        int width = getWidth();
        int height = getHeight();
        int left = 0;
        int top = 0;
        int right = 0;
        int bottom = 0;
        if(getChildCount() > 0) {
            MyTextView mt = (MyTextView)getChildAt(0);
            left = width/2-(int)Utils.dp2px(r);
            top = height/2-(int)Utils.dp2px(r);
            right = width/2+(int)Utils.dp2px(r);
            bottom = height/2+(int)Utils.dp2px(r);
            childrenBounds.get(0).set(left,top,right,bottom);
            //mt.layout(left,top,right,bottom);
        }
        int count = getChildCount();
        for(int i = 0; i < count; i ++) {
            MyTextView child = (MyTextView)getChildAt(i);
            Rect childBound = childrenBounds.get(i);
            if(i != 0) {
                childBound.left = width/2 + (int)(Utils.dp2px(300f)*Math.cos(i * 2 * Math.PI / (count - 1)) - Utils.dp2px(r));
                childBound.top = height/2 + (int)(Utils.dp2px(300f)*Math.sin(i * 2 * Math.PI / (count - 1)) -Utils.dp2px(r));
                childBound.right = width/2 + (int)(Utils.dp2px(300f)*Math.cos(i * 2 * Math.PI / (count - 1)) +Utils.dp2px(r));
                childBound.bottom = height/2 + (int)(Utils.dp2px(300f)*Math.sin(i * 2 * Math.PI / (count - 1)) +Utils.dp2px(r));
            }
            child.layout(childBound.left,childBound.top,childBound.right,childBound.bottom);
            //Log.e(childBound.left + " "  + childBound.top ,childBound.right + " " + childBound.bottom);
        }


    }

    public void doLayout() {
        int count = getChildCount();
        for(int i = 0; i < count; i ++) {
            MyTextView child = (MyTextView)getChildAt(i);
            Rect childBound = childrenBounds.get(i);
            child.layout(childBound.left,childBound.top,childBound.right,childBound.bottom);
            //Log.e(childBound.left + " "  + childBound.top ,childBound.right + " " + childBound.bottom);
        }
    }
}
