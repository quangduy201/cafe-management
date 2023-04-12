package com.cafe.BLL;

import com.cafe.DAL.IngredientDAL;
import com.cafe.DTO.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IngredientBLL extends Manager<Ingredient> {
    private IngredientDAL ingredientDAL;
    private List<Ingredient> ingredientList;

    public IngredientBLL() {
        try {
            ingredientDAL = new IngredientDAL();
            ingredientList = searchIngredients("DELETED = 0");
        } catch (Exception ignored) {

        }
    }

    public IngredientDAL getIngredientDAL() {
        return ingredientDAL;
    }

    public void setIngredientDAL(IngredientDAL ingredientDAL) {
        this.ingredientDAL = ingredientDAL;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public Object[][] getData() {
        return getData(ingredientList);
    }

    public boolean addIngredient(Ingredient ingredient) {
        ingredientList.add(ingredient);
        return ingredientDAL.addIngredient(ingredient) != 0;
    }

    public boolean updateIngredient(Ingredient ingredient) {
        ingredientList.set(getIndex(ingredient, "INGREDIENT_ID", ingredientList), ingredient);
        return ingredientDAL.updateIngredient(ingredient) != 0;
    }

    public boolean deleteIngredient(Ingredient ingredient) {
        ingredientList.remove(getIndex(ingredient, "INGREDIENT_ID", ingredientList));
        return ingredientDAL.deleteIngredient("INGREDIENT_ID = '" + ingredient.getIngredientID() + "'") != 0;
    }

    public List<Ingredient> searchIngredients(String... conditions) {
        this.ingredientList = ingredientDAL.searchIngredients(conditions);
        return this.ingredientList;
    }

    public List<Ingredient> findIngredients(String key, String value) {
        List<Ingredient> list = new ArrayList<>();
        for (Ingredient ingredient : ingredientList) {
            if (getValueByKey(ingredient, key).toString().toLowerCase().contains(value.toLowerCase())) {
                list.add(ingredient);
            }
        }
        return list;
    }

    public List<Ingredient> findIngredientsBy(Map<String, Object> conditions) {
        List<Ingredient> ingredients = ingredientList;
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            ingredients = findObjectsBy(entry.getKey(), entry.getValue(), ingredients);
        return ingredients;
    }

    public boolean exists(Ingredient ingredient) {
        return !findIngredientsBy(Map.of(
            "NAME", ingredient.getName(),
            "QUANTITY", ingredient.getQuantity(),
            "UNIT", ingredient.getUnit(),
            "SUPPLIER_ID", ingredient.getSupplierID()
        )).isEmpty();
    }

    public boolean exists(Map<String, Object> conditions) {
        return !findIngredientsBy(conditions).isEmpty();
    }

    public String getAutoID() {
        return getAutoID("ING", 3, searchIngredients());
    }

    @Override
    public Object getValueByKey(Ingredient ingredient, String key) {
        return switch (key) {
            case "INGREDIENT_ID" -> ingredient.getIngredientID();
            case "NAME" -> ingredient.getName();
            case "QUANTITY" -> ingredient.getQuantity();
            case "UNIT" -> ingredient.getUnit();
            case "SUPPLIER_ID" -> ingredient.getSupplierID();
            default -> null;
        };
    }
}
