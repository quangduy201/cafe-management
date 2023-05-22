package com.cafe.DTO;

public class ReceiptDetails {
    private String receiptID;
    private String ingredientID;
    private double quantity;

    public ReceiptDetails() {
    }

    public ReceiptDetails(String receiptID, String ingredientID, double quantity) {
        this.receiptID = receiptID;
        this.ingredientID = ingredientID;
        this.quantity = quantity;
    }

    public String getReceiptID() {
        return receiptID;
    }

    public void setReceiptID(String receiptID) {
        this.receiptID = receiptID;
    }

    public String getIngredientID() {
        return ingredientID;
    }

    public void setIngredientID(String ingredientID) {
        this.ingredientID = ingredientID;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return receiptID + " | " +
            ingredientID + " | " +
            quantity;
    }
}
