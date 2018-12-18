package com.jaden.htlabel.utils;

import android.util.Log;

/**
 * Created Date: 2018/12/13
 * Description:
 */
public class Slog {
    private static final String TAG = "HTLabel";

    public static void i(String msg){
        Log.i(TAG, msg);
    }

    public static void e(String msg){
        Log.e(TAG, msg);
    }
}
