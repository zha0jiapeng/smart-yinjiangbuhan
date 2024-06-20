package com.rubin.rpan.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * 日期工具类
 * Created by RubinChu on 2021/1/22 下午 4:11
 */
public class DateUtil {

    /**
     * 标准的日期转换格式
     */
    private static final String STD_DAY_STR_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 标准时间转换类
     */
    private static final SimpleDateFormat STD_FORMAT = new SimpleDateFormat(STD_DAY_STR_FORMAT);

    /**
     * 日历实体
     */
    private static final Calendar CALENDAR_INSTANCE = Calendar.getInstance();;

    /**
     * 获取当前时间N天后的日期
     *
     * @param days
     * @return
     */
    public static Date afterDays(Integer days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * 获取特定时间N天后的日期
     *
     * @param date
     * @param days
     * @return
     */
    public static Date afterDays(Date date, Integer days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * 获取当前时间N天前的日期
     *
     * @param days
     * @return
     */
    public static Date beforeDays(Integer days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -days);
        return calendar.getTime();
    }

    /**
     * 获取特定时间N天前的日期
     *
     * @param date
     * @param days
     * @return
     */
    public static Date beforeDays(Date date, Integer days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -days);
        return calendar.getTime();
    }

    /**
     * 标准的日期转换成字符串
     *
     * @param date
     * @return
     */
    public static String stdDate2String(Date date) {
        return STD_FORMAT.format(date);
    }

    /**
     * 获取当前年份
     *
     * @return
     */
    public static int getYear() {
        return getYear(null);
    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static int getMonth() {
        return getMonth(null);
    }

    /**
     * 获取当前是当月的第几天
     *
     * @return
     */
    public static int getDay() {
        return getDay(null);
    }

    /**
     * 获取当前月份
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        return getAttribute(date, Calendar.YEAR);
    }

    /**
     * 获取当前月份
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        return getAttribute(date, Calendar.MONTH) + 1;
    }

    /**
     * 获取当前是当月的第几天
     *
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        return getAttribute(date, Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取日历属性
     *
     * @param date
     * @param mode
     * @return
     */
    public static int getAttribute(Date date, int mode) {
        if (!Objects.isNull(date)) {
            CALENDAR_INSTANCE.setTime(date);
        } else {
            CALENDAR_INSTANCE.setTime(new Date());
        }
        return CALENDAR_INSTANCE.get(mode);
    }

}
