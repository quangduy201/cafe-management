package com.cafe.BLL;

import com.cafe.DAL.ProductDAL;
import com.cafe.DTO.Product;

import java.util.List;

public class ProductBLL {
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

    public boolean insertProduct(Product product) {
        return productDAL.insertProduct(product) != 0;
    }

    public boolean updateProduct(Product product) {
        return productDAL.updateProduct(product) != 0;
    }

    public boolean removeProduct(String id) {
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
    public String getAutoID() {
        return productDAL.getAutoID();
    }

}
