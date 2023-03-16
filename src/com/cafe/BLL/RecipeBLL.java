package com.cafe.BLL;

import com.cafe.DAL.RecipeDAL;
import com.cafe.DTO.Recipe;

import java.util.List;

public class RecipeBLL {
    private RecipeDAL recipeDAL;

    public RecipeBLL() {
        try {
            recipeDAL = new RecipeDAL();
        } catch (Exception ignored) {

        }
    }

    public boolean insertRecipe(Recipe recipe) {
        return recipeDAL.insertRecipe(recipe) != 0;
    }

    public boolean updateRecipe(Recipe recipe) {
        return recipeDAL.updateRecipe(recipe) != 0;
    }

    public boolean removeRecipe(String id) {
        return recipeDAL.deleteRecipe(id) != 0;
    }

    public List<Recipe> searchRecipes(String... conditions) {
        return recipeDAL.searchRecipes(conditions);
    }
}
