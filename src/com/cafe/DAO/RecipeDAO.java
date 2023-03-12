package com.cafe.DAO;

import com.cafe.DTO.Product;
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

    public List<Recipe> getRecipes(){
        List<Recipe> recipeList = new ArrayList<Recipe>() {
        };
        try{
            RecipeDAO recipeDAO = new RecipeDAO();
            List<List<String>> recipes = recipeDAO.read();
            for (List<String> row : recipes){
                String product_ID, ingredient_ID, unit;
                double mass;
                product_ID = row.get(0);
                ingredient_ID = row.get(1);
                mass = Double.parseDouble(row.get(2));
                unit = row.get(3);
                Recipe recipe = new Recipe(product_ID, ingredient_ID, unit, mass);
                recipeList.add(recipe);
            }
        } catch(Exception e){

        }
        return recipeList;
    }

    public Recipe readByPrimaryKey(String product_ID, String ingredient_ID){
        try {
            for (Recipe recipe : getRecipes()){
                if (recipe.getProduct_ID().indexOf(product_ID) != -1 && recipe.getIngredient_ID().indexOf(ingredient_ID) != -1){
                    return recipe;
                }
            }
        } catch(Exception e){
            return null;
        }
        return null;
    }

    public List<Recipe> readByProduct_ID(String product_ID){
        List<Recipe> result = new ArrayList<Recipe>();
        try {
            for (Recipe recipe : getRecipes()){
                if (recipe.getProduct_ID().indexOf(product_ID) != -1){
                    result.add(recipe);
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public List<Recipe> readByIngredient_ID(String ingredient_ID){
        List<Recipe> result = new ArrayList<Recipe>();
        try {
            for (Recipe recipe : getRecipes()){
                if (recipe.getIngredient_ID().indexOf(ingredient_ID) != -1){
                    result.add(recipe);
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public int create(String product_ID, String ingredient_ID, String unit, double mass){
        if (readByPrimaryKey(product_ID, ingredient_ID) != null){
            return 0;
        }
        try {
            return super.create(product_ID, ingredient_ID, mass, unit);
        } catch(Exception e){
            return 0;
        }
    }

    public int update(String product_ID, String ingredient_ID, String unit){
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("UNIT", unit);
            return super.update(updateValues, "PRODUCT_ID = '"  + product_ID + "'", "INGREDIENT_ID ='" + ingredient_ID + "'");
        } catch(Exception e){
            return 0;
        }
    }

    public int update(String product_ID, String ingredient_ID, double mass){
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("MASS", mass);
            return super.update(updateValues, "PRODUCT_ID = '"  + product_ID + "'", "INGREDIENT_ID ='" + ingredient_ID + "'");
        } catch(Exception e){
            return 0;
        }
    }

    public int delete(String product_ID, String ingredient_ID, double mass){
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("MASS", mass);
            return super.update(updateValues, "PRODUCT_ID = '"  + product_ID + "'", "INGREDIENT_ID ='" + ingredient_ID + "'");
        } catch(Exception e){
            return 0;
        }
    }
}
