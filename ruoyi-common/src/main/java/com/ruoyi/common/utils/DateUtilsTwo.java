package com.ruoyi.common.utils;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 时间工具类
 * 
 * @author ruoyi
 */
public class DateUtilsTwo extends org.apache.commons.lang3.time.DateUtils {
    private static Map<String, ThreadLocal<DateFormat>> thredlocalmap = new Hashtable<String, ThreadLocal<DateFormat>>();

    public static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyyMMdd = "yyyy-MM-dd";
    public static final String yyyyMM = "yyyy-MM";
    public static final String yyyy = "yyyy";
    public static final String HHmmss = "HH:mm:ss";
    public static final String MM = "MM";
    public static final String week = "E";

    static {
        thredlocalmap.put(yyyyMMddHHmmss,  new ThreadLocal<DateFormat>());
        thredlocalmap.put(yyyyMMdd,  new ThreadLocal<DateFormat>());
        thredlocalmap.put(yyyyMM,  new ThreadLocal<DateFormat>());
        thredlocalmap.put(yyyy,  new ThreadLocal<DateFormat>());
        thredlocalmap.put(HHmmss,  new ThreadLocal<DateFormat>());
        thredlocalmap.put(MM,  new ThreadLocal<DateFormat>());
        thredlocalmap.put(week,  new ThreadLocal<DateFormat>());
    }

    /**
     * 通过格式获取格式化对象,线程安全
     *
     * @param datePattern
     * @return
     */
    public static DateFormat getDateFormat(String datePattern) {
        ThreadLocal<DateFormat> threadlocal = getThreadLocal(datePattern);
        DateFormat fmt = threadlocal.get();
        if (fmt == null) {
            fmt = new SimpleDateFormat(datePattern);
            threadlocal.set(fmt);
        }
        return fmt;
    }

    private static ThreadLocal<DateFormat> getThreadLocal(String datePattern) {
        ThreadLocal<DateFormat> theadlocal = thredlocalmap.get(datePattern);
        if(theadlocal == null) {
            theadlocal = new ThreadLocal<DateFormat>();
            thredlocalmap.put(datePattern, theadlocal);
        }
        return theadlocal;
    }

