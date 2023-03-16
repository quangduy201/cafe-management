package com.cafe.DTO;

public class Recipe {
    private String productID;
    private String ingredientID;
    private double mass;
    private String unit;
    private boolean deleted; // khi đọc dữ liệu từ database sẽ so sánh rồi đổi thành kiểu boolean

    public Recipe() {
    }

    public Recipe(String productID, String ingredientID, double mass, String unit, boolean deleted) {
        this.productID = productID;
        this.ingredientID = ingredientID;
        this.mass = mass;
        this.unit = unit;
        this.deleted = deleted;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getIngredientID() {
        return ingredientID;
    }

    public void setIngredientID(String ingredientID) {
        this.ingredientID = ingredientID;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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
            ingredientID + " | " +
            mass + " | " +
            unit;
    }
}
