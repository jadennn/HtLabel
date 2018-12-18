package com.jaden.htlabel.utils;

import android.graphics.Color;

/**
 * Created Date: 2018/12/13
 * Description:
 */
public class ColorUtil {
    /**
    *description: 生成随机颜色，测试用
    **/
    public static int getRandomColor() {
        int r = (int) (Math.random() * 255);
        int g = (int) (Math.random() * 255);
        int b = (int) (Math.random() * 255);
        return Color.rgb(r, g, b);
    }
}
