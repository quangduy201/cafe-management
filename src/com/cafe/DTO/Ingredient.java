package com.cafe.DTO;

public class Ingredient {
    private String ingredient_ID, name, unit, supplier_ID;
    private double quantity;
    private boolean deleted;// khi đọc dữ liệu từ database sẽ so sánh rồi đổi thành kiểu boolean

    public Ingredient() {
    }

    public Ingredient(String ingredient_ID, String name, String unit, String supplier_ID, double quantity, boolean deleted) {
        this.ingredient_ID = ingredient_ID;
        this.name = name;
        this.unit = unit;
        this.supplier_ID = supplier_ID;
        this.quantity = quantity;
        this.deleted = deleted;
    }

    public String getIngredient_ID() {
        return ingredient_ID;
    }

    public void setIngredient_ID(String ingredient_ID) {
        this.ingredient_ID = ingredient_ID;
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

    public String getSupplier_ID() {
        return supplier_ID;
    }

    public void setSupplier_ID(String supplier_ID) {
        this.supplier_ID = supplier_ID;
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
}
