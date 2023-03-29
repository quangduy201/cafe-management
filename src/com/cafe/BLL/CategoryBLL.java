package com.cafe.BLL;

import com.cafe.DAL.CategoryDAL;
import com.cafe.DTO.Category;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class CategoryBLL {
    private CategoryDAL categoryDAL;
    private List<Category> categoryList;

    public CategoryBLL() {
        try {
            categoryDAL = new CategoryDAL();
            categoryList = searchCategories();
        } catch (Exception ignored) {

        }
    }

    public CategoryDAL getCategoryDAL() {
        return categoryDAL;
    }

    public void setCategoryDAL(CategoryDAL categoryDAL) {
        this.categoryDAL = categoryDAL;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public Object[][] getData() {
        Object[][] data = new Object[categoryList.size()][];
        for (int i = 0; i < data.length; i++) {
            data[i] = categoryList.get(i).toString().split(" \\| ");
        }
        return data;
    }

    public int getIndex(Category category, String key) {
        return IntStream.range(0, categoryList.size())
            .filter(i -> Objects.equals(getValueByKey(categoryList.get(i), key), getValueByKey(category, key)))
            .findFirst()
            .orElse(-1);
    }

    public Object getValueByKey(Category category, String key) {
        return switch (key) {
            case "CATEGORY_ID" -> category.getCategoryID();
            case "NAME" -> category.getName();
            case "QUANTITY" -> category.getQuantity();
            default -> null;
        };
    }

    public boolean insertCategory(Category category) {
        if (getIndex(category, "NAME") != -1) {
            System.out.println("Can't insert new category. Name already exists.");
            return false;
        }
        categoryList.add(category);
        return categoryDAL.insertCategory(category) != 0;
    }

    public boolean updateCategory(Category category) {
        categoryList.set(getIndex(category, "CATEGORY_ID"), category);
        return categoryDAL.updateCategory(category) != 0;
    }

    public boolean removeCategory(Category category) {
        categoryList.set(getIndex(category, "CATEGORY_ID"), category);
        return categoryDAL.deleteCategory("CATEGORY_ID = '" + category.getCategoryID() + "'") != 0;
    }

    public List<Category> searchCategories(String... conditions) {
        return categoryDAL.searchCategories(conditions);
    }

    public String getAutoID() {
        return categoryDAL.getAutoID();
    }
}
