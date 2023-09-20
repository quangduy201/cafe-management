package com.cafe.BLL;

import com.cafe.DAL.ProductDAL;
import com.cafe.DTO.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductBLL extends Manager<Product> {
    private ProductDAL productDAL;
    private List<Product> productList;

    public ProductBLL() {
        productDAL = new ProductDAL();
        productList = searchProducts("DELETED = 0");
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
        productList.add(product);
        return productDAL.addProduct(product) != 0;
    }

    public boolean updateProduct(Product product) {
        productList.set(getIndex(product, "PRODUCT_ID", productList), product);
        return productDAL.updateProduct(product) != 0;
    }

    public boolean deleteProduct(Product product) {
        productList.remove(getIndex(product, "PRODUCT_ID", productList));
        return productDAL.deleteProduct("PRODUCT_ID = '" + product.getProductID() + "'") != 0;
    }

    public List<Product> searchProducts(String... conditions) {
        return productDAL.searchProducts(conditions);
    }

    public List<Product> findProducts(String key, String value) {
        List<Product> list = new ArrayList<>();
        if (key.equals("SIZED")) {
            for (Product product : productList)
                if (getValueByKey(product, key).toString().equals(value))
                    list.add(product);
        } else {
            for (Product product : productList)
                if (getValueByKey(product, key).toString().toLowerCase().contains(value.toLowerCase()))
                    list.add(product);
        }
        return list;
    }

    public List<Product> findProductsBy(Map<String, Object> conditions) {
        List<Product> products = productList;
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            products = findObjectsBy(entry.getKey(), entry.getValue(), products);
        return products;
    }

    public boolean exists(Product product) {
        return !findProductsBy(Map.of(
            "NAME", product.getName(),
            "CATEGORY_ID", product.getCategoryID(),
            "SIZED", product.getSized(),
            "COST", product.getCost(),
            "IMAGE", product.getImage()
        )).isEmpty();
    }

    public boolean exists(Map<String, Object> conditions) {
        return !findProductsBy(conditions).isEmpty();
    }

    public String getAutoID() {
        return getAutoID("PR", 3, searchProducts());
    }

    @Override
    public Object getValueByKey(Product product, String key) {
        return switch (key) {
            case "PRODUCT_ID" -> product.getProductID();
            case "NAME" -> product.getName();
            case "CATEGORY_ID" -> product.getCategoryID();
            case "SIZED" -> product.getSized();
            case "COST" -> product.getCost();
            case "IMAGE" -> product.getImage();
            default -> null;
        };
    }
}
