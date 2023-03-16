package com.cafe.BLL;

import com.cafe.DAL.ProductDAL;
import com.cafe.DTO.Product;

import java.util.List;

public class ProductBLL {
    private ProductDAL productDAL;

    public ProductBLL() {
        try {
            productDAL = new ProductDAL();
        } catch (Exception ignored) {

        }
    }

    public boolean insertProduct(Product product) {
        return productDAL.insertProduct(product) != 0;
    }

    public boolean updateProduct(Product product) {
        return productDAL.updateProduct(product) != 0;
    }

    public boolean removeProduct(String id) {
        return productDAL.deleteProduct(id) != 0;
    }

    public List<Product> searchProducts(String... conditions) {
        return productDAL.searchProducts(conditions);
    }

    public String getAutoID() {
        return productDAL.getAutoID();
    }
}
