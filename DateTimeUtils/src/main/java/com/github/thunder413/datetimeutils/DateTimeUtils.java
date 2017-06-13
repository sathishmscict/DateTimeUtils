package com.github.thunder413.datetimeutils;


import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * DateUtils
 * This class contains a bunch of function that can manipulate
 * Date object or Date String to achieve certain operations
 * e.g : Time difference, Time Ago, Date formatting
 *
 * @author Cheikh Semeta
 * @version 1.0
 */
@SuppressWarnings("WeakerAccess")
public class DateTimeUtils {
    /**
     * LOG TAG
     */
    private static final String LOG_TAG = "DateTimeUtils";
    /**
     * Debug mode
     */
    private static boolean debug;
    /**
     * Time zone
     */
    private static String timeZone = "UTC";
    
    /**
     * Enable / Disable
     * @param state Debug state
     */
    public static void setDebug(boolean state){
        debug = state;
    }

    /**
     * Set TimeZone
     * @param zone TimeZone
     */
    public static void setTimeZone(String zone){
        timeZone = zone;
    }
    /**
     * Tell whether or not a given string represent a date time string or a simple date
     *
     * @param dateString Date String
     * @return True if given string is a date time False otherwise
     */
    private static boolean isDateTime(String dateString) {
        return (dateString != null) && (dateString.trim().split(" ").length > 1);
    }

    /**
     * Get Date or DateTime formatting pattern
     *
     * @param dateString Date String
     * @return Format Pattern
     */
    private static String getDatePattern(String dateString) {
        if (isDateTime(dateString)) {
            return (dateString.contains("/")) ? DateTimeFormat.DATE_TIME_PATTERN_2 : DateTimeFormat.DATE_TIME_PATTERN_1;
        } else {
            return (dateString.contains("/")) ? DateTimeFormat.DATE_PATTERN_2 : DateTimeFormat.DATE_PATTERN_1;
        }
    }

    /**
     * Convert a Java Date object to String
     *
     * @param date Date Object
     * @return Date Object string representation
     */
    public static String formatDate(Date date, Locale locale) {
        if(date == null && debug){
            Log.e(LOG_TAG,"formatDate >> Supplied date is null");
        }
        SimpleDateFormat iso8601Format = new SimpleDateFormat(DateTimeFormat.DATE_TIME_PATTERN_1, locale);
        iso8601Format.setTimeZone(TimeZone.getTimeZone(timeZone));
        return iso8601Format.format(date);
    }

    /**
     * Convert a Java Date object to String
     *
     * @param date Date Object
     * @return Date Object string representation
     */
    public static String formatDate(Date date) {
        return formatDate(date, Locale.getDefault());
    }

    /**
     * Convert a date string to Java Date Object
     *
     * @param dateString Date String
     * @return Java Date Object
     */
    public static Date formatDate(String dateString, Locale locale) {
        SimpleDateFormat iso8601Format = new SimpleDateFormat(getDatePattern(dateString), locale);
        iso8601Format.setTimeZone(TimeZone.getTimeZone(timeZone));
        Date date = null;
        if (dateString != null) {
            try {
                date = iso8601Format.parse(dateString.trim());
            } catch (ParseException e) {
                if(debug) {
                    Log.e(LOG_TAG,"formatDate >> Fail to parse supplied date string >> "+dateString);
                    e.printStackTrace();
                }
            }
        }
        return date;
    }

    /**
     * Convert a date string to Java Date Object
     *
     * @param dateString Date String
     * @return Java Date Object
     */
    public static Date formatDate(String dateString) {
        return formatDate(dateString, Locale.getDefault());
    }

    /**
     * Convert a timeStamp into a date
     *
     * @param timeStamp TimeStamp
     * @param units Witch unit is whether seconds or milliseconds
     *              @see DateTimeUnits#SECONDS
     *              @see DateTimeUnits#MILLISECONDS
     * @return Date object
     */
    public static Date formatDate(long timeStamp, DateTimeUnits units){
        if(units.equals(DateTimeUnits.SECONDS))
            return new Date(timeStamp*1000L);
        else
            return new Date(timeStamp);
    }
    /**
     * Convert a timeStamp into a date considering given timeStamp in milliseconds
     *
     * @see DateTimeUnits#MILLISECONDS
     * @param timeStamp TimeStamp
     * @return Date object
     */
    public static Date formatDate(long timeStamp){
       return formatDate(timeStamp,DateTimeUnits.MILLISECONDS);
    }

