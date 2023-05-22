package com.cafe.BLL;

import com.cafe.DAL.DiscountDetailsDAL;
import com.cafe.DTO.DiscountDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DiscountDetailsBLL extends Manager<DiscountDetails> {
    private DiscountDetailsDAL discountDetailsDAL;
    private List<DiscountDetails> discountDetailsList;

    public DiscountDetailsBLL() {
        try {
            discountDetailsDAL = new DiscountDetailsDAL();
            discountDetailsList = searchDiscountDetails("DELETED = 0");
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

    public boolean updateDiscountDetails(DiscountDetails discountDetails) {
        discountDetailsList.add(discountDetails);
        return discountDetailsDAL.updateDiscountDetails(discountDetails) != 0;
    }

    public boolean deleteDiscountDetails(DiscountDetails discountDetails) {
        discountDetailsList.remove(getIndex(discountDetails));
        return discountDetailsDAL.deletedDiscountDetails(discountDetails) != 0;
    }

    public List<DiscountDetails> searchDiscountDetails(String... conditions) {
        return discountDetailsDAL.searchDiscountDetails(conditions);
    }

    public List<DiscountDetails> findDiscountDetails(String key, String value) {
        List<DiscountDetails> list = new ArrayList<>();
        for (DiscountDetails discountDetail : discountDetailsList)
            if (getValueByKey(discountDetail, key).toString().toLowerCase().contains(value.toLowerCase()))
                list.add(discountDetail);
        return list;
    }

    public List<DiscountDetails> findDiscountDetailsBy(Map<String, Object> conditions) {
        List<DiscountDetails> discountDetails = discountDetailsList;
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            discountDetails = findObjectsBy(entry.getKey(), entry.getValue(), discountDetails);
        return discountDetails;
    }

    public int getIndex(DiscountDetails discountDetails) {
        for (int i = 0; i < discountDetailsList.size(); i++) {
            if (discountDetailsList.get(i).getDiscountID().equals(discountDetails.getDiscountID()) && discountDetailsList.get(i).getProductID().equals(discountDetails.getProductID())) {
                return i;
            }
        }
        return -1;
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
