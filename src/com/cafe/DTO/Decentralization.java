package com.cafe.DTO;

public class Decentralization {
    private String decentralizationID;
    private int isRecipe;
    private int isProduct;
    private int isCategory;
    private int isBill;
    private int isDiscount;
    private int isCustomer;
    private int isWarehouses;
    private int isStaff;
    private int isAccount;
    private int isDecentralize;
    private String decentralizationName;
    private boolean deleted; // khi đọc dữ liệu từ database sẽ so sánh rồi đổi thành kiểu boolean
    public static final int NONE = 0;
    public static final int VIEW = 1;
    public static final int EDIT = 2;
    public static final int ALL = 3;

    public Decentralization() {
    }

    public Decentralization(String decentralizationID, int isRecipe, int isProduct, int isCategory, int isBill, int isDiscount, int isCustomer, int isWarehouses, int isStaff, int isAccount, int isDecentralize, String decentralizationName, boolean deleted) {
        this.decentralizationID = decentralizationID;
        this.isRecipe = isRecipe;
        this.isProduct = isProduct;
        this.isCategory = isCategory;
        this.isBill = isBill;
        this.isDiscount = isDiscount;
        this.isCustomer = isCustomer;
        this.isWarehouses = isWarehouses;
        this.isStaff = isStaff;
        this.isAccount = isAccount;
        this.isDecentralize = isDecentralize;
        this.decentralizationName = decentralizationName;
        this.deleted = deleted;
    }

    public String getDecentralizationID() {
        return decentralizationID;
    }

    public void setDecentralizationID(String decentralizationID) {
        this.decentralizationID = decentralizationID;
    }

    public int getIsRecipe() {
        return isRecipe;
    }

    public void setIsRecipe(int isRecipe) {
        this.isRecipe = isRecipe;
    }

    public int getIsProduct() {
        return isProduct;
    }

    public void setIsProduct(int isProduct) {
        this.isProduct = isProduct;
    }

    public int getIsCategory() {
        return isCategory;
    }

    public void setIsCategory(int isCategory) {
        this.isCategory = isCategory;
    }

    public int getIsBill() {
        return isBill;
    }

    public void setIsBill(int isBill) {
        this.isBill = isBill;
    }

    public int getIsDiscount() {
        return isDiscount;
    }

    public void setIsDiscount(int isDiscount) {
        this.isDiscount = isDiscount;
    }

    public int getIsCustomer() {
        return isCustomer;
    }

    public void setIsCustomer(int isCustomer) {
        this.isCustomer = isCustomer;
    }

    public int getIsWarehouses() {
        return isWarehouses;
    }

    public void setIsWarehouses(int isWarehouses) {
        this.isWarehouses = isWarehouses;
    }

    public int getIsStaff() {
        return isStaff;
    }

    public void setIsStaff(int isStaff) {
        this.isStaff = isStaff;
    }

    public int getIsAccount() {
        return isAccount;
    }

    public void setIsAccount(int isAccount) {
        this.isAccount = isAccount;
    }

    public int getIsDecentralize() {
        return isDecentralize;
    }

    public void setIsDecentralize(int isDecentralize) {
        this.isDecentralize = isDecentralize;
    }

    public String getDecentralizationName() {
        return decentralizationName;
    }

    public void setDecentralizationName(String decentralizationName) {
        this.decentralizationName = decentralizationName;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return decentralizationID + " | " +
            isRecipe + " | " +
            isProduct + " | " +
            isCategory + " | " +
            isBill + " | " +
            isDiscount + " | " +
            isCustomer + " | " +
            isWarehouses + " | " +
            isStaff + " | " +
            isAccount + " | " +
            isDecentralize + " | " +
            decentralizationName;
    }
}
