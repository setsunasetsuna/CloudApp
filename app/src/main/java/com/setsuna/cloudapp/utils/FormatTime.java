package com.setsuna.cloudapp.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by setsuna on 2016/9/27.
 */
public class FormatTime {
    private static SimpleDateFormat mSimpleDateFormat;
    private static Date date = new Date();

    public static String getTime(long time) {
        mSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        date.setTime(time * 1000);
        return mSimpleDateFormat.format(date);
    }
    public static String getTimeNYR(long time) {
        mSimpleDateFormat= new SimpleDateFormat("yyyy/MM/dd");
        date.setTime(time * 1000);
        return mSimpleDateFormat.format(date);
    }

}
