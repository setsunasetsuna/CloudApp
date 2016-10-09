package com.setsuna.cloudapp.utils;

import java.math.BigDecimal;

/**
 * Created by setsuna on 2016/9/27.
 */
public class FormatSize {
    public static String getSize(long size){
        String formatSize="";
        double newSize=0.0;
        if (size>=1024*1024){
            newSize=size/(1024*1024*1.0);
            BigDecimal bigDecimal=new BigDecimal(newSize);
            bigDecimal=bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP);
            formatSize=bigDecimal+" MB";
        }else {
            newSize=size/(1024*1.0);
            BigDecimal bigDecimal=new BigDecimal(newSize);
            bigDecimal=bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP);
            formatSize=bigDecimal+" KB";
        }

        return formatSize;
    }
}
