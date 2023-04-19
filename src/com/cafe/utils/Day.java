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
        this.date =  localDate.getDayOfMonth();
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
        int days = 0;
        Day temp1, temp2;
        /*
         *      temp1 là ngày nhỏ hơn temp2 (ngày nhỏ hơn sẽ gán vào temp1)
         * nếu day1.year < day2.year thì gán day1 vào temp1 và ngược lại
         * nếu day1.year = day2.year thì xét tháng:
         * nếu day1.month < day2.month thì gán day1 vào temp1 và ngược lại
         * nếu day1.month = day2.month thì xét ngày:
         * nếu day1.date = day2.date thì gán day1 vào temp1 và ngược lại
         */
        if (day1.year < day2.year ||
            day1.year == day2.year && day1.month < day2.month ||
            day1.year == day2.year && day1.month == day2.month && day1.date < day2.date) {
            temp1 = new Day(day1.date, day1.month, day1.year);
            temp2 = new Day(day2.date, day2.month, day2.year);
        } else {
            temp1 = new Day(day2.date, day2.month, day2.year);
            temp2 = new Day(day1.date, day1.month, day1.year);
        }
        while (temp1.year <= temp2.year) {
            if (temp1.year == temp2.year && temp1.month == temp2.month) {
                days += temp2.date - temp1.date;
                break;
            } else {
                days += numOfDays(temp1.month, temp2.year) - temp1.date;
                temp1.month++;
                temp1.date = 0;
                if (temp1.month > 12) {
                    temp1.year++;
                    temp1.month = 1;
                }
            }
        }
        return days;
    }

    public boolean compareDates(Day day) {
        if (this.year > day.getYear()) {
            return true;
        } else if (this.year < day.getYear()) {
            return false;
        } else {
            if (this.month > day.getMonth()) {
                return true;
            } else if (this.month < day.getMonth()) {
                return false;
            } else {
                if (this.date> day.getDate()) {
                    return true;
                } else {
                    return false;
                }
            }
        }
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
        return getDate() == day.getDate() && getMonth() == day.getMonth() && getYear() == day.getYear();
    }

    public boolean compare(Day day1, Day day2) {
        return getDate() >= day1.getDate() && getMonth() >= day1.getMonth() && getYear() >= day1.getYear() && getDate() <= day2.getDate() && getMonth() <= day2.getMonth() && getYear() <= day2.getYear();
    }

    @Override
    public String toString() {
        // dd-MM-yyyy
        String yearString = String.valueOf(year);
        int count = 4 - yearString.length();
        yearString = "0".repeat(count) + yearString;
        return yearString + "-" +
            ((month < 10) ? "0" : "") + month + "-" +
            ((date < 10) ? "0" : "") + date;
    }
}
