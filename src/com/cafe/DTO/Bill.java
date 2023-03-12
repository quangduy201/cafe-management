package com.cafe.DTO;

import com.cafe.utils.Day;

public class Bill {
    private String bill_ID, customer_ID, staff_ID;
    private Day dayOfPurchase;
    private double total;
    private boolean deleted; // khi đọc dữ liệu từ database sẽ so sánh rồi đổi thành kiểu boolean

    public Bill() {
    }

    public Bill(String bill_ID, String customer_ID, String staff_ID, Day dayOfPurchase, double total, boolean deleted) {
        this.bill_ID = bill_ID;
        this.customer_ID = customer_ID;
        this.staff_ID = staff_ID;
        this.dayOfPurchase = dayOfPurchase;
        this.total = total;
        this.deleted = deleted;
    }

    public String getBill_ID() {
        return bill_ID;
    }

    public void setBill_ID(String bill_ID) {
        this.bill_ID = bill_ID;
    }

    public String getCustomer_ID() {
        return customer_ID;
    }

    public void setCustomer_ID(String customer_ID) {
        this.customer_ID = customer_ID;
    }

    public String getStaff_ID() {
        return staff_ID;
    }

    public void setStaff_ID(String staff_ID) {
        this.staff_ID = staff_ID;
    }

    public Day getDayOfPurchase() {
        return dayOfPurchase;
    }

    public void setDayOfPurchase(Day dayOfPurchase) {
        this.dayOfPurchase = dayOfPurchase;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
