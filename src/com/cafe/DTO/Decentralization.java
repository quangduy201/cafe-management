package com.cafe.DTO;

import java.util.ArrayList;
import java.util.List;

public class Decentralization {
    private String decentralizationID;
    private int isSale;
    private int isProduct;
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
    private final List<Integer> array = new ArrayList<>();
    public static final int NONE = 0;
    public static final int VIEW = 1;
    public static final int EDIT = 2;
    public static final int ALL = 3;

//    public Decentralization(String s, int i, int parseInt, int anInt, int i1, int parseInt1, int anInt1, int i2, int parseInt2, int anInt2, int i3, int parseInt3, int anInt3, String s1, boolean b) {
//    }


    public Decentralization() {

    }

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
        setArray();
    }
    public Decentralization(String decentralizationID, List<String> agrs, String DecentralizationName, boolean deleted) {
        this.decentralizationID = decentralizationID;
        for (int i = 0; i<agrs.size(); i++) {
            int value = 0;
            switch (agrs.get(i)){
                case "Không" -> value = NONE;
                case "Xem" -> value = VIEW;
                case "Sửa" -> value = EDIT;
                case "Xem và sửa" -> value = ALL;
                default -> {}
            }
            switch (i) {
                case 0 -> this.isSale = 0;
                case 1 -> this.isProduct = 0;
                case 2 -> this.isCategory = 0;
                case 3 -> this.isRecipe = 0;
                case 4 -> this.isImport = 0;
                case 5 -> this.isBill = 0;
                case 6 -> this.isWarehouses = 0;
                case 7 -> this.isAccount = 0;
                case 8 -> this.isStaff = 0;
                case 9 -> this.isCustomer = 0;
                case 10 -> this.isDiscount = 0;
                case 11 -> this.isDecentralization = 0;
                default -> {
                }
            }
        }
        this.DecentralizationName = DecentralizationName;
        this.deleted = deleted;
        setArray();
    }

    public void setArray(){
        array.add(this.isSale);
        array.add(this.isProduct);
        array.add(this.isCategory);
        array.add(this.isRecipe);
        array.add(this.isImport);
        array.add(this.isBill);
        array.add(this.isWarehouses);
        array.add(this.isAccount);
        array.add(this.isStaff);
        array.add(this.isCustomer);
        array.add(this.isDiscount);
        array.add(this.isDecentralization);
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
        String string = "";
        for (int i : array) {
            switch (i){
                case NONE -> string += "Không | ";
                case VIEW -> string += "Xem | ";
                case EDIT -> string += "Sửa | ";
                case ALL -> string += "Xem và sửa | ";
                default -> {}
            }
        }
        return decentralizationID + " | " + string + DecentralizationName;
    }
}
