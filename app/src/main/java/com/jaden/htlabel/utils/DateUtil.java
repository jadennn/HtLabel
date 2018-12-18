package com.jaden.htlabel.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created Date: 2018/12/13
 * Description:
 */
public class DateUtil {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    public static String formatDate(long msec){
        return sdf.format(msec);
    }

    public static String getFormatDate(){
        return sdf.format(new Date());
    }
}
