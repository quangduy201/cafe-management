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

    public List<Product> getProducts(){
        List<Product> productList = new ArrayList<Product>() {
        };
        try{
            ProductDAO productDAO = new ProductDAO();
            List<List<String>> products = productDAO.read();
            for (List<String> row : products){
                String product_ID, name, category_ID;
                double cost;
                boolean deleted;
                product_ID = row.get(0);
                name = row.get(1);
                category_ID = row.get(2);
                cost = Double.parseDouble(row.get(3));
                if (row.get(4).indexOf("0") != -1){
                    deleted = false;
                } else {
                    deleted = true;
                }
                Product product = new Product(product_ID, name, category_ID, cost, deleted);
                productList.add(product);
            }
        } catch(Exception e){

        }
        return productList;
    }

    public Product readByPrimaryKey(String product_ID){
        try {
            for (Product product : getProducts()){
                if (product.getProduct_ID().indexOf(product_ID) != -1){
                    return product;
                }
            }
        } catch(Exception e){
            return null;
        }
        return null;
    }

    public List<Product> readByName(String name){
        List<Product> result = new ArrayList<Product>();
        try {
            for (Product product : getProducts()){
                if (product.getName().indexOf(name) != -1){
                    result.add(product);
                }
            }
        } catch(Exception e){

        }
        return result;
    }

    public List<Product> readByCategory_ID(String category_ID){
        List<Product> result = new ArrayList<Product>();
        try {
            for (Product product : getProducts()){
                if (product.getCategory_ID().indexOf(category_ID) != -1){
                    result.add(product);
                }
            }
        } catch(Exception e){

        }
        return result;
    }

    public List<Product> readByCost(String compare, double cost){
        List<Product> result = new ArrayList<Product>();
        try {
            if (compare.indexOf("<") != -1){
                for (Product product : getProducts()) {
                    if (product.getCost() < cost) {
                        result.add(product);
                    }
                }
            } else if (compare.indexOf("<=") != -1){
                for (Product product : getProducts()){
                    if (product.getCost() <= cost){
                        result.add(product);
                    }
                }
            } else if (compare.indexOf("=") != -1){
                for (Product product : getProducts()){
                    if (product.getCost() == cost){
                        result.add(product);
                    }
                }
            } else if (compare.indexOf(">") != -1){
                for (Product product : getProducts()){
                    if (product.getCost() > cost){
                        result.add(product);
                    }
                }
            } else if (compare.indexOf(">=") != -1){
                for (Product product : getProducts()){
                    if (product.getCost() >= cost){
                        result.add(product);
                    }
                }
            }
        } catch(Exception e){

        }
        return result;
    }

    public List<Product> readByCost(double cost1, double cost2){
        List<Product> result = new ArrayList<Product>();
        try {
            for (Product product : getProducts()){
                if (product.getCost() >= cost1 && product.getCost() <= cost2){
                    result.add(product);
                }
            }
        } catch(Exception e){
        }
        return result;
    }

    public List<Product> readByDeleted(boolean deleted){
        List<Product> result = new ArrayList<Product>();
        try {
            for (Product product : getProducts()){
                if (product.isDeleted() == deleted){
                    result.add(product);
                }
            }
        } catch(Exception e){
        }
        return result;
    }
    public int create(String product_ID, String name, String category_ID, double cost){
        if (readByPrimaryKey(product_ID) != null){
            return 0;
        }
        try {
            return super.create(product_ID, name, category_ID, cost, 0);
        } catch(Exception e){
            return 0;
        }
    }

    public int update(String product_ID, String name, String category_ID){
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

    public int update(String product_ID, double cost){
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("COST", cost);
            return super.update(updateValues, "PRODUCT_ID = '"  + product_ID + "'");
        } catch(Exception e){
            return 0;
        }
    }

    public int delete(String product_ID){
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("DELETED", 1);
            return super.update(updateValues, "PRODUCT_ID = '"  + product_ID + "'");
        } catch(Exception e){
            return 0;
        }
    }

}
