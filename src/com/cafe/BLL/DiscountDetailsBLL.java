package com.cafe.BLL;

import com.cafe.DAL.DiscountDetailsDAL;
import com.cafe.DTO.Discount;
import com.cafe.DTO.DiscountDetails;

import java.util.List;
import java.util.Map;

public class DiscountDetailsBLL extends Manager<DiscountDetails> {
    private DiscountDetailsDAL discountDetailsDAL;
    private List<DiscountDetails> discountDetailsList;

    public DiscountDetailsBLL() {
        try {
            discountDetailsDAL = new DiscountDetailsDAL();
            discountDetailsList = searchDiscountDetails();
        } catch (Exception ignored) {

        }
    }

    public DiscountDetailsDAL getDiscountDetailsDAL() {
        return discountDetailsDAL;
    }

    public void setDiscountDetailsDAL(DiscountDetailsDAL discountDetailsDAL) {
        this.discountDetailsDAL = discountDetailsDAL;
    }

    public List<DiscountDetails> getDiscountDetailsList() {
        return discountDetailsList;
    }

    public void setDiscountDetailsList(List<DiscountDetails> discountDetailsList) {
        this.discountDetailsList = discountDetailsList;
    }

    public Object[][] getData() {
        return getData(discountDetailsList);
    }

    public boolean addDiscountDetails(DiscountDetails discountDetails) {
        discountDetailsList.add(discountDetails);
        return discountDetailsDAL.addDiscountDetails(discountDetails) != 0;
    }

    public List<DiscountDetails> searchDiscountDetails(String... conditions) {
        return discountDetailsDAL.searchDiscountDetails(conditions);
    }

    public List<DiscountDetails> findDiscountDetailsBy(Map<String, Object> conditions) {
        List<DiscountDetails> discountDetails = discountDetailsList;
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            discountDetails = findObjectsBy(entry.getKey(), entry.getValue(), discountDetails);
        return discountDetails;
    }

    @Override
    public Object getValueByKey(DiscountDetails discountDetails, String key) {
        return switch (key) {
            case "DISCOUNT_ID" -> discountDetails.getDiscountID();
            case "PRODUCT_ID" -> discountDetails.getProductID();
            default -> null;
        };
    }
}
