package com.cafe.DTO;

public class ReceiptDetails {
    private String receipt_ID, ingredient_ID, supplier_ID;
    private double quantity;

    public ReceiptDetails() {
    }

    public ReceiptDetails(String receipt_ID, String ingredient_ID, String supplier_ID, double quantity) {
        this.receipt_ID = receipt_ID;
        this.ingredient_ID = ingredient_ID;
        this.supplier_ID = supplier_ID;
        this.quantity = quantity;
    }

    public String getReceipt_ID() {
        return receipt_ID;
    }

    public void setReceipt_ID(String receipt_ID) {
        this.receipt_ID = receipt_ID;
    }

    public String getIngredient_ID() {
        return ingredient_ID;
    }

    public void setIngredient_ID(String ingredient_ID) {
        this.ingredient_ID = ingredient_ID;
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
}
