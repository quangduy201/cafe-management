package com.cafe.DTO;

public class DiscountDetails {
    private String discountID;
    private String productID;
    private boolean deleted; // khi đọc dữ liệu từ database sẽ so sánh rồi đổi thành kiểu boolean


    public DiscountDetails() {
    }

    public DiscountDetails(String discountID, String productID, boolean deleted) {
        this.discountID = discountID;
        this.productID = productID;
        this.deleted = deleted;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return discountID + " | " +
            productID;
    }
}
