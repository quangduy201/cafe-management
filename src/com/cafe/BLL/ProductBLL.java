package com.cafe.BLL;

import com.cafe.DAL.ProductDAL;
import com.cafe.DTO.Product;

import java.util.List;
import java.util.Map;

public class ProductBLL extends Manager<Product> {
    private ProductDAL productDAL;
    private List<Product> productList;

    public ProductBLL() {
        try {
            productDAL = new ProductDAL();
            productList = searchProducts();
        } catch (Exception ignored) {

        }
    }

    public ProductDAL getProductDAL() {
        return productDAL;
    }

    public void setProductDAL(ProductDAL productDAL) {
        this.productDAL = productDAL;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public Object[][] getData() {
        return getData(productList);
    }

    public boolean addProduct(Product product) {
        if (getIndex(product, "NAME", productList) != -1) {
            System.out.println("Can't add new product. Name already exists.");
            return false;
        }
        productList.add(product);
        return productDAL.addProduct(product) != 0;
    }

    public boolean updateProduct(Product product) {
        productList.set(getIndex(product, "PRODUCT_ID", productList), product);
        return productDAL.updateProduct(product) != 0;
    }

    public boolean deleteProduct(Product product) {
        product.setDeleted(true);
        productList.set(getIndex(product, "PRODUCT_ID", productList), product);
        return productDAL.deleteProduct("PRODUCT_ID = '" + product.getProductID() + "'") != 0;
    }

    public List<Product> searchProducts(String... conditions) {
        return productDAL.searchProducts(conditions);
    }

    public List<Product> findProductsBy(Map<String, Object> conditions) {
        List<Product> products = productList;
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            products = findObjectsBy(entry.getKey(), entry.getValue(), products);
        return products;
    }

    public String getAutoID() {
        try {
            return getAutoID("PR", 3, productList);
        } catch (Exception e) {
            System.out.println("Error occurred in ProductBLL.getAutoID(): " + e.getMessage());
        }
        return "";
    }

    @Override
    public Object getValueByKey(Product product, String key) {
        return switch (key) {
            case "PRODUCT_ID" -> product.getProductID();
            case "NAME" -> product.getName();
            case "CATEGORY_ID" -> product.getCategoryID();
            case "SIZE" -> product.getSize();
            case "COST" -> product.getCost();
            default -> null;
        };
    }
}
