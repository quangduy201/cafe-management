package com.cafe.DTO;

public class BillDetails {
    private String bill_ID, product_ID;
    private int quantity;

    public BillDetails() {
    }

    public BillDetails(String bill_ID, String product_ID, int quantity) {
        this.bill_ID = bill_ID;
        this.product_ID = product_ID;
        this.quantity = quantity;
    }

    public String getBill_ID() {
        return bill_ID;
    }

    public void setBill_ID(String bill_ID) {
        this.bill_ID = bill_ID;
    }

    public String getProduct_ID() {
        return product_ID;
    }

    public void setProduct_ID(String product_ID) {
        this.product_ID = product_ID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
