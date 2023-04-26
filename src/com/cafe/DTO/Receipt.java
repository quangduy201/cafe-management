package com.cafe.DTO;

import com.cafe.utils.Day;

public class Receipt {
    private String receiptID;
    private String staffID;
    private Day dor;
    private double grandTotal;
    private boolean deleted; // khi đọc dữ liệu từ database sẽ so sánh rồi đổi thành kiểu boolean

    public Receipt() {
    }

    public Receipt(String receiptID, String staffID, Day dor, double grandTotal, boolean deleted) {
        this.receiptID = receiptID;
        this.staffID = staffID;
        this.dor = dor;
        this.grandTotal = grandTotal;
        this.deleted = deleted;
    }

    public String getReceiptID() {
        return receiptID;
    }

    public void setReceiptID(String receiptID) {
        this.receiptID = receiptID;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public Day getDor() {
        return dor;
    }

    public void setDor(Day dor) {
        this.dor = dor;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return receiptID + " | " +
            staffID + " | " +
            dor + " | " +
            grandTotal;
    }
}
