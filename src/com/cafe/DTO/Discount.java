package com.cafe.DTO;

import com.cafe.utils.Day;

public class Discount {
    private String discountID;
    private int discountPercent;
    private Day startDay;
    private Day endDay;
    private int status;
    private boolean deleted; // khi đọc dữ liệu từ database sẽ so sánh rồi đổi thành kiểu boolean

    public Discount() {
    }

    public Discount(String discountID, int discountPercent, Day startDay, Day endDay, int status, boolean deleted) {
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

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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
        String str = status == 0 ? "Đang áp dụng" : "Ngừng áp dụng";
        return discountID + " | " +
            discountPercent + " | " +
            startDay + " | " +
            endDay + " | " +
            str;
    }
}
