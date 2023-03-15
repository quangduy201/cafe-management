package com.cafe.DAL;

import com.cafe.DTO.Ingredient;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IngredientDAL extends Manager {
    public IngredientDAL() throws SQLException {
        super("ingredient", new ArrayList<>(
            List.of("INGREDIENT_ID",
                "NAME",
                "QUANTITY",
                "UNIT",
                "SUPPLIER",
                "DELETED")
        ));
    }

    public List<Ingredient> convertToIngredients(List<List<String>> data) {
        return convert(data, row -> new Ingredient(
            row.get(0), // ingredientID
            row.get(1), // name
            Double.parseDouble(row.get(2)), // quantity
            row.get(3), // unit
            row.get(4), // supplierID
            Boolean.parseBoolean(row.get(5)) // deleted
        ));
    }

    public int insertIngredient(Ingredient ingredient) {
        try {
            return create(ingredient.getIngredientID(),
                ingredient.getName(),
                ingredient.getQuantity(),
                ingredient.getUnit(),
                ingredient.getSupplierID(),
                false
            ); // ingredient khi tạo mặc định deleted = 0
        } catch (Exception e) {
            System.out.println("Error occurred in IngredientDAL.insertIngredient(): " + e.getMessage());
        }
        return 0;
    }

    public int updateIngredient(Map<String, Object> updateValues, String... conditions) {
        try {
            return update(updateValues, conditions);
        } catch (Exception e) {
            System.out.println("Error occurred in IngredientDAL.updateIngredient(): " + e.getMessage());
        }
        return 0;
    }

    public int deleteIngredient(String... conditions) {
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("DELETED", 1);
            return update(updateValues, conditions);
        } catch (Exception e) {
            System.out.println("Error occurred in IngredientDAL.deleteIngredient(): " + e.getMessage());
        }
        return 0;
    }

    public List<Ingredient> searchIngredients(String... conditions) {
        try {
            return convertToIngredients(read(conditions));
        } catch (Exception e) {
            System.out.println("Error occurred in IngredientDAL.searchIngredients(): " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public String getAutoID() {
        try {
            return getAutoID("ING", 3);
        } catch (Exception e) {
            System.out.println("Error occurred in IngredientDAL.getAutoID(): " + e.getMessage());
        }
        return "";
    }
}
