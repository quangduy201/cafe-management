package com.cafe.DAL;

import com.cafe.DTO.Recipe;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeDAL extends Manager {
    public RecipeDAL() throws SQLException {
        super("recipe", new ArrayList<>(
            List.of("PRODUCT_ID",
                "INGREDIENT_ID",
                "MASS",
                "UNIT",
                "DELETED")
        ));
    }

    public List<Recipe> convertToRecipes(List<List<String>> data) {
        return convert(data, row -> new Recipe(
            row.get(0), // productID
            row.get(1), // ingredientID
            Double.parseDouble(row.get(2)), // mass
            row.get(3), // unit
            Boolean.parseBoolean(row.get(4)) // deleted
        ));
    }

    public int addRecipe(Recipe recipe) {
        try {
            return create(recipe.getProductID(),
                recipe.getIngredientID(),
                recipe.getMass(),
                recipe.getUnit(),
                false
            );
        } catch (Exception e) {
            System.out.println("Error occurred in RecipeDAL.addRecipe(): " + e.getMessage());
        }
        return 0;
    }

    public int updateRecipe(Recipe recipe) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(recipe.getProductID());
            updateValues.add(recipe.getIngredientID());
            updateValues.add(recipe.getMass());
            updateValues.add(recipe.getUnit());
            updateValues.add(recipe.isDeleted());
            return update(updateValues, "PRODUCT_ID = " + recipe.getProductID(),
                "INGREDIENT_ID = " + recipe.getIngredientID());
        } catch (Exception e) {
            System.out.println("Error occurred in RecipeDAL.updateRecipe(): " + e.getMessage());
        }
        return 0;
    }

    public int deleteRecipe(String... conditions) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(1);
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
