package com.cafe.BLL;

import com.cafe.DAL.DiscountDAL;
import com.cafe.DTO.Decentralization;
import com.cafe.DTO.Discount;

import java.util.List;
import java.util.Map;

public class DiscountBLL extends Manager<Discount> {
    private DiscountDAL discountDAL;
    private List<Discount> discountList;

    public DiscountBLL() {
        try {
            discountDAL = new DiscountDAL();
            discountList = searchDiscounts();
        } catch (Exception ignored) {

        }
    }

    public DiscountDAL getDiscountDAL() {
        return discountDAL;
    }

    public void setDiscountDAL(DiscountDAL discountDAL) {
        this.discountDAL = discountDAL;
    }

    public List<Discount> getDiscountList() {
        return discountList;
    }

    public void setDiscountList(List<Discount> discountList) {
        this.discountList = discountList;
    }

    public Object[][] getData() {
        return getData(discountList);
    }

    public boolean addDiscount(Discount discount) {
        discountList.add(discount);
        return discountDAL.addDiscount(discount) != 0;
    }

    public boolean updateDiscount(Discount discount) {
        discountList.set(getIndex(discount, "DISCOUNT_ID", discountList), discount);
        return discountDAL.updateDiscount(discount) != 0;
    }

    public boolean deleteDiscount(Discount discount) {
        discount.setDeleted(true);
        discountList.set(getIndex(discount, "DISCOUNT_ID", discountList), discount);
        return discountDAL.deleteDiscount("DISCOUNT_ID = '" + discount.getDiscountID() + "'") != 0;
    }

    public List<Discount> searchDiscounts(String... conditions) {
        return discountDAL.searchDiscounts(conditions);
    }

    public List<Discount> findDiscountBy(Map<String, Object> conditions) {
        List<Discount> discounts = discountList;
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            discounts = findObjectsBy(entry.getKey(), entry.getValue(), discounts);
        return discounts;
    }

    public String getAutoID() {
        try {
            return getAutoID("DIS", 3, discountList);
        } catch (Exception e) {
            System.out.println("Error occurred in DiscountBLL.getAutoID(): " + e.getMessage());
        }
        return "";
    }

    @Override
    public Object getValueByKey(Discount discount, String key) {
        return switch (key) {
            case "CATEGORY_ID" -> discount.getDiscountID();
            case "DISCOUNT_PERCENT" -> discount.getDiscountPercent();
            case "START_DATE" -> discount.getStartDay();
            case "END_DATE" -> discount.getEndDay();
            case "STATUS" -> discount.getStatus();
            default -> null;
        };
    }
}
