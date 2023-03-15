package com.cafe.DAL;

import com.cafe.DTO.Recipe;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeDAL extends Manager {
    public RecipeDAL() throws SQLException {
        super("recipe", new ArrayList<>(
            List.of("PRODUCT_ID",
                "INGREDIENT_ID",
                "MASS",
                "UNIT")
        ));
    }

    public List<Recipe> convertToRecipes(List<List<String>> data) {
        return convert(data, row -> new Recipe(
            row.get(0), // productID
            row.get(1), // ingredientID
            Double.parseDouble(row.get(2)), // mass
            row.get(3) // unit
        ));
    }

    public int insertRecipe(Recipe recipe) {
        try {
            return create(recipe.getProductID(),
                recipe.getIngredientID(),
                recipe.getMass(),
                recipe.getUnit()
            );
        } catch (Exception e) {
            System.out.println("Error occurred in RecipeDAL.insertRecipe(): " + e.getMessage());
        }
        return 0;
    }

    public int updateRecipe(Map<String, Object> updateValues, String... conditions) {
        try {
            return update(updateValues, conditions);
        } catch (Exception e) {
            System.out.println("Error occurred in RecipeDAL.updateRecipe(): " + e.getMessage());
        }
        return 0;
    }

    public int deleteRecipe(String... conditions) {
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("DELETED", 1);
            return update(updateValues, conditions);
        } catch (Exception e) {
            System.out.println("Error occurred in RecipeDAL.deleteRecipe(): " + e.getMessage());
        }
        return 0;
    }

    public List<Recipe> searchRecipes(String... conditions) {
        try {
            return convertToRecipes(read(conditions));
        } catch (Exception e) {
            System.out.println("Error occurred in RecipeDAL.searchRecipes(): " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
