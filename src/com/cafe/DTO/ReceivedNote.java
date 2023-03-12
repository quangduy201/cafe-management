package com.cafe.DTO;

import com.cafe.utils.Day;

public class ReceivedNote {
    private String receipt_ID, staff_ID;
    private Day DOR;
    private double grand_Total;
    private boolean deleted;

    public ReceivedNote() {
    }

    public ReceivedNote(String receipt_ID, String staff_ID, Day DOR, double grand_Total, boolean deleted) {
        this.receipt_ID = receipt_ID;
        this.staff_ID = staff_ID;
        this.DOR = DOR;
        this.grand_Total = grand_Total;
        this.deleted = deleted;
    }

    public String getReceipt_ID() {
        return receipt_ID;
    }

    public void setReceipt_ID(String receipt_ID) {
        this.receipt_ID = receipt_ID;
    }

    public String getStaff_ID() {
        return staff_ID;
    }

    public void setStaff_ID(String staff_ID) {
        this.staff_ID = staff_ID;
    }

    public Day getDOR() {
        return DOR;
    }

    public void setDOR(Day DOR) {
        this.DOR = DOR;
    }

    public double getGrand_Total() {
        return grand_Total;
    }

    public void setGrand_Total(double grand_Total) {
        this.grand_Total = grand_Total;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
