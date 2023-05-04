package com.cafe.DAL;

import com.cafe.DTO.Discount;
import com.cafe.DTO.DiscountDetails;
import com.cafe.utils.Day;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiscountDetailsDAL extends Manager {
    public DiscountDetailsDAL() throws SQLException {
        super("discount_details",
            List.of("DISCOUNT_ID",
                "PRODUCT_ID",
                "DELETED")
        );
    }

    public List<DiscountDetails> convertToDiscountDetails(List<List<String>> data) {
        return convert(data, row -> {
            try {
                return new DiscountDetails(
                    row.get(0), // discountID
                    row.get(1), // productID
                    Boolean.parseBoolean(row.get(2)) // deleted
                );
            } catch (Exception e) {
                System.out.println("Error occurred in DiscountDetailsDAL.convertToDiscountDetails(): " + e.getMessage());
            }
            return new DiscountDetails();
        });
    }

    public int addDiscountDetails(DiscountDetails discountDetails) {
        try {
            return create(discountDetails.getDiscountID(),
                discountDetails.getProductID(),
                false
            );
        } catch (Exception e) {
            System.out.println("Error occurred in DiscountDetailsDAL.addDiscountDetails(): " + e.getMessage());
        }
        return 0;
    }

    public int updateDiscountDetails(DiscountDetails discountDetails) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(discountDetails.getDiscountID());
            updateValues.add(discountDetails.getProductID());
            updateValues.add(discountDetails.isDeleted());
            return update(updateValues, "DISCOUNT_ID = '" + discountDetails.getDiscountID() + "'", "PRODUCT_ID = '" + discountDetails.getProductID() + "'");
        } catch (Exception e) {
            System.out.println("Error occurred in DiscountDetailsDAL.deletedDiscountDetails(): " + e.getMessage());
        }
        return 0;
    }

    public int deletedDiscountDetails(DiscountDetails discountDetails) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(discountDetails.getDiscountID());
            updateValues.add(discountDetails.getProductID());
            updateValues.add(true);
            return update(updateValues, "DISCOUNT_ID = '" + discountDetails.getDiscountID() + "'", "PRODUCT_ID = '" + discountDetails.getProductID() + "'");
        } catch (Exception e) {
            System.out.println("Error occurred in DiscountDetailsDAL.deletedDiscountDetails(): " + e.getMessage());
        }
        return 0;
    }

    public List<DiscountDetails> searchDiscountDetails(String... conditions) {
        try {
            return convertToDiscountDetails(read(conditions));
        } catch (Exception e) {
            System.out.println("Error occurred in DiscountDetailsDAL.searchDiscountDetails(): " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
