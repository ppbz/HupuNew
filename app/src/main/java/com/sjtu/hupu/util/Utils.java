package com.sjtu.hupu.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;

public class Utils {
    //dp转px
    public static float dp2px(float value) {
        /*只要转化不应该传view
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value,view.getResources().getDisplayMetrics());*/
        /*也不应该传context，activity就是个context，但方法更通用些，上下文信息包括value里的内容等
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value,context.getResources().getDisplayMetrics());*/
        //但其实跟value里的内容无关，所以应该要一个更通用的，Resources.getSystem()只能拿到与系统相关的上下文信息
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value, Resources.getSystem().getDisplayMetrics());
    }


}
