package com.cafe.DAL;

import com.cafe.DTO.Category;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public int addCategory(Category category) {
        try {
            return create(category.getCategoryID(),
                category.getName(),
                category.getQuantity(),
                false
            ); // category khi tạo mặc định deleted = 0
        } catch (Exception e) {
            System.out.println("Error occurred in CategoryDAL.addCategory(): " + e.getMessage());
        }
        return 0;
    }

    public int updateCategory(Category category) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(category.getCategoryID());
            updateValues.add(category.getName());
            updateValues.add(category.getQuantity());
            updateValues.add(category.isDeleted());
            return update(updateValues, "CATEGORY_ID = " + category.getCategoryID());
        } catch (Exception e) {
            System.out.println("Error occurred in CategoryDAL.updateCategory(): " + e.getMessage());
        }
        return 0;
    }

    public int deleteCategory(String... conditions) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(1);
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
}
