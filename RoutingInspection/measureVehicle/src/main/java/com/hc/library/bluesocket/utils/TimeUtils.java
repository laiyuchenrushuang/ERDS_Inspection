package com.hc.library.bluesocket.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2018/1/17.
 */

public class TimeUtils {
    public static long getSystemTime() {
            return  System.currentTimeMillis();
    }
    /*
 * 将时间转换为时间戳
 */
    public static long dateToStamp(String time)  {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = simpleDateFormat.parse(time);
            long ts = date.getTime();
            return ts;
        }catch (ParseException io){
            return 0;
        }
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(long timeMillis){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }
}
