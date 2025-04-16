package com.example.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ScheduleGenerator {
    public static LocalDate getFirstDayOfNextMonth(){
        LocalDate nextMonth = LocalDate.now().plusMonths(1);
        return LocalDate.of(nextMonth.getYear(), nextMonth.getMonth(), 1);
    }

    public static LocalDate getLastDayOfNextMonth(LocalDate firstDayOfMonth){
        return firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth());
    }

    public static List<LocalDate> generateDate(LocalDate firstDayOfMonth,LocalDate lastDayOfMonth,String dayStr){
        List<LocalDate> scheduleDates = new ArrayList<>();
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(dayStr.toUpperCase());

        // 获取本月第一天是周几
        DayOfWeek startDayOfWeek = firstDayOfMonth.getDayOfWeek();

        // 计算第一个匹配的日期
        LocalDate currentDate = firstDayOfMonth;
        if (!startDayOfWeek.equals(dayOfWeek)) {
            int daysToAdd = (dayOfWeek.getValue() - startDayOfWeek.getValue() + 7) % 7;
            currentDate = firstDayOfMonth.plusDays(daysToAdd);
        }

        // 遍历本月所有符合条件的日期
        while (!currentDate.isAfter(lastDayOfMonth)) {
            scheduleDates.add(currentDate);
            currentDate = currentDate.plusWeeks(1); // 移动到下一周相同的日子
        }

        return scheduleDates;
    }
}
