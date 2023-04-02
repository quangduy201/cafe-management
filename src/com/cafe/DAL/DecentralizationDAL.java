package com.cafe.DAL;

import com.cafe.DTO.Decentralization;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DecentralizationDAL extends Manager {
    public DecentralizationDAL() throws SQLException {
        super("decentralization", new ArrayList<>(
            List.of("DECENTRALIZATION_ID",
                "IS_SALE",
                "IS_PRODUCT",
                "IS_CATEGORY",
                "IS_RECIPE",
                "IS_IMPORT",
                "IS_BILL",
                "IS_WAREHOUSES",
                "IS_ACCOUNT",
                "IS_STAFF",
                "IS_CUSTOMER",
                "IS_DISCOUNT",
                "IS_DECENTRALIZE",
                "DECENTRALIZATION_NAME",
                "DELETED")
        ));
    }

    public List<Decentralization> convertToDecentralization(List<List<String>> data) {
        return convert(data, row -> new Decentralization(
            row.get(0), // decentralizationID
            Integer.parseInt(row.get(1)), // isSale
            Integer.parseInt(row.get(2)), // isProduct
            Integer.parseInt(row.get(3)), // isCategory
            Integer.parseInt(row.get(4)), // isRecipe
            Integer.parseInt(row.get(5)), // isImport
            Integer.parseInt(row.get(6)), // isBill
            Integer.parseInt(row.get(7)), // isWarehouses
            Integer.parseInt(row.get(8)), // isAccount
            Integer.parseInt(row.get(9)), // isStaff
            Integer.parseInt(row.get(10)), // isCustomer
            Integer.parseInt(row.get(11)), // isDiscount
            Integer.parseInt(row.get(12)), // isDecentralization
            row.get(13), // decentralizationName
            Boolean.parseBoolean(row.get(14)) // deleted
        ));
    }

    public int addDecentralization(Decentralization Decentralization) {
        try {
            return create(Decentralization.getDecentralizationID(),
                Decentralization.getIsSale(),
                Decentralization.getIsProduct(),
                Decentralization.getIsCategory(),
                Decentralization.getIsRecipe(),
                Decentralization.getIsImport(),
                Decentralization.getIsBill(),
                Decentralization.getIsWarehouses(),
                Decentralization.getIsAccount(),
                Decentralization.getIsStaff(),
                Decentralization.getIsCustomer(),
                Decentralization.getIsDiscount(),
                Decentralization.getIsDecentralization(),
                Decentralization.getDecentralizationID(),
                Decentralization.getDecentralizationName(),
                false
            ); // decentralization khi tạo mặc định deleted = 0
        } catch (Exception e) {
            System.out.println("Error occurred in DecentralizationDAL.addDecentralization(): " + e.getMessage());
        }
        return 0;
    }

    public int updateDecentralization(Decentralization decentralization) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(decentralization.getDecentralizationID());
            updateValues.add(decentralization.getIsSale());
            updateValues.add(decentralization.getIsProduct());
            updateValues.add(decentralization.getIsCategory());
            updateValues.add(decentralization.getIsRecipe());
            updateValues.add(decentralization.getIsImport());
            updateValues.add(decentralization.getIsBill());
            updateValues.add(decentralization.getIsWarehouses());
            updateValues.add(decentralization.getIsAccount());
            updateValues.add(decentralization.getIsStaff());
            updateValues.add(decentralization.getIsCustomer());
            updateValues.add(decentralization.getIsDiscount());
            updateValues.add(decentralization.getIsDecentralization());
            updateValues.add(decentralization.getDecentralizationID());
            updateValues.add(decentralization.getDecentralizationName());
            updateValues.add(decentralization.isDeleted());
            return update(updateValues, "DECENTRALIZATION_ID = '" + decentralization.getDecentralizationID() + "'");
        } catch (Exception e) {
            System.out.println("Error occurred in DecentralizationDAL.updateDecentralization(): " + e.getMessage());
        }
        return 0;
    }

    public int deleteDecentralization(String... conditions) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(true);
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
}
