package com.cafe.DTO;

import com.cafe.utils.Day;

public class Discount {
    private String discount_ID, status;
    private double discount_Percent;
    private Day start_Day, end_Day;
    private boolean deleted; // khi đọc dữ liệu từ database sẽ so sánh rồi đổi thành kiểu boolean

    public Discount() {
    }

    public Discount(String discount_ID, String status, double discount_Percent, Day start_Day, Day end_Day, boolean deleted) {
        this.discount_ID = discount_ID;
        this.status = status;
        this.discount_Percent = discount_Percent;
        this.start_Day = start_Day;
        this.end_Day = end_Day;
        this.deleted = deleted;
    }

    public String getDiscount_ID() {
        return discount_ID;
    }

    public void setDiscount_ID(String discount_ID) {
        this.discount_ID = discount_ID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getDiscount_Percent() {
        return discount_Percent;
    }

    public void setDiscount_Percent(double discount_Percent) {
        this.discount_Percent = discount_Percent;
    }

    public Day getStart_Day() {
        return start_Day;
    }

    public void setStart_Day(Day start_Day) {
        this.start_Day = start_Day;
    }

    public Day getEnd_Day() {
        return end_Day;
    }

    public void setEnd_Day(Day end_Day) {
        this.end_Day = end_Day;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
