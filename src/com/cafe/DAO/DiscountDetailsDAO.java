package com.cafe.DAO;

import com.cafe.DTO.Discount;
import com.cafe.DTO.DiscountDetails;
import com.cafe.utils.Day;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiscountDetailsDAO extends Manager{
    public DiscountDetailsDAO() throws SQLException {
        super("discount_details", new ArrayList<>(
            List.of("DISCOUNT_ID",
                "PRODUCT_ID")
        ));
    }

    public List<DiscountDetails> getDiscountDetails(){
        List<DiscountDetails> discountDetailsList = new ArrayList<DiscountDetails>();
        try {
            DiscountDetailsDAO DiscountDetailsDAO = new DiscountDetailsDAO();
            String discount_ID, product_ID;
            List<List<String>> discountDetails = DiscountDetailsDAO.read();
            for (List<String> row : discountDetails) {
                discount_ID = row.get(0);
                product_ID = row.get(1);
                DiscountDetails newDiscountDetails = new DiscountDetails(discount_ID, product_ID);
                discountDetailsList.add(newDiscountDetails);
            }
        } catch (Exception e){

        }
        return discountDetailsList;
    }

    public DiscountDetails readByPrimaryKey(String discount_ID, String product_ID){
        try {
            for (DiscountDetails discountDetails: getDiscountDetails()) {
                if (discountDetails.getDiscount_ID().indexOf(discount_ID) != -1 && discountDetails.getProduct_ID().indexOf(product_ID) != -1){
                    return discountDetails;
                }
            }
        } catch (Exception e){
            return null;
        }
        return  null;
    }

    public List<DiscountDetails> readByDiscount_ID(String discount_ID){
        List<DiscountDetails> result = new ArrayList<DiscountDetails>();
        try {
            for (DiscountDetails discountDetails: getDiscountDetails()) {
                if (discountDetails.getDiscount_ID().indexOf(discount_ID) != -1){
                    result.add(discountDetails);
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public List<DiscountDetails> readByProduct_ID(String product_ID){
        List<DiscountDetails> result = new ArrayList<DiscountDetails>();
        try {
            for (DiscountDetails discountDetails: getDiscountDetails()) {
                if (discountDetails.getProduct_ID().indexOf(product_ID) != -1){
                    result.add(discountDetails);
                }
            }
        } catch (Exception e){

        }
        return result;
    }
}
