package com.cafe.DAO;

import com.cafe.DTO.Bill;
import com.cafe.DTO.Category;
import com.cafe.utils.Day;

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

    public List<Category> getCategories(){
        List<Category> categoryList = new ArrayList<Category>();
        try {
            CategoryDAO CategoryDAO = new CategoryDAO();
            String category_ID, name;
            int quantity;
            boolean deleted;
            List<List<String>> categories = CategoryDAO.read();
            for (List<String> row : categories) {
                category_ID = row.get(0);
                name = row.get(1);
                quantity = Integer.parseInt(row.get(2));
                if (row.get(3).indexOf("0") != -1){
                    deleted = false;
                }else {
                    deleted = true;
                }
                Category category = new Category(category_ID, name, quantity, deleted);
                categoryList.add(category);
            }
        } catch (Exception e){

        }
        return categoryList;
    }

    public Category readByPrimayKey (String category_ID){
        try {
            for (Category category: getCategories()) {
                if (category.getCategory_ID().indexOf(category_ID) != -1){
                    return category;
                }
            }
        } catch (Exception e){
            return null;
        }
        return  null;
    }

    public List<Category> readByName(String name){
        List<Category> result = new ArrayList<>();
        try {
            for (Category category: getCategories()) {
                if (category.getName().indexOf(name) != -1){
                    result.add(category);
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public List<Category> readByDeleted(Boolean deleted){
        List<Category> result = new ArrayList<>();
        try {
            for (Category category: getCategories()) {
                if (category.isDeleted() == deleted){
                    result.add(category);
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public int create(String category_ID, String name, int quanity){
        if (readByPrimayKey(category_ID) != null){
            return 0;
        }
        try {
            return super.create(category_ID, name, quanity, 0); // bill khi tạo mặc định deleted = 0
        } catch (Exception ignored) {
            return 0;
        }
    }

    public int delete(String category_ID){
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("DELETED", 1);
            return super.update(updateValues, "BILL_ID = '" + category_ID + "'");
        } catch (Exception e){
            return 0;
        }
    }
}
