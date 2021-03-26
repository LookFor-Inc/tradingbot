package com.lookfor.trading.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Time utility
 */
public class TimeUtil {
    public static final String PATTERN = "HH:mm:ss";

    /**
     * Convert string date to Date object
     *
     * @param str date in string format
     * @return Date object
     */
    public static Date stringToDate(String str) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(PATTERN);
        Date date = null;

        try {
            date = inputFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * Format Date object to the string
     *
     * @param date Date object
     * @return String
     */
    public static String dateToString(Date date) {
        DateFormat formatter = new SimpleDateFormat(PATTERN);
        return formatter.format(date);
    }
}
