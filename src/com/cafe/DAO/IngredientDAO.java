package com.cafe.DAO;
import com.cafe.DTO.Ingredient;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IngredientDAO extends Manager{
    public IngredientDAO() throws SQLException {
        super("ingredient", new ArrayList<>(
            List.of("INGREDIENT_ID",
                "NAME",
                "QUANTITY",
                "UNIT",
                "SUPPLIER",
                "DELETED")
        ));
    }

    public List<Ingredient> convertToIngredient(List<List<String>> data){
        List<Ingredient> ingredientList = new ArrayList<>();
        try {
            String ingredient_ID, name, unit, supplier_ID;
            double quantity;
            boolean deleted;
            for (List<String> row : data) {
                ingredient_ID = row.get(0);
                name = row.get(1);
                quantity = Double.parseDouble(row.get(2));
                unit = row.get(3);
                supplier_ID = row.get(4);
                deleted = !row.get(5).contains("0");
                Ingredient ingredient = new Ingredient(ingredient_ID, name, unit, supplier_ID, quantity, deleted);
                ingredientList.add(ingredient);
            }
        } catch (Exception ignored){

        }
        return ingredientList;
    }

    public List<Ingredient> readIngredients(String[] conditions){
        List<Ingredient> ingredientList = new ArrayList<>();
        try {
            ingredientList = convertToIngredient(read(conditions));
        } catch (Exception ignored){

        }
        return ingredientList;
    }

    public int createIngredient(String ingredient_ID, String name, String unit, String supplier_ID){
        if (readIngredients(new String[] {"INGREDIENT_ID '" + ingredient_ID + "'"}).size() != 0){
            return 0;
        }
        try {
            return super.create(ingredient_ID, name, 0, unit, supplier_ID, 0);
        } catch (Exception e){
            return 0;
        }
    }

    public int updateIngredient(String ingredient_ID, String name, String unit, String supplier_ID){
        // Những giá trị là _ID sẽ được combobox cho user chọn
        try {
            Map<String, Object> updateValues = new HashMap<>();
            if (name != null){
                updateValues.put("NAME", name);
            }
            if (unit != null){
                updateValues.put("UNIT", name);
            }
            if (supplier_ID != null){
                updateValues.put("SUPPLIER_ID", name);
            }
            return super.update(updateValues, "INGEDIENT_ID = '" + ingredient_ID + "'");
        } catch (Exception e){
            return 0;
        }
    }

    public int deleteIngredient(String ingredient_ID){
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("DELETED", 1);
            return super.update(updateValues, "INGEDIENT_ID = '" + ingredient_ID + "'");
        } catch (Exception e){
            return 0;
        }
    }
}