    /**
     * Get localized date string (Using given locale)
     *
     * @param dateString Date string
     * @param locale     Desired locale
     * @return Formatted localized date string
     */
    public static String formatLocalized(String dateString, Locale locale) {
        Date date = formatDate(dateString, locale);
        SimpleDateFormat iso8601Format = new SimpleDateFormat("d MMM, yyyy HH:mm", locale);
        iso8601Format.setTimeZone(TimeZone.getTimeZone(timeZone));
        return iso8601Format.format(date);
    }

    /**
     * Get localized date string (Using default locale)
     *
     * @param date Date string
     * @return Formatted localized date string
     */
    public static String formatLocalized(Date date, Locale locale) {
        return formatLocalized(formatDate(date), locale);
    }

    /**
     * Get localized date string (Using default locale)
     *
     * @param dateString Date string
     * @return Formatted localized date string
     */
    public static String formatLocalized(String dateString) {
        return formatLocalized(dateString, Locale.getDefault());
    }

    /**
     * Get localized date string (Using default locale)
     *
     * @param date Date string
     * @return Formatted localized date string
     */
    public static String formatLocalized(Date date) {
        return formatLocalized(date, Locale.getDefault());
    }

    /**
     * Extract time from date without seconds
     * @param date Date object
     * @return Time string
     */
    public static String formatTime(String date){
        return formatTime(formatDate(date));
    }

    /**
     * Extract time from date without seconds
     * @see DateTimeFormat#TIME_PATTERN_1
     * @param date Date object
     * @return Time String
     */
    public static String formatTime(Date date){
        SimpleDateFormat iso8601Format = new SimpleDateFormat(DateTimeFormat.TIME_PATTERN_1, Locale.getDefault());
        iso8601Format.setTimeZone(TimeZone.getTimeZone(timeZone));
        return iso8601Format.format(date);
    }

