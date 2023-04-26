package com.cafe.DTO;

import java.util.ArrayList;
import java.util.List;

public class Decentralization {
    public static final int NONE = 0;
    public static final int VIEW = 1;
    public static final int EDIT = 2;
    public static final int ALL = 3;
    private final List<Integer> array = new ArrayList<>();
    private String decentralizationID;
    private int isSale;
    private int isProduct;
    private int isCategory;
    private int isRecipe;
    private int isImport;
    private int isSupplier;
    private int isBill;
    private int isWarehouses;
    private int isAccount;
    private int isStaff;
    private int isCustomer;
    private int isDiscount;
    private int isDecentralization;
    private String decentralizationName;
    private boolean deleted; // khi đọc dữ liệu từ database sẽ so sánh rồi đổi thành kiểu boolean

    public Decentralization() {

    }

    public Decentralization(String decentralizationID, int isSale, int isProduct, int isCategory, int isRecipe, int isImport, int isSupplier, int isBill, int isWarehouses, int isAccount, int isStaff, int isCustomer, int isDiscount, int isDecentralization, String DecentralizationName, boolean deleted) {
        this.decentralizationID = decentralizationID;
        this.isSale = isSale;
        this.isProduct = isProduct;
        this.isCategory = isCategory;
        this.isRecipe = isRecipe;
        this.isImport = isImport;
        this.isSupplier = isSupplier;
        this.isBill = isBill;
        this.isWarehouses = isWarehouses;
        this.isAccount = isAccount;
        this.isStaff = isStaff;
        this.isCustomer = isCustomer;
        this.isDiscount = isDiscount;
        this.isDecentralization = isDecentralization;
        this.decentralizationName = DecentralizationName;
        this.deleted = deleted;
        setArray();
    }

    public Decentralization(String decentralizationID, List<String> args, String decentralizationName, boolean deleted) {
        this.decentralizationID = decentralizationID;
        for (int i = 0; i < args.size(); i++) {
            int value = switch (args.get(i)) {
                case "Không" -> NONE;
                case "Xem" -> VIEW;
                case "Thêm" -> EDIT;
                case "Sửa và xóa" -> ALL;
                default -> -1;
            };
            switch (i) {
                case 0 -> this.isSale = value;
                case 1 -> this.isProduct = value;
                case 2 -> this.isCategory = value;
                case 3 -> this.isRecipe = value;
                case 4 -> this.isImport = value;
                case 5 -> this.isSupplier = value;
                case 6 -> this.isBill = value;
                case 7 -> this.isWarehouses = value;
                case 8 -> this.isAccount = value;
                case 9 -> this.isStaff = value;
                case 10 -> this.isCustomer = value;
                case 11 -> this.isDiscount = value;
                case 12 -> this.isDecentralization = value;
                default -> {
                }
            }
        }
        this.decentralizationName = decentralizationName;
        this.deleted = deleted;
        setArray();
    }

    public void setArray() {
        array.add(this.isSale);
        array.add(this.isProduct);
        array.add(this.isCategory);
        array.add(this.isRecipe);
        array.add(this.isImport);
        array.add(this.isSupplier);
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

    public int getIsSupplier() {
        return isSupplier;
    }

    public void setIsSupplier(int isSupplier) {
        this.isSupplier = isSupplier;
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
            isSale + " | " +
            isProduct + " | " +
            isCategory + " | " +
            isRecipe + " | " +
            isImport + " | " +
            isSupplier + " | " +
            isBill + " | " +
            isWarehouses + " | " +
            isAccount + " | " +
            isStaff + " | " +
            isCustomer + " | " +
            isDiscount + " | " +
            isDecentralization + " | " +
            decentralizationName;
    }
}
