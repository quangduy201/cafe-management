package com.cafe.DAO;
import com.cafe.DTO.Recipe;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeDAO extends Manager{
    public RecipeDAO() throws SQLException {
        super("recipe", new ArrayList<>(
            List.of("PRODUCT_ID",
                "INGREDIENT_ID",
                "MASS",
                "UNIT")
        ));
    }

    public List<Recipe> convertToRecipe(List<List<String>> data){
        List<Recipe> recipeList = new ArrayList<>();
        try{
            String product_ID, ingredient_ID, unit;
            double mass;
            for (List<String> row : data){
                product_ID = row.get(0);
                ingredient_ID = row.get(1);
                mass = Double.parseDouble(row.get(2));
                unit = row.get(3);
                Recipe recipe = new Recipe(product_ID, ingredient_ID, unit, mass);
                recipeList.add(recipe);
            }
        } catch(Exception ignored){

        }
        return recipeList;
    }

    public List<Recipe> readRecipes(String[] conditions){
        List<Recipe> recipeList = new ArrayList<>();
        try {
            recipeList = convertToRecipe(read(conditions));
        } catch (Exception ignored){

        }
        return recipeList;
    }

    public int createRecipe(String product_ID, String ingredient_ID, String unit, double mass){
        if (readRecipes(new String[]{"PRODUCT_ID = '" + product_ID + "'", "INGREDIENT_ID = '" + ingredient_ID + "'"}).size() != 0){
            return 0;
        }
        try {
            return super.create(product_ID, ingredient_ID, mass, unit);
        } catch(Exception e){
            return 0;
        }
    }

    public int updateRecipe(String product_ID, String ingredient_ID, String unit){
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("UNIT", unit);
            return super.update(updateValues, "PRODUCT_ID = '"  + product_ID + "'", "INGREDIENT_ID ='" + ingredient_ID + "'");
        } catch(Exception e){
            return 0;
        }
    }

    public int updateRecipe(String product_ID, String ingredient_ID, double mass){
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("MASS", mass);
            return super.update(updateValues, "PRODUCT_ID = '"  + product_ID + "'", "INGREDIENT_ID ='" + ingredient_ID + "'");
        } catch(Exception e){
            return 0;
        }
    }

    public int deleteRecipe(String product_ID, String ingredient_ID, double mass){
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("MASS", mass);
            return super.update(updateValues, "PRODUCT_ID = '"  + product_ID + "'", "INGREDIENT_ID ='" + ingredient_ID + "'");
        } catch(Exception e){
            return 0;
        }
    }
}
