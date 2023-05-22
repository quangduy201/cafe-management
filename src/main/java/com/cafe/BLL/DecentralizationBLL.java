package com.cafe.BLL;

import com.cafe.DAL.DecentralizationDAL;
import com.cafe.DTO.Decentralization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DecentralizationBLL extends Manager<Decentralization> {
    private DecentralizationDAL decentralizationDAL;
    private List<Decentralization> decentralizationList;

    public DecentralizationBLL() {
        try {
            decentralizationDAL = new DecentralizationDAL();
            decentralizationList = searchDecentralization("DELETED = 0", "DECENTRALIZATION_ID != 'DE00'");
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

    public List<Decentralization> findDecentralizations(String key, String value) {
        List<Decentralization> list = new ArrayList<>();
        for (Decentralization decentralization : decentralizationList) {
            if (getValueByKey(decentralization, key).toString().toLowerCase().contains(value.toLowerCase())) {
                list.add(decentralization);
            }
        }
        return list;
    }

    public List<Decentralization> findDecentralizationsBy(Map<String, Object> conditions) {
        List<Decentralization> decentralizations = decentralizationList;
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            decentralizations = findObjectsBy(entry.getKey(), entry.getValue(), decentralizations);
        return decentralizations;
    }

    public boolean exists(Decentralization decentralization) {
        Map<String, Object> conditions = new HashMap<>(Map.of(
            "IS_SALE", decentralization.getIsSale(),
            "IS_PRODUCT", decentralization.getIsProduct(),
            "IS_CATEGORY", decentralization.getIsCategory(),
            "IS_RECIPE", decentralization.getIsRecipe(),
            "IS_IMPORT", decentralization.getIsImport(),
            "IS_SUPPLIER", decentralization.getIsSupplier(),
            "IS_BILL", decentralization.getIsBill()
        ));
        conditions.putAll(Map.of(
            "IS_WAREHOUSES", decentralization.getIsWarehouses(),
            "IS_ACCOUNT", decentralization.getIsAccount(),
            "IS_STAFF", decentralization.getIsStaff(),
            "IS_CUSTOMER", decentralization.getIsCustomer(),
            "IS_DISCOUNT", decentralization.getIsDiscount(),
            "IS_DECENTRALIZE", decentralization.getIsDecentralization(),
            "DECENTRALIZATION_NAME", decentralization.getDecentralizationName()
        ));
        return !findDecentralizationsBy(conditions).isEmpty();
    }

    public boolean exists(Map<String, Object> conditions) {
        if (conditions.containsKey("DECENTRALIZATION_NAME") && conditions.get("DECENTRALIZATION_NAME").equals("admin")) {
            return true;
        }
        return !findDecentralizationsBy(conditions).isEmpty();
    }

    public String getAutoID() {
        return getAutoID("DE", 2, searchDecentralization("DECENTRALIZATION_ID != 'DE00'"));
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
            case "IS_SUPPLIER" -> decentralization.getIsSupplier();
            case "IS_BILL" -> decentralization.getIsBill();
            case "IS_WAREHOUSES" -> decentralization.getIsWarehouses();
            case "IS_ACCOUNT" -> decentralization.getIsAccount();
            case "IS_STAFF" -> decentralization.getIsStaff();
            case "IS_CUSTOMER" -> decentralization.getIsCustomer();
            case "IS_DISCOUNT" -> decentralization.getIsDiscount();
            case "IS_DECENTRALIZE" -> decentralization.getIsDecentralization();
            case "DECENTRALIZATION_NAME" -> decentralization.getDecentralizationName();
            default -> null;
        };
    }
}
