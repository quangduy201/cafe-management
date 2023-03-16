package com.cafe.DTO;

import com.cafe.utils.Day;

public class Discount {
    private String discountID;
    private double discountPercent;
    private Day startDay;
    private Day endDay;
    private String status;
    private boolean deleted; // khi đọc dữ liệu từ database sẽ so sánh rồi đổi thành kiểu boolean

    public Discount() {
    }

    public Discount(String discountID, double discountPercent, Day startDay, Day endDay, String status, boolean deleted) {
        this.discountID = discountID;
        this.discountPercent = discountPercent;
        this.startDay = startDay;
        this.endDay = endDay;
        this.status = status;
        this.deleted = deleted;
    }

    public String getDiscountID() {
        return discountID;
    }

    public void setDiscountID(String discountID) {
        this.discountID = discountID;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Day getStartDay() {
        return startDay;
    }

    public void setStartDay(Day startDay) {
        this.startDay = startDay;
    }

    public Day getEndDay() {
        return endDay;
    }

    public void setEndDay(Day endDay) {
        this.endDay = endDay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return discountID + " | " +
            status + " | " +
            discountPercent + " | " +
            startDay + " | " +
            endDay;
    }
}
