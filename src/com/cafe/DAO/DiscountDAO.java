package com.cafe.DAO;
import com.cafe.DTO.Discount;
import com.cafe.utils.Day;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscountDAO extends Manager{
    public DiscountDAO() throws SQLException {
        super("discount", new ArrayList<>(
            List.of("DISCOUNT_ID",
                "DISCOUNT_PERCENT",
                "START_DATE",
                "END_DATE",
                "STATUS",
                "DELETED")
        ));
    }

    public List<Discount> convertToDiscount(List<List<String>> data){
        List<Discount> discountList = new ArrayList<>();
        try {
            String discount_ID, status;
            double discount_Percent;
            Day start_Day, end_Day;
            boolean deleted;
            for (List<String> row : data) {
                discount_ID = row.get(0);
                discount_Percent = Double.parseDouble(row.get(1));
                start_Day = Day.parseDay(row.get(2));
                end_Day = Day.parseDay(row.get(3));
                if (row.get(4).contains("0")){
                    status = "Available";
                }else {
                    status = "Unavailable";
                }
                deleted = !row.get(5).contains("0");
                Discount discount = new Discount(discount_ID, status, discount_Percent, start_Day, end_Day, deleted);
                discountList.add(discount);
            }
        } catch (Exception ignored){

        }
        return discountList;
    }

    public List<Discount> readDiscounts(String[] conditions){
        List<Discount> discountList = new ArrayList<>();
        try {
            discountList = convertToDiscount(read(conditions));
        } catch (Exception ignored){

        }
        return discountList;
    }

    public int createDiscount(String discount_ID, String status, double discount_Percent, Day start_Day, Day end_Day){
        if (readDiscounts(new String[] {"DISCOUNT_ID '" + discount_ID + "'"}).size() != 0){
            return 0;
        }
        try {
            return super.create(discount_ID, discount_Percent, start_Day, end_Day, status, 0);
        } catch (Exception e){
            return 0;
        }
    }

    public int updateDiscount(String discount_ID, String status){
        // Những giá trị là _ID sẽ được combobox cho user chọn
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("STATUS", status);
            return super.update(updateValues, "DISCOUNT_ID = '" + discount_ID + "'");
        } catch (Exception e){
            return 0;
        }
    }

    public int updateDiscount(String discount_ID, double discount_Percent){
        // Những giá trị là _ID sẽ được combobox cho user chọn
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("DISCOUNT_PERCENT", discount_Percent);
            return super.update(updateValues, "DISCOUNT_ID = '" + discount_ID + "'");
        } catch (Exception e){
            return 0;
        }
    }

    public int deleteDiscount(String discount_ID){
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("DELETED", 1);
            return super.update(updateValues, "DISCOUNT_ID = '" + discount_ID + "'");
        } catch (Exception e){
            return 0;
        }
    }
}
