package com.lyun.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ZHAOWEIWEI on 2017/3/1.
 */

public class TimeUtil {
    /**
     * 转换毫秒数成“分、秒”，如“01:53”。若超过60分钟则显示“时、分、秒”，如“01:01:30
     *
     * @param time 待转换的毫秒数
     */
    public static String convertMills2Str(long time) {
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;

        long hour = (time) / hh;
        long minute = (time - hour * hh) / mi;
        long second = (time - hour * hh - minute * mi) / ss;

        String strHour = hour < 10 ? "0" + hour : "" + hour;
        String strMinute = minute < 10 ? "0" + minute : "" + minute;
        String strSecond = second < 10 ? "0" + second : "" + second;
        if (hour > 0) {
            return strHour + ":" + strMinute + ":" + strSecond;
        } else {
            return strMinute + ":" + strSecond;
        }
    }

    //分钟转化为小时和分钟
    public static String convertMin2Str(String min) {
        long timeMin = 0;
        try {
            timeMin = Long.parseLong(min);
        } catch (Exception e) {
            return null;
        }
        if (timeMin <= 60) {
            return min + "分钟";
        } else {
            long hour = timeMin / 60;
            long mins = timeMin % 60;
            return hour+"小时"+mins+"分钟";
        }
    }
    //格式化String 时间
    public static String formatTime(String data,String style) {
        SimpleDateFormat time = new SimpleDateFormat(style);
        Date date = null;
        try {
           date = time.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(date ==null)
            return "";
        return time.format(date);
    }
}
