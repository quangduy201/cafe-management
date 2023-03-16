package com.cafe.DTO;

public class DiscountDetails {
    private String discountID;
    private String productID;

    public DiscountDetails() {
    }

    public DiscountDetails(String discountID, String productID) {
        this.discountID = discountID;
        this.productID = productID;
    }

    public String getDiscountID() {
        return discountID;
    }

    public void setDiscountID(String discountID) {
        this.discountID = discountID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    @Override
    public String toString() {
        return discountID + " | " +
            productID;
    }
}
