package com.cafe.BLL;

import com.cafe.DAL.DiscountDetailsDAL;
import com.cafe.DTO.DiscountDetails;

import java.util.List;

public class DiscountDetailsBLL {
    private DiscountDetailsDAL discountDetailsDAL;

    public DiscountDetailsBLL() {
        try {
            discountDetailsDAL = new DiscountDetailsDAL();
        } catch (Exception ignored) {

        }
    }

    public boolean insertDiscountDetails(DiscountDetails discountDetails) {
        return discountDetailsDAL.insertDiscountDetails(discountDetails) != 0;
    }

    public List<DiscountDetails> searchDiscountDetails(String... conditions) {
        return discountDetailsDAL.searchDiscountDetails(conditions);
    }
}
