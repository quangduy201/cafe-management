package com.cafe.BLL;

import com.cafe.DAL.RecipeDAL;
import com.cafe.DTO.Recipe;

import java.util.List;
import java.util.Map;

public class RecipeBLL extends Manager<Recipe> {
    private RecipeDAL recipeDAL;
    private List<Recipe> recipeList;

    public RecipeBLL() {
        try {
            recipeDAL = new RecipeDAL();
            recipeList = searchRecipes();
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
        List<Recipe> recipes = findRecipesBy("PRODUCT_ID", recipe.getProductID());
        recipes = findObjectsBy("INGREDIENT_ID", recipe.getIngredientID(), recipes);
        Recipe recipeNeedsUpdating = recipes.get(0);
        recipeNeedsUpdating.setMass(recipe.getMass());
        recipeNeedsUpdating.setUnit(recipe.getUnit());
        recipeNeedsUpdating.setDeleted(recipe.isDeleted());
        return recipeDAL.updateRecipe(recipe) != 0;
    }

    public List<Recipe> searchRecipes(String... conditions) {
        return recipeDAL.searchRecipes(conditions);
    }

    public List<Recipe> findRecipesBy(Map<String, Object> conditions) {
        List<Recipe> recipes = recipeList;
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            recipes = findObjectsBy(entry.getKey(), entry.getValue(), recipes);
        return recipes;
    }

    @Override
    public Object getValueByKey(Recipe recipe, String key) {
        return switch (key) {
            case "PRODUCT_ID" -> recipe.getProductID();
            case "INGREDIENT_ID" -> recipe.getIngredientID();
            case "MASS" -> recipe.getMass();
            case "UNIT" -> recipe.getUnit();
            default -> null;
        };
    }
}
