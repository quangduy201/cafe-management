package com.cafe.DTO;

public class Category {
    private String category_ID, name;
    private int quanity;
    private boolean deleted; // khi đọc dữ liệu từ database sẽ so sánh rồi đổi thành kiểu boolean

    public Category() {
    }

    public Category(String category_ID, String name, int quanity, boolean deleted) {
        this.category_ID = category_ID;
        this.name = name;
        this.quanity = quanity;
        this.deleted = deleted;
    }

    public String getCategory_ID() {
        return category_ID;
    }

    public void setCategory_ID(String category_ID) {
        this.category_ID = category_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuanity() {
        return quanity;
    }

    public void setQuanity(int quanity) {
        this.quanity = quanity;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
