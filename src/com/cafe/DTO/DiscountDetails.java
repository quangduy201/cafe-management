package com.cafe.DTO;

public class DiscountDetails {
    private String discount_ID, product_ID;

    public DiscountDetails() {
    }

    public DiscountDetails(String discount_ID, String product_ID) {
        this.discount_ID = discount_ID;
        this.product_ID = product_ID;
    }

    public String getDiscount_ID() {
        return discount_ID;
    }

    public void setDiscount_ID(String discount_ID) {
        this.discount_ID = discount_ID;
    }

    public String getProduct_ID() {
        return product_ID;
    }

    public void setProduct_ID(String product_ID) {
        this.product_ID = product_ID;
    }
}
