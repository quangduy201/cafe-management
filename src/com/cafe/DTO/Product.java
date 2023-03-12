package com.cafe.DTO;

public class Product {
    private String product_ID, name, category_ID;
    private double cost;
    private boolean deleted;// khi đọc dữ liệu từ database sẽ so sánh rồi đổi thành kiểu boolean

    public Product() {
    }

    public Product(String product_ID, String name, String category_ID, double cost, boolean deleted) {
        this.product_ID = product_ID;
        this.name = name;
        this.category_ID = category_ID;
        this.cost = cost;
        this.deleted = deleted;
    }

    public String getProduct_ID() {
        return product_ID;
    }

    public void setProduct_ID(String product_ID) {
        this.product_ID = product_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory_ID() {
        return category_ID;
    }

    public void setCategory_ID(String category_ID) {
        this.category_ID = category_ID;
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
}
