package com.cafe.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Day {
    private int date, month, year;

    public Day() {
        this.date = 0;
        this.month = 0;
        this.year = 0;
    }

    public Day(int date, int month, int year) {
        this.date = date;
        this.month = month;
        this.year = year;
    }

    public Day(Date date) {
        LocalDate localDate = LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
        this.date = localDate.getDayOfMonth();
        this.month = localDate.getMonthValue();
        this.year = localDate.getYear();
    }

    public static boolean isLeapYear(int year) {
        return year % 400 == 0 || (year % 100 != 0 && year % 4 == 0);
    }

    public static int numOfDays(int month, int year) {
        return switch (month) {
            case 2 -> isLeapYear(year) ? 29 : 28;
            case 1, 3, 5, 7, 8, 10, 12 -> 31;
            case 4, 6, 9, 11 -> 30;
            default -> 0;
        };
    }

    public static boolean isValidDay(int date, int month, int year) {
        return year >= 100 && year <= 2030 &&
            month > 0 && month <= 12 &&
            date > 0 && date <= numOfDays(month, year);
    }

    public static boolean isValidDay(Day day) {
        return day.year > 1930 && day.year <= 2030 &&
            day.month > 0 && day.month <= 12 &&
            day.date > 0 && day.date <= numOfDays(day.month, day.year);
    }

    public static int calculateDays(Day day1, Day day2) {
        Day start = day1.compareDates(day2) ? day2 : day1;
        Day end = day1.compareDates(day2) ? day1 : day2;
        int days = 0;
        while (end.compareDates(start)) {
            days++;
            start = start.nextDate();
        }
        return days;
    }

    public static Day parseDay(String str) throws Exception {
        String[] temp = str.split("-");
        int date, month, year;
        try {
            year = Integer.parseInt(temp[0]);
            month = Integer.parseInt(temp[1]);
            date = Integer.parseInt(temp[2]);
            if (!Day.isValidDay(date, month, year))
                throw new Exception();
        } catch (Exception e) {
            throw new Exception();
        }
        return new Day(date, month, year);
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object object) {
        Day day = (Day) object;
        return year == day.getYear() && month == day.getMonth() && date == day.getDate();
    }

    public boolean isBetween(Day day1, Day day2) {
        return year >= day1.getYear() && month >= day1.getMonth() && date >= day1.getDate()
            && year <= day2.getYear() && month <= day2.getMonth() && date <= day2.getDate();
    }

    public boolean compareDates(Day day) {
        if (this.year != day.getYear()) {
            return this.year > day.getYear();
        } else if (this.month != day.getMonth()) {
            return this.month > day.getMonth();
        } else {
            return this.date > day.getDate();
        }
    }

    public Day nextDate() {
        if (this.date == numOfDays(this.month, this.year)) {
            if (this.month == 12)
                return new Day(1, 1, this.year + 1);
            else
                return new Day(1, this.month + 1, this.year);
        } else
            return new Day(this.date + 1, this.month, this.year);
    }

    public Day previousDate() {
        if (this.date == 1) {
            if (this.month == 1)
                return new Day(31, 12, this.year - 1);
            else
                return new Day(numOfDays(this.month - 1, this.year), this.month - 1, this.year);
        } else
            return new Day(this.date - 1, this.month, this.year);
    }

    @Override
    public String toString() {
        // yyyy-MM-dd
        return String.format("%04d-%02d-%02d", year, month, date);
    }
}
