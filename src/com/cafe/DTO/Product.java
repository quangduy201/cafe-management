package com.cafe.DTO;

public class Product {
    private String productID;
    private String name;
    private String categoryID;
    private String sized;
    private String image;
    private double cost;
    private boolean deleted; // khi đọc dữ liệu từ database sẽ so sánh rồi đổi thành kiểu boolean

    public Product() {
    }

    public Product(String productID, String name, String categoryID, String sized, double cost, String image, boolean deleted) {
        this.productID = productID;
        this.name = name;
        this.categoryID = categoryID;
        this.sized = sized;
        this.cost = cost;
        this.image = image;
        this.deleted = deleted;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getSized() {
        return sized;
    }

    public void setSized(String sized) {
        this.sized = sized;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return productID + " | " +
            name + " | " +
            categoryID + " | " +
            sized + " | " +
            cost + " | " +
            image;
    }
}
