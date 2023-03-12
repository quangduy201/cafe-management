package com.cafe.DTO;

public class Recipe {
    private String product_ID, ingredient_ID, unit;
    private double mass;

    public Recipe() {
    }

    public Recipe(String product_ID, String ingredient_ID, String unit, double mass) {
        this.product_ID = product_ID;
        this.ingredient_ID = ingredient_ID;
        this.unit = unit;
        this.mass = mass;
    }

    public String getProduct_ID() {
        return product_ID;
    }

    public void setProduct_ID(String product_ID) {
        this.product_ID = product_ID;
    }

    public String getIngredient_ID() {
        return ingredient_ID;
    }

    public void setIngredient_ID(String ingredient_ID) {
        this.ingredient_ID = ingredient_ID;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }
}
