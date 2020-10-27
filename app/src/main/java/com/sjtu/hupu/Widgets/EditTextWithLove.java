package com.sjtu.hupu.Widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import com.sjtu.hupu.R;

public class EditTextWithLove extends AppCompatEditText {
    Drawable leftIconDrawable;
    Drawable rightIconDrawable;
    Drawable topIconDrawable;
    Drawable bottomIconDrawable;
    public EditTextWithLove(@NonNull Context context) {
        super(context);
    }

    public EditTextWithLove(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.EditTextWithLove,
                0, 0);

        try {
            int iconLeftId = a.getResourceId(R.styleable.EditTextWithLove_leftLoveIcon,0);
            int iconRightId = a.getResourceId(R.styleable.EditTextWithLove_rightLoveIcon,0);
            int iconTopId = a.getResourceId(R.styleable.EditTextWithLove_topLoveIcon,0);
            int iconBottomId = a.getResourceId(R.styleable.EditTextWithLove_bottomLoveIcon,0);
            if (iconLeftId != 0) {
                leftIconDrawable = ContextCompat.getDrawable(context,iconLeftId);
            }
            if (iconRightId != 0) {
                rightIconDrawable = ContextCompat.getDrawable(context,iconRightId);
            }

            if (iconTopId != 0) {
                topIconDrawable = ContextCompat.getDrawable(context,iconTopId);
            }
            if (iconBottomId != 0) {
                bottomIconDrawable = ContextCompat.getDrawable(context,iconBottomId);
            }
        } finally {
            a.recycle();
        }
    }

    public EditTextWithLove(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        toggleClearIcon();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float width = getWidth();
        //是整个高度
        float height = getHeight();


        if(event.getAction() == MotionEvent.ACTION_UP
        && event.getX() > 0
        && event.getX() < leftIconDrawable.getIntrinsicWidth()
        && event.getY() > height / 2 - leftIconDrawable.getIntrinsicHeight()
        && event.getY() < height / 2 + leftIconDrawable.getIntrinsicHeight()
        ) {
           setText("PP帮主");
        }

        if(event.getAction() == MotionEvent.ACTION_UP
                && event.getX() > width / 2 - topIconDrawable.getIntrinsicWidth() / 2
                && event.getX() < width / 2 + topIconDrawable.getIntrinsicWidth() / 2
//                && event.getY() > height / 2 - leftIconDrawable.getIntrinsicHeight()
//                && event.getY() < height / 2 + leftIconDrawable.getIntrinsicHeight()
                && event.getY() > 20
                && event.getY() < 20 + topIconDrawable.getIntrinsicHeight()
        ) {
            setText("小榴莲");
        }

        if(event.getAction() == MotionEvent.ACTION_UP
                && event.getX() > width / 2 - bottomIconDrawable.getIntrinsicWidth() / 2
                && event.getX() < width / 2 + bottomIconDrawable.getIntrinsicWidth() / 2
//                && event.getY() > height / 2 - leftIconDrawable.getIntrinsicHeight()
//                && event.getY() < height / 2 + leftIconDrawable.getIntrinsicHeight()
                && event.getY() > height - bottomIconDrawable.getIntrinsicHeight() - 20
                && event.getY() < height - 20
        ) {
            setText("顾家铧和刘莲");
        }

        if(event.getAction() == MotionEvent.ACTION_UP
                && event.getX() > width - rightIconDrawable.getIntrinsicWidth()
                && event.getX() < width
//                && event.getY() > height / 2 - leftIconDrawable.getIntrinsicHeight()
//                && event.getY() < height / 2 + leftIconDrawable.getIntrinsicHeight()
                && event.getY() > height / 2 - bottomIconDrawable.getIntrinsicHeight() / 2 - 10
                && event.getY() < height / 2 + bottomIconDrawable.getIntrinsicHeight() / 2 + 10
        ) {
            setText("");
        }

        performClick();
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        toggleClearIcon();
    }

    public void toggleClearIcon() {
        CharSequence text = getText();
        Drawable iconLeft = (isFocused() && text.length() != 0) ? leftIconDrawable : null;
        Drawable iconRight = (isFocused() && text.length() != 0) ? rightIconDrawable : null;
        Drawable iconTop = (isFocused() && text.length() != 0) ? topIconDrawable : null;
        Drawable iconBottom = (isFocused() && text.length() != 0) ? bottomIconDrawable : null;
        setCompoundDrawablesWithIntrinsicBounds(iconLeft,iconTop,iconRight,iconBottom);
    }
}
