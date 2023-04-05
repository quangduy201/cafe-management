package com.cafe.BLL;

import com.cafe.DAL.RecipeDAL;
import com.cafe.DTO.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecipeBLL extends Manager<Recipe> {
    private RecipeDAL recipeDAL;
    private List<Recipe> recipeList;

    public RecipeBLL() {
        try {
            recipeDAL = new RecipeDAL();
            recipeList = searchRecipes("DELETED = 0");
        } catch (Exception ignored) {

        }
    }

    public RecipeDAL getRecipeDAL() {
        return recipeDAL;
    }

    public void setRecipeDAL(RecipeDAL recipeDAL) {
        this.recipeDAL = recipeDAL;
    }

    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    public Object[][] getData() {
        return getData(recipeList);
    }

    public boolean addRecipe(Recipe recipe) {
        recipeList.add(recipe);
        return recipeDAL.addRecipe(recipe) != 0;
    }

    public boolean updateRecipe(Recipe recipe) {

        return recipeDAL.updateRecipe(recipe) != 0;
    }

    public List<Recipe> searchRecipes(String... conditions) {
        return recipeDAL.searchRecipes(conditions);
    }

    public List<Recipe> findRecipes(String key, String value) {
        List<Recipe> list = new ArrayList<>();
        for (Recipe recipe : recipeList) {
            if (getValueByKey(recipe, key).toString().toLowerCase().contains(value.toLowerCase())) {
                list.add(recipe);
            }
        }
        return list;
    }

    public List<Recipe> findRecipesBy(Map<String, Object> conditions) {
        List<Recipe> recipes = recipeList;
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            recipes = findObjectsBy(entry.getKey(), entry.getValue(), recipes);
        return recipes;
    }

    public String getAutoID() {
        try {
            return getAutoID("RE", 3, searchRecipes());
        } catch (Exception e) {
            System.out.println("Error occurred in AccountDAL.getAutoID(): " + e.getMessage());
        }
        return "";
    }

    @Override
    public Object getValueByKey(Recipe recipe, String key) {
        return switch (key) {
            case "RECIPE_ID" -> recipe.getRecipeID();
            case "PRODUCT_ID" -> recipe.getProductID();
            case "INGREDIENT_ID" -> recipe.getIngredientID();
            case "MASS" -> recipe.getMass();
            case "UNIT" -> recipe.getUnit();
            default -> null;
        };
    }
}
