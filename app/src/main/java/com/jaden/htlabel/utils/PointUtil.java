package com.jaden.htlabel.utils;

import android.graphics.PointF;
import android.view.MotionEvent;

/**
 * Created Date: 2018/12/13
 * Description:
 */
public class PointUtil {
    /**
    *description: 判断手指是否移动，根据两点间距离公式
    **/
    public static boolean isMove(PointF current, PointF last, float distance){
        double d = Math.sqrt(Math.pow(current.x - last.x, 2) + Math.pow(current.y - last.y, 2));
        if(d > distance){
            return true;
        }
        return false;
    }

    /**
    *description: 将MotionEvent转换成PointF
    **/
    public static void motion2point(PointF pointF, MotionEvent event){
        pointF.set(event.getX(), event.getY());
    }
}
