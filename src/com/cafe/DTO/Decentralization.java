package com.cafe.DTO;

public class Decentralization {
    private String decentralizationID;
    private  int isSale;
    private  int isProduct;
    private int isCategory;
    private int isRecipe;
    private int isImport;
    private int isBill;
    private int isWarehouses;
    private int isAccount;
    private int isStaff;
    private int isCustomer;
    private int isDiscount;
    private int isDecentralization;

    private String DecentralizationName;

    private boolean deleted; // khi đọc dữ liệu từ database sẽ so sánh rồi đổi thành kiểu boolean
    public static final int NONE = 0;
    public static final int VIEW = 1;
    public static final int EDIT = 2;
    public static final int ALL = 3;

//    public Decentralization(String s, int i, int parseInt, int anInt, int i1, int parseInt1, int anInt1, int i2, int parseInt2, int anInt2, int i3, int parseInt3, int anInt3, String s1, boolean b) {
//    }

    public Decentralization(String decentralizationID, int isSale, int isProduct, int isCategory, int isRecipe, int isImport, int isBill, int isWarehouses, int isAccount, int isStaff, int isCustomer, int isDiscount, int isDecentralization, String DecentralizationName, boolean deleted) {
        this.decentralizationID = decentralizationID;
        this.isSale = isSale;
        this.isProduct = isProduct;
        this.isCategory = isCategory;
        this.isRecipe = isRecipe;
        this.isImport = isImport;
        this.isBill = isBill;
        this.isWarehouses = isWarehouses;
        this.isAccount = isAccount;
        this.isStaff = isStaff;
        this.isCustomer = isCustomer;
        this.isDiscount = isDiscount;
        this.isDecentralization = isDecentralization;
        this.DecentralizationName = DecentralizationName;
        this.deleted = deleted;
    }

    public String getDecentralizationID() {
        return decentralizationID;
    }

    public void setDecentralizationID(String decentralizationID) {
        this.decentralizationID = decentralizationID;
    }

    public int getIsSale() {
        return isSale;
    }

    public void setIsSale(int isSale) {
        this.isSale = isSale;
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

    public int getIsRecipe() {
        return isRecipe;
    }

    public void setIsRecipe(int isRecipe) {
        this.isRecipe = isRecipe;
    }

    public int getIsImport() {
        return isImport;
    }

    public void setIsImport(int isImport) {
        this.isImport = isImport;
    }

    public int getIsBill() {
        return isBill;
    }

    public void setIsBill(int isBill) {
        this.isBill = isBill;
    }

    public int getIsWarehouses() {
        return isWarehouses;
    }

    public void setIsWarehouses(int isWarehouses) {
        this.isWarehouses = isWarehouses;
    }

    public int getIsAccount() {
        return isAccount;
    }

    public void setIsAccount(int isAccount) {
        this.isAccount = isAccount;
    }

    public int getIsStaff() {
        return isStaff;
    }

    public void setIsStaff(int isStaff) {
        this.isStaff = isStaff;
    }

    public int getIsCustomer() {
        return isCustomer;
    }

    public void setIsCustomer(int isCustomer) {
        this.isCustomer = isCustomer;
    }

    public int getIsDiscount() {
        return isDiscount;
    }

    public void setIsDiscount(int isDiscount) {
        this.isDiscount = isDiscount;
    }

    public int getIsDecentralization() {
        return isDecentralization;
    }

    public void setIsDecentralization(int isDecentralization) {
        this.isDecentralization = isDecentralization;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    //            isSale
//            isProduct

    public String getDecentralizationName() {
        return DecentralizationName;
    }

    public void setDecentralizationName(String decentralizationName) {
        DecentralizationName = decentralizationName;
    }
//            isCategory
//            isRecipe
//            isImport
//            isBill
//            isWarehouses
//            isAccount
//            isStaff
//            isCustomer
//            isDiscount
//            isDecentralization


    @Override
    public String toString() {
        return "Decentralization{" +
            "decentralizationID='" + decentralizationID + '\'' +
            ", isSale=" + isSale +
            ", isProduct=" + isProduct +
            ", isCategory=" + isCategory +
            ", isRecipe=" + isRecipe +
            ", isImport=" + isImport +
            ", isBill=" + isBill +
            ", isWarehouses=" + isWarehouses +
            ", isAccount=" + isAccount +
            ", isStaff=" + isStaff +
            ", isCustomer=" + isCustomer +
            ", isDiscount=" + isDiscount +
            ", isDecentralization=" + isDecentralization +
            ", DecentralizationName=" + DecentralizationName +
            ", deleted=" + deleted +
            '}';
    }
}
