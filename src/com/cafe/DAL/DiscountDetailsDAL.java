package com.cafe.DAL;

import com.cafe.DTO.DiscountDetails;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiscountDetailsDAL extends Manager {
    public DiscountDetailsDAL() throws SQLException {
        super("discount_details", new ArrayList<>(
            List.of("DISCOUNT_ID",
                "PRODUCT_ID")
        ));
    }

    public List<DiscountDetails> convertToDiscountDetails(List<List<String>> data) {
        return convert(data, row -> new DiscountDetails(
            row.get(0), // discountID
            row.get(1) // productID
        ));
    }

    public int addDiscountDetails(DiscountDetails discountDetails) {
        try {
            return create(discountDetails.getDiscountID(),
                discountDetails.getProductID()
            );
        } catch (Exception e) {
            System.out.println("Error occurred in DiscountDetailsDAL.addDiscountDetails(): " + e.getMessage());
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
