package com.cafe.DTO;

import com.cafe.utils.Day;

public class Bill {
    private String billID;
    private String customerID;
    private String staffID;
    private Day dateOfPurchase;
    private double total;
    private boolean deleted; // khi đọc dữ liệu từ database sẽ so sánh rồi đổi thành kiểu boolean

    public Bill() {
    }

    public Bill(String billID, String customerID, String staffID, Day dateOfPurchase, double total, boolean deleted) {
        this.billID = billID;
        this.customerID = customerID;
        this.staffID = staffID;
        this.dateOfPurchase = dateOfPurchase;
        this.total = total;
        this.deleted = deleted;
    }

    public String getBillID() {
        return billID;
    }

    public void setBillID(String billID) {
        this.billID = billID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public Day getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(Day dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
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

    @Override
    public String toString() {
        return billID + " | " +
            customerID + " | " +
            staffID + " | " +
            dateOfPurchase + " | " +
            total;
    }
}
