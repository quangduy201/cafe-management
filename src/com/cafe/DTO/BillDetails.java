package com.cafe.DTO;

public class BillDetails {
    private String billID, productID;
    private int quantity;

    public BillDetails() {
    }

    public BillDetails(String billID, String productID, int quantity) {
        this.billID = billID;
        this.productID = productID;
        this.quantity = quantity;
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
}
