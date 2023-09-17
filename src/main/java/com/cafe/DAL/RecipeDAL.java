package com.cafe.DAL;

import com.cafe.DTO.Recipe;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeDAL extends Manager {
    public RecipeDAL() throws SQLException {
        super("recipe",
            List.of("RECIPE_ID",
                "PRODUCT_ID",
                "INGREDIENT_ID",
                "MASS",
                "UNIT",
                "DELETED")
        );
    }

    public List<Recipe> convertToRecipes(List<List<String>> data) {
        return convert(data, row -> {
            row.set(row.size() - 1, row.get(row.size() - 1).equals("0") ? "false" : "true");
            return new Recipe(
                row.get(0), // recipeID
                row.get(1), // productID
                row.get(2), // ingredientID
                Double.parseDouble(row.get(3)), // mass
                row.get(4), // unit
                Boolean.parseBoolean(row.get(5)) // deleted
            );
        });
    }

    public int addRecipe(Recipe recipe) {
        try {
            return create(recipe.getRecipeID(),
                recipe.getProductID(),
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
            updateValues.add(recipe.getRecipeID());
            updateValues.add(recipe.getProductID());
            updateValues.add(recipe.getIngredientID());
            updateValues.add(recipe.getMass());
            updateValues.add(recipe.getUnit());
            updateValues.add(recipe.isDeleted());
            return update(updateValues, "RECIPE_ID = '" + recipe.getRecipeID() + "'");
        } catch (Exception e) {
            System.out.println("Error occurred in RecipeDAL.updateRecipe(): " + e.getMessage());
        }
        return 0;
    }

    public int deleteRecipe(String... conditions) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(true);
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

//    public List<List<String>> getFullRecipeTable() {
//        try {
//            return executeQuery("");
//        } catch (Exception e) {
//
//        }
//    }
}
