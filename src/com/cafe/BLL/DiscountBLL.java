package com.cafe.BLL;

import com.cafe.DAL.DiscountDAL;
import com.cafe.DTO.Discount;

import java.util.List;
import java.util.Map;

public class DiscountBLL {
    private DiscountDAL discountDAL;

    public DiscountBLL() {
        try {
            discountDAL = new DiscountDAL();
        } catch (Exception ignored) {

        }
    }

    public boolean insertDiscount(Discount discount) {
        return discountDAL.insertDiscount(discount) != 0;
    }

    public boolean updateDiscount(Map<String, Object> updateValues, String... conditions) {
        return discountDAL.updateDiscount(updateValues, conditions) != 0;
    }

    public boolean removeDiscount(String id) {
        return discountDAL.deleteDiscount(id) != 0;
    }

    public List<Discount> searchDiscounts(String... conditions) {
        return discountDAL.searchDiscounts(conditions);
    }

    public String getAutoID() {
        return discountDAL.getAutoID();
    }
}