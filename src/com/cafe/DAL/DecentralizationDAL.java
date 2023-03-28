package com.cafe.DAL;

import com.cafe.DTO.Decentralization;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DecentralizationDAL extends Manager {
    public DecentralizationDAL() throws SQLException {
        super("decentralization", new ArrayList<>(
            List.of("DECENTRALIZATION_ID",
                "IS_RECIPE",
                "IS_PRODUCT",
                "IS_CATEGORY",
                "IS_BILL",
                "IS_DISCOUNT",
                "IS_CUSTOMER",
                "IS_WAREHOUSES",
                "IS_STAFF",
                "IS_ACCOUNT",
                "IS_DECENTRALIZE",
                "DECENTRALIZATION_NAME",
                "DELETED")
        ));
    }

    public List<Decentralization> convertToDecentralization(List<List<String>> data) {
        return convert(data, row -> new Decentralization(
            row.get(0), // decentralizationID
            Integer.parseInt(row.get(1)), // isRecipe
            Integer.parseInt(row.get(1)), // isProduct
            Integer.parseInt(row.get(1)), // isCategory
            Integer.parseInt(row.get(1)), // isBill
            Integer.parseInt(row.get(1)), // isDiscount
            Integer.parseInt(row.get(1)), // isCustomer
            Integer.parseInt(row.get(1)), // isWarehouses
            Integer.parseInt(row.get(1)), // isStaff
            Integer.parseInt(row.get(1)), // isAccount
            Integer.parseInt(row.get(1)), // isDecentralization
            row.get(11), // decentralizationName
            Boolean.parseBoolean(row.get(12)) // deleted
        ));
    }

    public int insertDecentralization(Decentralization Decentralization) {
        try {
            return create(Decentralization.getDecentralizationID(),
                Decentralization.getIsRecipe(),
                Decentralization.getIsProduct(),
                Decentralization.getIsCategory(),
                Decentralization.getIsBill(),
                Decentralization.getIsDiscount(),
                Decentralization.getIsCustomer(),
                Decentralization.getIsWarehouses(),
                Decentralization.getIsStaff(),
                Decentralization.getIsAccount(),
                Decentralization.getIsDecentralize(),
                Decentralization.getDecentralizationName(),
                false
            ); // decentralization khi tạo mặc định deleted = 0
        } catch (Exception e) {
            System.out.println("Error occurred in DecentralizationDAL.insertDecentralization(): " + e.getMessage());
        }
        return 0;
    }

    public int updateDecentralization(Decentralization decentralization) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(decentralization.getDecentralizationID());
            updateValues.add(decentralization.getIsRecipe());
            updateValues.add(decentralization.getIsProduct());
            updateValues.add(decentralization.getIsCategory());
            updateValues.add(decentralization.getIsBill());
            updateValues.add(decentralization.getIsDiscount());
            updateValues.add(decentralization.getIsCustomer());
            updateValues.add(decentralization.getIsWarehouses());
            updateValues.add(decentralization.getIsStaff());
            updateValues.add(decentralization.getIsAccount());
            updateValues.add(decentralization.getIsDecentralize());
            updateValues.add(decentralization.getDecentralizationName());
            updateValues.add(decentralization.isDeleted());
            return update(updateValues, "DECENTRALIZATION_ID = " + decentralization.getDecentralizationID());
        } catch (Exception e) {
            System.out.println("Error occurred in DecentralizationDAL.updateDecentralization(): " + e.getMessage());
        }
        return 0;
    }

    public int deleteDecentralization(String... conditions) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(1);
            return update(updateValues, conditions);
        } catch (Exception e) {
            System.out.println("Error occurred in DecentralizationDAL.deleteDecentralization(): " + e.getMessage());
        }
        return 0;
    }

    public List<Decentralization> searchDecentralizations(String... conditions) {
        try {
            return convertToDecentralization(read(conditions));
        } catch (Exception e) {
            System.out.println("Error occurred in DecentralizationDAL.searchDecentralizations(): " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public String getAutoID() {
        try {
            return getAutoID("DE", 2);
        } catch (Exception e) {
            System.out.println("Error occurred in DecentralizationDAL.getAutoID(): " + e.getMessage());
        }
        return "";
    }
}
