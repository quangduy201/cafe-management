package com.cafe.DAL;

import com.cafe.DTO.Ingredient;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAL extends Manager {
    public IngredientDAL() throws SQLException {
        super("ingredient", new ArrayList<>(
            List.of("INGREDIENT_ID",
                "NAME",
                "QUANTITY",
                "UNIT",
                "SUPPLIER_ID",
                "DELETED")
        ));
    }

    public List<Ingredient> convertToIngredients(List<List<String>> data) {
        return convert(data, row -> {
            row.set(row.size() - 1, row.get(row.size() - 1).equals("0") ? "false" : "true");
            return new Ingredient(
                row.get(0), // ingredientID
                row.get(1), // name
                Double.parseDouble(row.get(2)), // quantity
                row.get(3), // unit
                row.get(4), // supplierID
                Boolean.parseBoolean(row.get(5)) // deleted
            );
        });
    }

    public int addIngredient(Ingredient ingredient) {
        try {
            return create(ingredient.getIngredientID(),
                ingredient.getName(),
                ingredient.getQuantity(),
                ingredient.getUnit(),
                ingredient.getSupplierID(),
                false
            ); // ingredient khi tạo mặc định deleted = 0
        } catch (Exception e) {
            System.out.println("Error occurred in IngredientDAL.addIngredient(): " + e.getMessage());
        }
        return 0;
    }

    public int updateIngredient(Ingredient ingredient) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(ingredient.getIngredientID());
            updateValues.add(ingredient.getName());
            updateValues.add(ingredient.getQuantity());
            updateValues.add(ingredient.getUnit());
            updateValues.add(ingredient.getSupplierID());
            updateValues.add(ingredient.isDeleted());
            return update(updateValues, "INGREDIENT_ID = '" + ingredient.getIngredientID() + "'");
        } catch (Exception e) {
            System.out.println("Error occurred in IngredientDAL.updateIngredient(): " + e.getMessage());
        }
        return 0;
    }

    public int deleteIngredient(String... conditions) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(true);
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
}
