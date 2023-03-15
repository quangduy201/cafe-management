package com.cafe.DTO;

public class Recipe {
    private String productID, ingredientID, unit;
    private double mass;

    public Recipe() {
    }

    public Recipe(String productID, String ingredientID, double mass, String unit) {
        this.productID = productID;
        this.ingredientID = ingredientID;
        this.mass = mass;
        this.unit = unit;
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

    @Override
    public String toString() {
        return productID + "\t" +
            ingredientID + "\t" +
            mass + "\t" +
            unit;
    }
}
