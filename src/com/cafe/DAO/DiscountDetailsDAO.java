package com.cafe.DAO;
import com.cafe.DTO.DiscountDetails;
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

    public List<DiscountDetails> convertToDiscountDetails(List<List<String>> data){
        List<DiscountDetails> discountDetailsList = new ArrayList<>();
        try {
            String discount_ID, product_ID;
            for (List<String> row : data) {
                discount_ID = row.get(0);
                product_ID = row.get(1);
                DiscountDetails newDiscountDetails = new DiscountDetails(discount_ID, product_ID);
                discountDetailsList.add(newDiscountDetails);
            }
        } catch (Exception ignored){

        }
        return discountDetailsList;
    }

    public List<DiscountDetails> readDiscountDetails(String[] consitions){
        List<DiscountDetails> discountDetailsList = new ArrayList<>();
        try {
            discountDetailsList = convertToDiscountDetails(read(consitions));
        } catch (Exception ignored){

        }
        return discountDetailsList;
    }
}
