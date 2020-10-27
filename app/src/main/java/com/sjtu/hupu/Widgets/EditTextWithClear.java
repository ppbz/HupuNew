package com.sjtu.hupu.Widgets;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import com.sjtu.hupu.R;

import org.w3c.dom.Text;

public class EditTextWithClear extends AppCompatEditText {
    private Drawable iconDrawable;
    private Drawable searchIconDrawable;
    public EditTextWithClear(@NonNull Context context) {
        super(context);

        //iconDrawable = ContextCompat.getDrawable(context, R.drawable.ic_baseline_clear_24);
    }

    public EditTextWithClear(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //iconDrawable = ContextCompat.getDrawable(context, R.drawable.ic_baseline_clear_24);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.EditTextWithClear, 0, 0);

        try {
            int iconID = a.getResourceId(R.styleable.EditTextWithClear_clearIcon,0);
            if(iconID != 0) {
                iconDrawable = ContextCompat.getDrawable(context,iconID);
            }
            int searchIconId = a.getResourceId(R.styleable.EditTextWithClear_searchIcon,0);
            if(searchIconId != 0) {
                searchIconDrawable = ContextCompat.getDrawable(context,searchIconId);
                setCompoundDrawablesRelativeWithIntrinsicBounds(searchIconDrawable,null,null,null);
            }
        } finally {
            a.recycle();
        }
    }

    public EditTextWithClear(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //iconDrawable = ContextCompat.getDrawable(context, R.drawable.ic_baseline_clear_24);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.EditTextWithClear, 0, 0);

        try {
            int iconID = a.getResourceId(R.styleable.EditTextWithClear_clearIcon,0);
            if(iconID != 0) {
                iconDrawable = ContextCompat.getDrawable(context,iconID);
            }
            int searchIconId = a.getResourceId(R.styleable.EditTextWithClear_searchIcon,0);
            if(searchIconId != 0) {
                searchIconDrawable = ContextCompat.getDrawable(context,searchIconId);
                setCompoundDrawablesRelativeWithIntrinsicBounds(searchIconDrawable,null,null,null);
            }
        } finally {
            a.recycle();
        }
    }



    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        toggleClearIcon();
    }

    private void toggleClearIcon() {
        CharSequence text = getText();
        Drawable icon = (isFocused() && text.length() != 0) ? iconDrawable : null;
        //固有尺寸。用setCompoundDrawablesRelative需要setBounds，非常麻烦
        setCompoundDrawablesRelativeWithIntrinsicBounds(searchIconDrawable,null,icon,null);

    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event != null && iconDrawable != null) {
            //这个是屏幕的宽度高度
            /*Resources resources = this.getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            float width = dm.widthPixels;
            float height = dm.heightPixels;*/
            //这个是editText的宽度高度
            float width = getWidth();
            float height = getHeight();
            if (event.getAction() == MotionEvent.ACTION_UP
                && event.getX() > width - iconDrawable.getIntrinsicWidth() - 20
                && event.getX() < width + 20
                && event.getY() > height / 2 - iconDrawable.getIntrinsicHeight() / 2 - 20
                && event.getY() < height / 2 + iconDrawable.getIntrinsicHeight() / 2 + 20
            ) {
                setText("");
            }
        }
        performClick();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        toggleClearIcon();
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}
