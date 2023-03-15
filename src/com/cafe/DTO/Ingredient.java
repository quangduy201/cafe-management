package com.cafe.DTO;

public class Ingredient {
    private String ingredientID, name, unit, supplierID;
    private double quantity;
    private boolean deleted; // khi đọc dữ liệu từ database sẽ so sánh rồi đổi thành kiểu boolean

    public Ingredient() {
    }

    public Ingredient(String ingredientID, String name, double quantity, String unit, String supplierID, boolean deleted) {
        this.ingredientID = ingredientID;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
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
        return ingredientID + "\t" +
            name + "\t" +
            quantity + "\t" +
            unit + "\t" +
            supplierID;
    }
}
