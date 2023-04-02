package com.cafe.BLL;

import com.cafe.DAL.DecentralizationDAL;
import com.cafe.DTO.Decentralization;

import java.util.List;
import java.util.Map;

public class DecentralizationBLL extends Manager<Decentralization> {
    private DecentralizationDAL decentralizationDAL;
    private List<Decentralization> decentralizationList;

    public DecentralizationBLL() {
        try {
            decentralizationDAL = new DecentralizationDAL();
            decentralizationList = searchDecentralization();
        } catch (Exception ignored) {

        }
    }

    public DecentralizationDAL getDecentralizationDAL() {
        return decentralizationDAL;
    }

    public void setDecentralizationDAL(DecentralizationDAL decentralizationDAL) {
        this.decentralizationDAL = decentralizationDAL;
    }

    public List<Decentralization> getDecentralizationList() {
        return decentralizationList;
    }

    public void setDecentralizationList(List<Decentralization> decentralizationList) {
        this.decentralizationList = decentralizationList;
    }

    public Object[][] getData() {
        return getData(decentralizationList);
    }

    public boolean addDecentralization(Decentralization decentralization) {
        if (getIndex(decentralization, "DECENTRALIZATION_NAME", decentralizationList) != -1) {
            System.out.println("Can't add new decentralization. Name already exists.");
            return false;
        }
        decentralizationList.add(decentralization);
        return decentralizationDAL.addDecentralization(decentralization) != 0;
    }

    public boolean updateDecentralization(Decentralization decentralization) {
        decentralizationList.set(getIndex(decentralization, "DECENTRALIZATION_ID", decentralizationList), decentralization);
        return decentralizationDAL.updateDecentralization(decentralization) != 0;
    }

    public boolean deleteDecentralization(Decentralization decentralization) {
        decentralizationList.remove(getIndex(decentralization, "DECENTRALIZATION_ID", decentralizationList));
        return decentralizationDAL.deleteDecentralization("DECENTRALIZATION_ID = '" + decentralization.getDecentralizationID() + "'") != 0;
    }

    public List<Decentralization> searchDecentralization(String... conditions) {
        return decentralizationDAL.searchDecentralizations(conditions);
    }

    public List<Decentralization> findDecentralizationBy(Map<String, Object> conditions) {
        List<Decentralization> decentralizations = decentralizationList;
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            decentralizations = findObjectsBy(entry.getKey(), entry.getValue(), decentralizations);
        return decentralizations;
    }

    public String getAutoID() {
        try {
            return getAutoID("DE", 2, decentralizationList);
        } catch (Exception e) {
            System.out.println("Error occurred in DecentralizationBLL.getAutoID(): " + e.getMessage());
        }
        return "";
    }

    @Override
    public Object getValueByKey(Decentralization decentralization, String key) {
        return switch (key) {
            case "DECENTRALIZATION_ID" -> decentralization.getDecentralizationID();
            case "IS_SALE" -> decentralization.getIsSale();
            case "IS_PRODUCT" -> decentralization.getIsProduct();
            case "IS_CATEGORY" -> decentralization.getIsCategory();
            case "IS_RECIPE" -> decentralization.getIsRecipe();
            case "IS_IMPORT" -> decentralization.getIsImport();
            case "IS_BILL" -> decentralization.getIsBill();
            case "IS_WAREHOUSES" -> decentralization.getIsWarehouses();
            case "IS_ACCOUNT" -> decentralization.getIsAccount();
            case "IS_STAFF" -> decentralization.getIsStaff();
            case "IS_CUSTOMER" -> decentralization.getIsCustomer();
            case "IS_DISCOUNT" -> decentralization.getIsDiscount();
            case "IS_DECENTRALIZATION" -> decentralization.getIsDecentralization();
            case "DECENTRALIZATION_NAME" -> decentralization.getDecentralizationName();
            default -> null;
        };
    }
}
