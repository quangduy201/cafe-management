package com.cafe.DAL;

import com.cafe.DTO.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAL extends Manager {
    public ProductDAL() throws SQLException {
        super("product", new ArrayList<>(
            List.of("PRODUCT_ID",
                "NAME",
                "CATEGORY_ID",
                "SIZED",
                "COST",
                "IMAGE",
                "DELETED")
        ));
    }

    public List<Product> convertToProducts(List<List<String>> data) {
        return convert(data, row -> {
            row.set(row.size()-1, row.get(row.size()-1).equals("0") ? "false" : "true");
            return new Product(
                row.get(0), // productID
                row.get(1), // name
                row.get(2), // categoryID
                row.get(3), // sized
                Double.parseDouble(row.get(4)), // cost,
                row.get(5), // image
                Boolean.parseBoolean(row.get(6))    // deleted
            );
        });
    }

    public int insertProduct(Product product) {
        try {
            return create(product.getProductID(),
                product.getName(),
                product.getCategoryID(),
                product.getSized(),
                product.getCost(),
                product.getImage(),
                false
            ); // product khi tạo mặc định deleted = 0
        } catch (Exception e) {
            System.out.println("Error occurred in ProductDAL.insertProduct(): " + e.getMessage());
        }
        return 0;
    }

    public int updateProduct(Product product) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(product.getProductID());
            updateValues.add(product.getName());
            updateValues.add(product.getCategoryID());
            updateValues.add(product.getSized());
            updateValues.add(product.getCost());
            updateValues.add(product.getImage());
            updateValues.add(product.isDeleted());
            return update(updateValues, "PRODUCT_ID = '" + product.getProductID() + "'");
        } catch (Exception e) {
            System.out.println("Error occurred in ProductDAL.updateProduct(): " + e.getMessage());
        }
        return 0;
    }

    public int deleteProduct(String... conditions) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(1);
            return update(updateValues, conditions);
        } catch (Exception e) {
            System.out.println("Error occurred in ProductDAL.deleteProduct(): " + e.getMessage());
        }
        return 0;
    }

    public List<Product> searchProducts(String... conditions) {
        try {
            return convertToProducts(read(conditions));
        } catch (Exception e) {
            System.out.println("Error occurred in ProductDAL.searchProducts(): " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public String getAutoID() {
        try {
            return getAutoID("PR", 3);
        } catch (Exception e) {
            System.out.println("Error occurred in ProductDAL.getAutoID(): " + e.getMessage());
        }
        return "";
    }
}
