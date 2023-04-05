package com.cafe.BLL;

import com.cafe.DAL.CategoryDAL;
import com.cafe.DTO.Category;
import com.cafe.DTO.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CategoryBLL extends Manager<Category> {
    private CategoryDAL categoryDAL;
    private List<Category> categoryList;

    public CategoryBLL() {
        try {
            categoryDAL = new CategoryDAL();
            categoryList = searchCategories("DELETED = 0");
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
        return getData(categoryList);
    }

    public boolean addCategory(Category category) {
        if (getIndex(category, "NAME", categoryList) != -1) {
            System.out.println("Can't add new category. Name already exists.");
            return false;
        }
        categoryList.add(category);
        return categoryDAL.addCategory(category) != 0;
    }

    public boolean updateCategory(Category category) {
        categoryList.set(getIndex(category, "CATEGORY_ID", categoryList), category);
        return categoryDAL.updateCategory(category) != 0;
    }

    public boolean deleteCategory(Category category) {
        categoryList.remove(getIndex(category, "CATEGORY_ID", categoryList));
        return categoryDAL.deleteCategory("CATEGORY_ID = '" + category.getCategoryID() + "'") != 0;
    }

    public List<Category> searchCategories(String... conditions) {
        return categoryDAL.searchCategories(conditions);
    }

    public List<Category> findCategories(String key, String value) {
        List<Category> list = new ArrayList<>();
        for (Category category : categoryList) {
            if (getValueByKey(category, key).toString().toLowerCase().contains(value.toLowerCase())) {
                list.add(category);
            }
        }
        return list;
    }

    public List<Category> findCategoriesBy(Map<String, Object> conditions) {
        List<Category> categories = categoryList;
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            categories = findObjectsBy(entry.getKey(), entry.getValue(), categories);
        return categories;
    }

    public String getAutoID() {
        return getAutoID("CA", 2, searchCategories());
    }

    @Override
    public Object getValueByKey(Category category, String key) {
        return switch (key) {
            case "CATEGORY_ID" -> category.getCategoryID();
            case "NAME" -> category.getName();
            case "QUANTITY" -> category.getQuantity();
            default -> null;
        };
    }

    public static void main(String[] args) {
        CategoryBLL categoryBLL = new CategoryBLL();
        List<Category> categories = categoryBLL.findCategoriesBy(Map.of("QUANTITY", 15, "NAME", "TRÀ"));
        for (Category category : categories)
            System.out.println(category);
    }
}
