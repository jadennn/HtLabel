package com.jaden.htlabel.utils;

import android.graphics.RectF;
import android.view.MotionEvent;

/**
 * Created Date: 2018/12/14
 * Description:
 */
public class RectUtil {
    /**
     *description: 判断点击事件是否落在当前RectF内
     **/
    public static boolean inRegion(RectF rectF, MotionEvent event){
        if(event.getX() > rectF.left && event.getX() < rectF.right
                && event.getY() > rectF.top && event.getY() < rectF.bottom){
            return true;
        }
        return false;
    }
}
