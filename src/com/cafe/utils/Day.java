package com.cafe.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Day {
    private int date;
    private int month;
    private int year;

    public Day() {
        LocalDate localDate = LocalDate.now();
        this.date = localDate.getDayOfMonth();
        this.month = localDate.getMonthValue();
        this.year = localDate.getYear();
    }

    public Day(int date, int month, int year) {
        this.date = date;
        this.month = month;
        this.year = year;
    }

    public Day(Day day) {
        this.date = day.date;
        this.month = day.month;
        this.year = day.year;
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
        return year >= 1000 && year <= 9999 &&
            month > 0 && month <= 12 &&
            date > 0 && date <= numOfDays(month, year);
    }

    public static boolean isValidDay(Day day) {
        return day.year >= 1000 && day.year <= 9999 &&
            day.month > 0 && day.month <= 12 &&
            day.date > 0 && day.date <= numOfDays(day.month, day.year);
    }

    public static int calculateDays(Day day1, Day day2) {
        if (day1.isAfter(day2))
            return calculateDays(day2, day1);
        if (day1.year < day2.year)
            return calculateDays(day1, new Day(31, 12, day1.year))
                + calculateDays(new Day(1, 1, day1.year + 1), day2);
        if (day1.month < day2.month)
            return calculateDays(day1, new Day(numOfDays(day1.month, day1.year), day1.month, day1.year))
                + calculateDays(new Day(1, day1.month + 1, day1.year), day2);
        return day2.date - day1.date;
    }

    public static Day parseDay(String str) throws Exception {
        int date, month, year;
        if (str.matches("^\\d{4}-(0?[1-9]|1[0-2])-(0?[1-9]|[12][0-9]|3[01])$")) {
            String[] temp = str.split("-");
            year = Integer.parseInt(temp[0]);
            month = Integer.parseInt(temp[1]);
            date = Integer.parseInt(temp[2]);
        } else if (str.matches("^(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[0-2])/\\d{4}$")) {
            String[] temp = str.split("/");
            date = Integer.parseInt(temp[0]);
            month = Integer.parseInt(temp[1]);
            year = Integer.parseInt(temp[2]);
        } else {
            throw new Exception("Invalid day.");
        }
        if (!Day.isValidDay(date, month, year))
            throw new Exception("Invalid day.");
        return new Day(date, month, year);
    }

    public static String now() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm:ss a"));
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
        assert object.getClass().equals(Day.class);
        Day day = (Day) object;
        return year == day.year && month == day.month && date == day.date;
    }

    public boolean isBetween(Day day1, Day day2) {
        return (isAfter(day1) || equals(day1)) && (isBefore(day2) || equals(day2));
    }

    public boolean isAfter(Day day) {
        if (this.year != day.year) {
            return this.year > day.year;
        } else if (this.month != day.month) {
            return this.month > day.month;
        } else {
            return this.date > day.date;
        }
    }

    public boolean isBefore(Day day) {
        if (this.year != day.year) {
            return this.year < day.year;
        } else if (this.month != day.month) {
            return this.month < day.month;
        } else {
            return this.date < day.date;
        }
    }

    public Day after(int days, int months, int years) {
        int newYear = year + years;
        int newMonth = month + months;
        int newDate = date + days;
        while (newMonth > 12) {
            newMonth -= 12;
            newYear++;
        }
        while (newDate > numOfDays(newMonth, newYear)) {
            newDate -= numOfDays(newMonth, newYear);
            newMonth++;
            if (newMonth > 12) {
                newMonth = 1;
                newYear++;
            }
        }
        return new Day(newDate, newMonth, newYear);
    }

    public Day before(int days, int months, int years) {
        int newYear = year - years;
        int newMonth = month - months;
        int newDate = date - days;
        while (newMonth < 1) {
            newMonth += 12;
            newYear--;
        }
        while (newDate < 1) {
            newMonth--;
            if (newMonth < 1) {
                newMonth = 12;
                newYear--;
            }
            newDate += numOfDays(newMonth, newYear);
        }
        return new Day(newDate, newMonth, newYear);
    }

    public Date toDate() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setLenient(false);
        return formatter.parse(date + "/" + month + "/" + year);
    }

    public Date toDateSafe() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setLenient(false);
        try {
            return formatter.parse(date + "/" + month + "/" + year);
        } catch (Exception ignored) {
            return new Date();
        }
    }

    public LocalDate toLocalDate() {
        return LocalDate.parse(toMySQLString());
    }

    public String toMySQLString() {
        // yyyy-MM-dd
        return String.format("%04d-%02d-%02d", year, month, date);
    }

    @Override
    public String toString() {
        // dd/MM/yyyy
        return String.format("%02d/%02d/%04d", date, month, year);
    }
}