    /**
     * 将字符串日期转换成Date对象
     *
     * @param dateStr 日期字符串
     * @param datePattern 日期格式
     * @return
     */
    public static Date parse(String dateStr, String datePattern) {
        try {
            return getDateFormat(datePattern).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将Date对象转换成字符串日期
     *
     * @param date 日期对象
     * @param datePattern 日期格式
     * @return
     */
    public static String format(Date date, String datePattern) {
        return getDateFormat(datePattern).format(date);
    }

    /**
     * 取得特定时间对应的字符串,格式化为yyyy-MM-dd HH:mm:ss
     *
     * @param date 日期对象
     * @return
     */
    public static String ymdhmsFormat(Date date) {
        return format(date, yyyyMMddHHmmss);
    }

    /**
     * 取得特定时间对应的字符串,格式化为yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String ymdFormat(Date date) {
        return format(date, yyyyMMdd);
    }

    /**
     * 根据当前日期,得到当前年月
     *
     * @param date
     * @return str
     */
    public static final String ymFormat(Date date) {
        if (date == null)
            return "";
        return format(date, yyyyMM);
    }

    /**
     * 根据当前日期,得到当前年份
     *
     * @param date
     * @return str
     */
    public static final String yFormat(Date date) {
        if (date == null)
            return "";
        return format(date, yyyy);
    }

    /**
     * 根据当前日期,得到当前月份
     *
     * @param date
     * @return str
     */
    public static final String mFormat(Date date) {
        if (date == null)
            return "";
        return format(date, MM);
    }

    /**
     * 返回当前的时间，格式为H:mm:ss
     *
     * @return 时间字符串
     */
    public static final String getTimeNow() {
        return format(new Date(), HHmmss);
    }

    /**
     * 把字符串形式转换成日期形式，字符串的格式必须为yyyy-MM-dd
     *
     * @param ymdStringDate
     * @return date
     */
    public static final Date ymdString2Date(String ymdStringDate) {
        if (ymdStringDate == null)
            return null;
        return parse(ymdStringDate, yyyyMMdd);
    }

    /**
     * 把字符串形式转换成日期形式，字符串的格式必须为yyyy-MM-dd HH:mm:ss
     *
     * @param ymdhmsStringDate
     * @return date
     */
    public static final Date ymdhmsString2Date(String ymdhmsStringDate) {
        if (ymdhmsStringDate == null)
            return null;
        return parse(ymdhmsStringDate, yyyyMMddHHmmss);
    }

    /**
     *
     * 得到当前时间,把日期后的时间归0 变成(yyyy-MM-dd 00:00:00:000)
     *
     * @return date
     */
    public static Date getCurrentDate() {
        Date date = new Date();
        return zerolizedTime(date);
    }

    /**
     * 把日期后的时间归0 变成(yyyy-MM-dd 00:00:00:000)
     *
     * @param fullDate
     * @return Date
     */
    public static final Date zerolizedTime(Date fullDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fullDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 得到两个时间的间隔
     *
     * @param bDate
     * @param eDate
     * @return
     */
    static public long dateDiffByDay(Date bDate, Date eDate) {
        if (bDate == null || eDate == null)
            return 0L;
        return (bDate.getTime() - eDate.getTime()) / (1000 * 3600 * 24);
    }


    /**
     * 取得指定日期的星期数
     *
     * @return String
     */
    public static final String getWeek(Date date) {
        if (date == null)
            return null;
        return format(date, week);
    }

    /**
     * 判断两个日期字符串是否相等,格式必需为yyyy-MM-dd
     *
     * @param one
     *            第一个日期字符串
     * @param two
     *            第二个日期字符串
     * @return Boolean
     */
    public static final boolean  isEqual(String one, String two) {
        return ymdString2Date(one).equals(ymdString2Date(two));
    }

    /**
     * 判断两个日期字符串是否相等
     *
     * @param one
     *            第一个日期字符串
     * @param two
     *            第二个日期字符串
     * @param datePattern
     *            包含日期格式的字符串
     * @return Boolean
     */
    public static final boolean  isEqual(String one, String two, String datePattern) {

        return isEqual(one, two, datePattern, datePattern);
    }

    /**
     * 判断两个日期字符串是否相等
     *
     * @param one
     *            第一个日期字符串
     * @param two
     *            第二个日期字符串
     * @param datePatternOne
     *            对应第一个日期字符串的包含日期格式的字符串
     * @param datePatternTwo
     *            对应第二个日期字符串的包含日期格式的字符串
     * @return Boolean
     */
    public static final Boolean isEqual(String one, String two,
                                        String datePatternOne, String datePatternTwo) {

        return parse(one, datePatternOne).equals(parse(two, datePatternTwo));
    }

    /**
     * 返回两时间的时间间隔（以分计算）
     *
     * @param date1
     * @param date2
     * @return
     */
    static public long spaceMinute(Date date1, Date date2) {
        Long num1 = date1.getTime();
        Long num2 = date2.getTime();
        Long space = (num2 - num1) / (1000 * 60);
        return space;
    }

    /**
     * 返回两时间的时间间隔（以天计算）
     *
     * @paramtime1
     * @paramtime2
     * @return
     */
    static public Long spaceDay(Date date1, Date date2) {
        Long num1 = date1.getTime();
        Long num2 = date2.getTime();
        Long space = (num2 - num1) / (1000 * 3600 * 24);
        return space;
    }

    static public Date getDateAfterDay(Date somedate, int day) {
        if (somedate == null)
            return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(somedate);
        cal.add(Calendar.DAY_OF_MONTH, day);
        return new Date(cal.getTime().getTime());
    }

    static public Date getDateAfterDay(int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    static public Date getTSAfterDay(Date somedate, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(somedate);
        cal.add(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    // 取得本月第一天时间
    static public Date getFirstDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * 获取两个时间之间的天数
     * @param maxDate 大的日期
     * @param minDate 小的日期
     * @return
     * @throws Exception
     */
    public static int getDay(Date maxDate,Date minDate) throws Exception {
        int day = 0;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        minDate=sdf.parse(sdf.format(minDate));
        maxDate=sdf.parse(sdf.format(maxDate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(minDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(maxDate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);
        if (between_days>0) {
            day = Integer.parseInt(String.valueOf(between_days));
        }
        return day;
    }
}