    /**
     * Extract time from date with seconds
     * @see DateTimeFormat#TIME_PATTERN_2
     * @param dateString Date string
     * @return Time String
     */
    public static String formatFullTime(String dateString){
        return formatTime(formatDate(dateString));
    }
    /**
     * Extract time from date with seconds
     *
     * @see DateTimeFormat#TIME_PATTERN_2
     * @param date Date object
     * @return Time String
     */
    public static String formatFullTime(Date date){
        SimpleDateFormat iso8601Format = new SimpleDateFormat(DateTimeFormat.TIME_PATTERN_2, Locale.getDefault());
        iso8601Format.setTimeZone(TimeZone.getTimeZone(timeZone));
        return iso8601Format.format(date);
    }
    /**
     * Convert millis to human readable time
     *
     * @param millis TimeStamp
     * @return Time String
     */
    public static String millisToTime(int millis){
        return  millisToTime(millis,true);
    }
    /**
     * Convert millis to human readable time
     *
     * @param millis TimeStamp
     * @param withSeconds add second to string or not
     * @return Time String
     */
    private static String millisToTime(int millis, boolean withSeconds){
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
                - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis));
        long hours = TimeUnit.MILLISECONDS.toHours(millis);

        return (hours == 0 ? "" : hours < 10 ? String.valueOf("0" + hours)+":" :
                String.valueOf(hours)+":") +
                (minutes == 0 ? "00" : minutes < 10 ? String.valueOf("0" + minutes) :
                        String.valueOf(minutes)) +
                (withSeconds ? ":" + (seconds == 0 ? "00" : seconds < 10 ? String.valueOf("0" + seconds)
                        : String.valueOf(seconds)):"");

    }
    /**
     * Tell whether or not a given date is yesterday
     * @param date Date Object
     * @return True if the date is yesterday False otherwise
     */
    public static boolean isYesterday(Date date){
        // Check if yesterday
        Calendar c1 = Calendar.getInstance(); // today
        c1.add(Calendar.DAY_OF_YEAR, -1); // yesterday
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date); //
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR);
    }
    /**
     * Tell whether or not a given date is yesterday
     * @param dateString Date String
     * @return True if the date is yesterday False otherwise
     */
    public static boolean isYesterday(String dateString){
        return isYesterday(formatDate(dateString));
    }

    /**
     * Tell whether or not a given date is today date
     * @param date Date object
     * @return True if date is today False otherwise
     */
    public static boolean isToday(Date date){
        return DateUtils.isToday(date.getTime());
    }

    /**
     * Tell whether or not a given date is today date
     * @param dateString Date string
     * @return True if date is today False otherwise
     */
    public static boolean isToday(String dateString){
        return isToday(formatDate(dateString));
    }
    /**
     * Get difference between two dates
     *
     * @param nowDate  Current date
     * @param oldDate  Date to compare
     * @param dateDiff Difference Unit
     * @return Difference
     */
    public static int getDateDiff(Date nowDate, Date oldDate, DateTimeUnits dateDiff) {
        long diffInMs = nowDate.getTime() - oldDate.getTime();
        int days = (int) TimeUnit.MILLISECONDS.toDays(diffInMs);
        int hours = (int) (TimeUnit.MILLISECONDS.toHours(diffInMs) - TimeUnit.DAYS.toHours(days));
        int minutes = (int) (TimeUnit.MILLISECONDS.toMinutes(diffInMs) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(diffInMs)));
        int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(diffInMs);

        switch (dateDiff) {
            case DAYS:
                return days;
            case SECONDS:
                return seconds;
            case MINUTES:
                return minutes;
            case HOURS:
                return hours;
            case MILLISECONDS:
            default:
                return (int) diffInMs;
        }
    }
    /**
     * Get time ago of given date
     *
     * @param context Context
     * @param date    Date object
     * @param style DateTimeStyle
     * @return Time ago string
     */
    public static String getTimeAgo(Context context, Date date, DateTimeStyle style) {
        double seconds = getDateDiff(new Date(), date, DateTimeUnits.SECONDS);
        double minutes = seconds / 60;
        double hours = minutes / 60;
        double days = hours / 24;
        String phrase;
        String s;
        if (seconds <= 0) {
            phrase = context.getString(R.string.time_ago_now);
        } else if (seconds < 45) {
            s = style.equals(DateTimeStyle.AGO_FULL_STRING) ? context.getString(R.string.time_ago_full_seconds):
                    context.getString(R.string.time_ago_seconds);
            phrase = String.format(s, Math.round(seconds));
        } else if (seconds < 90) {
            s = style.equals(DateTimeStyle.AGO_FULL_STRING) ? context.getString(R.string.time_ago_full_minute):
                    context.getString(R.string.time_ago_minute);
            phrase = String.format(s,1);
        } else if (minutes < 45) {
            s = style.equals(DateTimeStyle.AGO_FULL_STRING) ? context.getString(R.string.time_ago_full_minutes):
                    context.getString(R.string.time_ago_minutes);
            phrase = String.format(s, Math.round(minutes));
        } else if (minutes < 90) {
            s = style.equals(DateTimeStyle.AGO_FULL_STRING) ? context.getString(R.string.time_ago_full_hour):
                    context.getString(R.string.time_ago_hour);
            phrase = String.format(s, 1);
        } else if (hours < 24) {
            s = style.equals(DateTimeStyle.AGO_FULL_STRING) ? context.getString(R.string.time_ago_full_hours):
                    context.getString(R.string.time_ago_hours);
            phrase = String.format(s,Math.round(hours));
        } else if (hours < 42) {
            if (isYesterday(date)) {
                phrase = context.getString(R.string.time_ago_yesterday_at, formatTime(date));
            } else {
                phrase = formatLocalized(date);
            }
        } else if (days < 30) {
            s = style.equals(DateTimeStyle.AGO_FULL_STRING) ? context.getString(R.string.time_ago_full_days):
                    context.getString(R.string.time_ago_days);
            phrase = String.format(s, Math.round(days));
        } else if (days < 45) {
            s = style.equals(DateTimeStyle.AGO_FULL_STRING) ? context.getString(R.string.time_ago_full_month):
                    context.getString(R.string.time_ago_month);
            phrase = String.format(s, 1);
        } else if (days < 365) {
            s = style.equals(DateTimeStyle.AGO_FULL_STRING) ? context.getString(R.string.time_ago_full_months):
                    context.getString(R.string.time_ago_months);
            phrase = String.format(s, Math.round(days / 30));
        } else {
            phrase = formatLocalized(date);
        }
        return phrase;
    }
    /**
     * Get time ago of given date
     *
     * @param context    Context
     * @param dateString Representing a date time string
     * @return Time ago string
     */
    public static String getTimeAgo(Context context, String dateString) {
        return getTimeAgo(context, formatDate(dateString),DateTimeStyle.AGO_SHORT_STRING);
    }
    /**
     * Get time ago of given date
     *
     * @param context    Context
     * @param date Representing a date time string
     * @return Time ago string
     */
    public static String getTimeAgo(Context context, Date date) {
        return getTimeAgo(context, date,DateTimeStyle.AGO_SHORT_STRING);
    }

}