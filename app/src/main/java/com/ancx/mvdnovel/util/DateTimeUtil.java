package com.ancx.mvdnovel.util;

/**
 * Created by Ancx on 2016/4/16.
 */
public class DateTimeUtil {

    public static String getDate(String date) {
        if (date.length() > 16) {
            return date.substring(0, 10) + " " + date.substring(11, 16);
        }
        return date;
    }

}
