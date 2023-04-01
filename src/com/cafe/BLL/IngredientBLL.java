package com.cafe.BLL;

import com.cafe.DAL.IngredientDAL;
import com.cafe.DTO.Ingredient;
import com.cafe.DTO.Ingredient;
import com.cafe.DTO.Ingredient;
import com.cafe.DTO.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class IngredientBLL {
    private IngredientDAL ingredientDAL;
    private List<Ingredient> ingredientList;

    public IngredientBLL() {
        try {
            ingredientDAL = new IngredientDAL();
            ingredientList = searchIngredients();
            readIngredient();
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

    public boolean insertIngredient(Ingredient ingredient) {
        if (searchIngredients("NAME = '" + ingredient.getName() + "'").size() != 0) {
            System.out.println("Can't insert new ingredient. Name already exists.");
            return false;
        }
        ingredientList.add(ingredient);
        return ingredientDAL.insertIngredient(ingredient) != 0;
    }

    public boolean updateIngredient(Ingredient ingredient) {
        ingredientList.set(getIndex(ingredient, "INGREDIENT_ID"), ingredient);
        return ingredientDAL.updateIngredient(ingredient) != 0;
    }

    public boolean removeIngredient(String id) {
        List<Ingredient> list = new ArrayList<>();
        for (Ingredient ingredient : ingredientList){
            if (!ingredient.getIngredientID().contains(id)){
                list.add(ingredient);
            }
        }
        ingredientList = list;
        return ingredientDAL.deleteIngredient("INGREDIENT_ID = '" + id + "'") != 0;
    }

    public List<Ingredient> searchIngredients(String... conditions) {
        this.ingredientList = ingredientDAL.searchIngredients(conditions);
        return this.ingredientList;
    }

    public Object[][] getData (){
        Object [][] data = new Object[ingredientList.size()][];
        for (int i=0; i<data.length; i++){
            data[i] = ingredientList.get(i).toString().split(" \\| ");
        }
        return data;
    }

    public int getIndex(Ingredient ingredient, String key) {
        return IntStream.range(0, ingredientList.size())
            .filter(i -> Objects.equals(getValueByKey(ingredientList.get(i), key), getValueByKey(ingredient, key)))
            .findFirst()
            .orElse(-1);
    }

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

    public void readIngredient(){
        List<Ingredient> list = new ArrayList<>();
        for (Ingredient ingredient : ingredientList){
            if (!ingredient.isDeleted()){
                list.add(ingredient);
            }
        }
        ingredientList = list;
    }

    public List<Ingredient> finIngredients(String key, String value) {
        List<Ingredient> list = new ArrayList<>();
        for (Ingredient ingredient : ingredientList){
            if (getValueByKey(ingredient, key).toString().toLowerCase().contains(value.toLowerCase())){
                list.add(ingredient);
            }
        }
        return list;
    }

    public String getAutoID() {
        return ingredientDAL.getAutoID();
    }

}
