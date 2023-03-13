package com.cafe.DAO;


import com.cafe.DTO.Category;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryDAO extends Manager{
    public CategoryDAO () throws SQLException {
        super("category",new ArrayList<>(
            List.of("CATEGORY_ID",
                "NAME",
                "QUANTITY",
                "DELETED")
        ));
    }

    public List<Category> convertToCategory(List<List<String>> data){
        List<Category> categoryList = new ArrayList<>();
        try {
            String category_ID, name;
            int quantity;
            boolean deleted;
            for (List<String> row : data) {
                category_ID = row.get(0);
                name = row.get(1);
                quantity = Integer.parseInt(row.get(2));
                deleted = !row.get(3).contains("0");
                Category category = new Category(category_ID, name, quantity, deleted);
                categoryList.add(category);
            }
        } catch (Exception ignored){

        }
        return categoryList;
    }

    public List<Category> readCategories(String[] conditions){
        List<Category> categoryList = new ArrayList<>();
        try {
            categoryList = convertToCategory(read(conditions));
        } catch (Exception ignored){

        }
        return categoryList;
    }

    public int createCategory(String category_ID, String name, int quanity){
        if (readCategories(new String[]{"CATEGORY_ID = '" + category_ID + "'"}).size() != 0){
            return 0;
        }
        try {
            return super.create(category_ID, name, quanity, 0); // bill khi tạo mặc định deleted = 0
        } catch (Exception ignored) {
            return 0;
        }
    }

    public int deleteCategory(String category_ID){
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("DELETED", 1);
            return super.update(updateValues, "BILL_ID = '" + category_ID + "'");
        } catch (Exception e){
            return 0;
        }
    }
}
