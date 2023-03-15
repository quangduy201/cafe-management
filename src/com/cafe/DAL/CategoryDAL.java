package com.cafe.DAL;


import com.cafe.DTO.Category;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryDAL extends Manager {
    public CategoryDAL() throws SQLException {
        super("category", new ArrayList<>(
            List.of("CATEGORY_ID",
                "NAME",
                "QUANTITY",
                "DELETED")
        ));
    }

    public List<Category> convertToCategories(List<List<String>> data) {
        return convert(data, row -> new Category(
            row.get(0), // categoryID
            row.get(1), // name
            Integer.parseInt(row.get(2)), // quantity
            Boolean.parseBoolean(row.get(3)) // deleted
        ));
    }

    public int insertCategory(Category category) {
        try {
            return create(category.getCategoryID(),
                category.getCategoryID(),
                category.getName(),
                category.getQuantity(),
                false
            ); // category khi tạo mặc định deleted = 0
        } catch (Exception e) {
            System.out.println("Error occurred in CategoryDAL.insertCategory(): " + e.getMessage());
        }
        return 0;
    }

    public int updateCategory(Map<String, Object> updateValues, String... conditions) {
        try {
            return update(updateValues, conditions);
        } catch (Exception e) {
            System.out.println("Error occurred in CategoryDAL.updateCategory(): " + e.getMessage());
        }
        return 0;
    }

    public int deleteCategory(String... conditions) {
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("DELETED", 1);
            return update(updateValues, conditions);
        } catch (Exception e) {
            System.out.println("Error occurred in CategoryDAL.deleteCategory(): " + e.getMessage());
        }
        return 0;
    }

    public List<Category> searchCategories(String... conditions) {
        try {
            return convertToCategories(read(conditions));
        } catch (Exception e) {
            System.out.println("Error occurred in CategoryDAL.searchCategories(): " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public String getAutoID() {
        try {
            return getAutoID("CA", 2);
        } catch (Exception e) {
            System.out.println("Error occurred in CategoryDAL.getAutoID(): " + e.getMessage());
        }
        return "";
    }
}
