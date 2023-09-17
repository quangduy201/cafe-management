package com.cafe.DTO;

public class BillDetails {
    private String billID;
    private String productID;
    private String note;
    private int quantity;
    private double total;
    private double percent;

    public BillDetails() {
    }

    public BillDetails(String billID, String productID, int quantity, String note, double total, double percent) {
        this.billID = billID;
        this.productID = productID;
        this.quantity = quantity;
        this.note = note;
        this.total = total;
        this.percent = percent;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getBillID() {
        return billID;
    }

    public void setBillID(String billID) {
        this.billID = billID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    @Override
    public String toString() {
        return billID + " | " +
            productID + " | " +
            quantity + " | " +
            note + " | " +
            total + " | " +
            percent;
    }
}
