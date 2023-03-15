package com.cafe.DTO;

public class ReceiptDetails {
    private String receiptID, ingredientID, supplierID;
    private double quantity;

    public ReceiptDetails() {
    }

    public ReceiptDetails(String receiptID, String ingredientID, double quantity, String supplierID) {
        this.receiptID = receiptID;
        this.ingredientID = ingredientID;
        this.quantity = quantity;
        this.supplierID = supplierID;
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

    @Override
    public String toString() {
        return receiptID + "\t" +
            ingredientID + "\t" +
            quantity + "\t" +
            supplierID;
    }
}
