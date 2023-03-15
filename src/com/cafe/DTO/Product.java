package com.cafe.DTO;

public class Product {
    private String productID, name, categoryID, size;
    private double cost;
    private boolean deleted; // khi đọc dữ liệu từ database sẽ so sánh rồi đổi thành kiểu boolean

    public Product() {
    }

    public Product(String productID, String name, String categoryID, String size, double cost, boolean deleted) {
        this.productID = productID;
        this.name = name;
        this.categoryID = categoryID;
        this.cost = cost;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return productID + "\t" +
            name + "\t" +
            categoryID + "\t" +
            size + "\t" +
            cost;
    }
}
