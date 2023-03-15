package com.cafe.DTO;

public class Category {
    private String categoryID, name;
    private int quantity;
    private boolean deleted; // khi đọc dữ liệu từ database sẽ so sánh rồi đổi thành kiểu boolean

    public Category() {
    }

    public Category(String categoryID, String name, int quantity, boolean deleted) {
        this.categoryID = categoryID;
        this.name = name;
        this.quantity = quantity;
        this.deleted = deleted;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return categoryID + "\t" +
            name + "\t" +
            quantity;
    }
}
