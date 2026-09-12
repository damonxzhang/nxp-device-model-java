package com.ruoyi.common.utils;

import java.time.LocalDate;

/**
 * @author snkj
 * @create 2023-11-02 11:06
 */
public class LocalDateUtils {

    /**
     * 比较第一个日期是否大于第二个日期
     * @param firstDate 第一个日期
     * @param secondDate 第二个日期
     * @return true-大于;false-不大于
     */
    public static boolean localDateIsBefore(LocalDate firstDate, LocalDate secondDate) {
        return firstDate.isBefore(secondDate);
    }

    /**
     * 比较第一个日期是否小于第二个日期
     * @param firstDate 第一个日期
     * @param secondDate 第二个日期
     * @return true-小于;false-大于
     */
    public static boolean localDateIsAfter(LocalDate firstDate, LocalDate secondDate) {
        return firstDate.isAfter(secondDate);
    }

    /**
     * 比较两个日期是否相等
     * @param firstDate 第一个日期
     * @param secondDate 第二个日期
     * @return true-相等;false-不相等
     */
    public static boolean localDateIsEqual(LocalDate firstDate, LocalDate secondDate) {
        return firstDate.isEqual(secondDate);
    }


}
