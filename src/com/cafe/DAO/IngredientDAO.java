package com.cafe.DAO;

import com.cafe.DTO.Discount;
import com.cafe.DTO.DiscountDetails;
import com.cafe.DTO.Ingredient;
import com.cafe.utils.Day;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IngredientDAO extends Manager{
    public IngredientDAO() throws SQLException {
        super("ingredient", new ArrayList<>(
            List.of("INGEDIENT_ID",
                "NAME",
                "QUANTITY",
                "UNIT",
                "SUPPLIER",
                "DELETED")
        ));
    }

    public List<Ingredient> getIngredients(){
        List<Ingredient> ingredientList = new ArrayList<Ingredient>();
        try {
            IngredientDAO ingredientDAO = new IngredientDAO();
            String ingredient_ID, name, unit, supplier_ID;
            double quantity;
            boolean deleted;
            List<List<String>> ingredients = ingredientDAO.read();
            for (List<String> row : ingredients) {
                ingredient_ID = row.get(0);
                name = row.get(1);
                quantity = Double.parseDouble(row.get(2));
                unit = row.get(3);
                supplier_ID = row.get(4);
                if (row.get(5).indexOf("0") != -1){
                    deleted = false;
                }else {
                    deleted = true;
                }
                Ingredient ingredient = new Ingredient(ingredient_ID, name, unit, supplier_ID, quantity, deleted);
                ingredientList.add(ingredient);
            }
        } catch (Exception e){

        }
        return ingredientList;
    }

    public Ingredient readByPrimaryKey(String ingredient_ID){
        try {
            for (Ingredient ingredient: getIngredients()) {
                if (ingredient.getIngredient_ID().indexOf(ingredient_ID) != -1){
                    return ingredient;
                }
            }
        } catch (Exception e){
            return null;
        }
        return  null;
    }

    public List<Ingredient> readByName(String name){
        List<Ingredient> result = new ArrayList<Ingredient>();
        try {
            for (Ingredient ingredient: getIngredients()) {
                if (ingredient.getName().indexOf(name) != -1){
                    result.add(ingredient);
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public List<Ingredient> readBySupplier_ID(String supplier_ID){
        List<Ingredient> result = new ArrayList<Ingredient>();
        try {
            for (Ingredient ingredient: getIngredients()) {
                if (ingredient.getSupplier_ID().indexOf(supplier_ID) != -1){
                    result.add(ingredient);
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public List<Ingredient> readByDeleted(boolean deleted){
        List<Ingredient> result = new ArrayList<Ingredient>();
        try {
            for (Ingredient ingredient: getIngredients()) {
                if (ingredient.isDeleted() != deleted){
                    result.add(ingredient);
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public int create(String ingredient_ID, String name, String unit, String supplier_ID){
        if (readByPrimaryKey(ingredient_ID) != null){
            return 0;
        }
        try {
            return super.create(ingredient_ID, name, 0, unit, supplier_ID, 0);
        } catch (Exception e){
            return 0;
        }
    }

    public int update(String ingredient_ID, String name, String unit, String supplier_ID){
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

    public int delete(String ingredient_ID){
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("DELETED", 1);
            return super.update(updateValues, "INGEDIENT_ID = '" + ingredient_ID + "'");
        } catch (Exception e){
            return 0;
        }
    }
}
