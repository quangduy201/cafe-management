package com.cafe.BLL;

import com.cafe.DAL.ProductDAL;
import com.cafe.DTO.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class ProductBLL {
    private ProductDAL productDAL;
    private List<Product> productList;
    public ProductBLL() {
        try {
            productDAL = new ProductDAL();
            productList = searchProducts();
            readProduct();
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

    public boolean insertProduct(Product product) {
        productList.add(product);
        return productDAL.insertProduct(product) != 0;
    }

    public boolean updateProduct(Product product) {
        productList.set(getIndex(product, "PRODUCT_ID"), product);
        return productDAL.updateProduct(product) != 0;
    }

    public boolean removeProduct(String id) {
        List<Product> list = new ArrayList<>();
        for (Product product : productList){
            if (!product.getProductID().contains(id)){
                list.add(product);
            }
        }
        productList = list;
        return productDAL.deleteProduct("PRODUCT_ID = '" + id + "'") != 0;
    }

    public List<Product> searchProducts(String... conditions) {
        this.productList = productDAL.searchProducts(conditions);
        return this.productList;
    }

    public Object[][] getData (){
        Object [][] data = new Object[productList.size()][];
        for (int i=0; i<data.length; i++){
            data[i] = productList.get(i).toString().split(" \\| ");
        }
        return data;
    }

    public int getIndex(Product product, String key) {
        return IntStream.range(0, productList.size())
            .filter(i -> Objects.equals(getValueByKey(productList.get(i), key), getValueByKey(product, key)))
            .findFirst()
            .orElse(-1);
    }

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

    public void readProduct(){
        List<Product> list = new ArrayList<>();
        for (Product product : productList){
            if (!product.isDeleted()){
                list.add(product);
            }
        }
        productList = list;
    }

    public List<Product> findProducts(String key, String value){
        List<Product> list = new ArrayList<>();
        for (Product product : productList){
            if (getValueByKey(product, key).toString().contains(value)){
                list.add(product);
            }
        }
        return list;
    }
    public String getAutoID() {
        return productDAL.getAutoID();
    }

}
