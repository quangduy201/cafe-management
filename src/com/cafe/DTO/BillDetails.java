package com.cafe.DTO;

public class BillDetails {
    private String billID;
    private String productID;
    private int quantity;

    private Double total;

    private Double percent;

    public BillDetails() {
    }

    public BillDetails(String billID, String productID, int quantity, double total, double percent) {
        this.billID = billID;
        this.productID = productID;
        this.quantity = quantity;
        this.total = total;
        this.percent = percent;
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    @Override
    public String toString() {
        return billID + " | " +
            productID + " | " +
            quantity + " | " +
            total + " | " +
            percent;
    }
}
