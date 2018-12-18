package com.jaden.htlabel.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created Date: 2018/12/13
 * Description:
 */
public class DensityUtil {
    public static float dp2px(float dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return dp*metrics.density;
    }
}
