package com.cafe.DTO;

public class Ingredient {
    private String ingredientID;
    private String name;
    private double quantity;
    private String unit;
    private double unit_price;
    private String supplierID;
    private boolean deleted; // khi đọc dữ liệu từ database sẽ so sánh rồi đổi thành kiểu boolean

    public Ingredient() {
    }

    public Ingredient(String ingredientID, String name, double quantity, String unit, double unit_price, String supplierID, boolean deleted) {
        this.ingredientID = ingredientID;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.unit_price = unit_price;
        this.supplierID = supplierID;
        this.deleted = deleted;
    }

    public String getIngredientID() {
        return ingredientID;
    }

    public void setIngredientID(String ingredientID) {
        this.ingredientID = ingredientID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return ingredientID + " | " +
            name + " | " +
            quantity + " | " +
            unit + " | " +
            unit_price + " | " +
            supplierID;
    }
}
