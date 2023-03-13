package com.cafe.DAO;

import com.cafe.DTO.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDAO extends Manager{
    public ProductDAO() throws SQLException {
        super("product", new ArrayList<>(
            List.of("PRODUCT_ID",
                "NAME",
                "CATEGORY_ID",
                "COST",
                "DELETED")
        ));
    }

    public List<Product> convertToProduct(List<List<String>> data){
        List<Product> productList = new ArrayList<>() {
        };
        try{
            String product_ID, name, category_ID;
            double cost;
            boolean deleted;
            for (List<String> row : data){
                product_ID = row.get(0);
                name = row.get(1);
                category_ID = row.get(2);
                cost = Double.parseDouble(row.get(3));
                deleted = !row.get(4).contains("0");
                Product product = new Product(product_ID, name, category_ID, cost, deleted);
                productList.add(product);
            }
        } catch(Exception ignored){

        }
        return productList;
    }

    public List<Product> readProducts(String[] conditions){
        List<Product> productList = new ArrayList<>();
        try {
            productList = convertToProduct(read(conditions));
        } catch(Exception ignored){

        }
        return productList;
    }

    public int createProduct(String product_ID, String name, String category_ID, double cost){
        if (readProducts(new String[] {"PRODUCT_ID '" + product_ID + "'"}).size() != 0){
            return 0;
        }
        try {
            return super.create(product_ID, name, category_ID, cost, 0);
        } catch(Exception e){
            return 0;
        }
    }

    public int updateProduct(String product_ID, String name, String category_ID){
        try {
            Map<String, Object> updateValues = new HashMap<>();
            if (name != null){
                updateValues.put("NAME", name);
            }
            if (category_ID != null){
                updateValues.put("CATEGORY_ID", category_ID);
            }
            return super.update(updateValues, "PRODUCT_ID = '"  + product_ID + "'");
        } catch(Exception e){
            return 0;
        }
    }

    public int updateProduct(String product_ID, double cost){
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("COST", cost);
            return super.update(updateValues, "PRODUCT_ID = '"  + product_ID + "'");
        } catch(Exception e){
            return 0;
        }
    }

    public int deleteProduct(String product_ID){
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("DELETED", 1);
            return super.update(updateValues, "PRODUCT_ID = '"  + product_ID + "'");
        } catch(Exception e){
            return 0;
        }
    }

}
