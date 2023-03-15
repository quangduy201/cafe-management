package com.cafe.BLL;

import com.cafe.DAL.CategoryDAL;
import com.cafe.DTO.Category;

import java.util.List;
import java.util.Map;

public class CategoryBLL {
    private CategoryDAL categoryDAL;

    public CategoryBLL() {
        try {
            categoryDAL = new CategoryDAL();
        } catch (Exception ignored) {

        }
    }

    public boolean insertCategory(Category category) {
        if (searchCategories("NAME = '" + category.getName() + "'").size() != 0) {
            System.out.println("Can't insert new category. Name already exists.");
            return false;
        }
        return categoryDAL.insertCategory(category) != 0;
    }

    public boolean updateCategory(Map<String, Object> updateValues, String... conditions) {
        return categoryDAL.updateCategory(updateValues, conditions) != 0;
    }

    public boolean removeCategory(String id) {
        return categoryDAL.deleteCategory(id) != 0;
    }

    public List<Category> searchCategories(String... conditions) {
        return categoryDAL.searchCategories(conditions);
    }

    public String getAutoID() {
        return categoryDAL.getAutoID();
    }
}
