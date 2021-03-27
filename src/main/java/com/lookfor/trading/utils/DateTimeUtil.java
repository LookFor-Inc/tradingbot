package com.lookfor.trading.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date and time utility
 */
public class DateTimeUtil {

    /**
     * Pattern type enum
     */
    public enum PatternType {
        YYYY_MM_DD_TOG("yyyyMMdd"),
        HH_MM_SS_COLON("HH:mm:ss"),
        HH_MM_SS_TOG("HHmmss");

        private final String obj;

        PatternType(String obj) {
            if (obj == null)
                throw new NullPointerException("obj");
            this.obj = obj;
        }

        public String obj() {
            return obj;
        }
    }

    /**
     * Convert string date to Date object
     *
     * @param str date in string format
     * @return Date object
     */
    public static Date stringToDate(String str, PatternType type) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat(type.obj());
        return inputFormat.parse(str);
    }

    /**
     * Format Date object to the string
     *
     * @param date Date object
     * @return String
     */
    public static String dateToString(Date date, PatternType type) {
        DateFormat formatter = new SimpleDateFormat(type.obj());
        return formatter.format(date);
    }
}
