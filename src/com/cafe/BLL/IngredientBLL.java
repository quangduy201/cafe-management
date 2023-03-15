package com.cafe.BLL;

import com.cafe.DAL.IngredientDAL;
import com.cafe.DTO.Ingredient;

import java.util.List;
import java.util.Map;

public class IngredientBLL {
    private IngredientDAL ingredientDAL;

    public IngredientBLL() {
        try {
            ingredientDAL = new IngredientDAL();
        } catch (Exception ignored) {

        }
    }

    public boolean insertIngredient(Ingredient ingredient) {
        if (searchIngredients("NAME = '" + ingredient.getName() + "'").size() != 0) {
            System.out.println("Can't insert new ingredient. Name already exists.");
            return false;
        }
        return ingredientDAL.insertIngredient(ingredient) != 0;
    }

    public boolean updateIngredient(Map<String, Object> updateValues, String... conditions) {
        return ingredientDAL.updateIngredient(updateValues, conditions) != 0;
    }

    public boolean removeIngredient(String id) {
        return ingredientDAL.deleteIngredient(id) != 0;
    }

    public List<Ingredient> searchIngredients(String... conditions) {
        return ingredientDAL.searchIngredients(conditions);
    }

    public String getAutoID() {
        return ingredientDAL.getAutoID();
    }
}
