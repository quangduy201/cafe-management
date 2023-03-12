package com.cafe.DAO;

import com.cafe.DTO.CustomerDTO;
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

    public List<Discount> getDiscounts(){
        List<Discount> discountList = new ArrayList<Discount>();
        try {
            DiscountDAO DiscountDAO = new DiscountDAO();
            String discount_ID, status;
            double discount_Percent;
            Day start_Day, end_Day;
            boolean deleted;
            List<List<String>> discounts = DiscountDAO.read();
            for (List<String> row : discounts) {
                discount_ID = row.get(0);
                discount_Percent = Double.parseDouble(row.get(1));
                start_Day = Day.parseDay(row.get(2));
                end_Day = Day.parseDay(row.get(3));
                if (row.get(4).indexOf("0") != -1){
                    status = "Available";
                }else {
                    status = "Unavailable";
                }
                if (row.get(5).indexOf("0") != -1){
                    deleted = false;
                }else {
                    deleted = true;
                }
                Discount discount = new Discount(discount_ID, status, discount_Percent, start_Day, end_Day, deleted);
                discountList.add(discount);
            }
        } catch (Exception e){

        }
        return discountList;
    }

    public Discount readByPrimaryKey(String discount_ID){
        try {
            for (Discount discount: getDiscounts()) {
                if (discount.getDiscount_ID().indexOf(discount_ID) != -1){
                    return discount;
                }
            }
        } catch (Exception e){
            return null;
        }
        return  null;
    }

    public List<Discount> readByStatus(String status){
        List<Discount> result = new ArrayList<Discount>();
        try {
            for (Discount discount: getDiscounts()) {
                if (discount.getStatus().indexOf(status) != -1){
                    result.add(discount);
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public List<Discount> readByDiscount_Percent(double discount_Percent){
        List<Discount> result = new ArrayList<Discount>();
        try {
            for (Discount discount: getDiscounts()) {
                if (discount.getDiscount_Percent() == discount_Percent){
                    result.add(discount);
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public List<Discount> readByDeleted(boolean deleted){
        List<Discount> result = new ArrayList<Discount>();
        try {
            for (Discount discount: getDiscounts()) {
                if (discount.isDeleted() == deleted){
                    result.add(discount);
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public int create(String discount_ID, String status, double discount_Percent, Day start_Day, Day end_Day){
        if (readByPrimaryKey(discount_ID) != null){
            return 0;
        }
        try {
            return super.create(discount_ID, discount_Percent, start_Day, end_Day, status, 0);
        } catch (Exception e){
            return 0;
        }
    }

    public int update(String discount_ID, String status){
        // Những giá trị là _ID sẽ được combobox cho user chọn
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("STATUS", status);
            return super.update(updateValues, "DISCOUNT_ID = '" + discount_ID + "'");
        } catch (Exception e){
            return 0;
        }
    }

    public int update(String discount_ID, double discount_Percent){
        // Những giá trị là _ID sẽ được combobox cho user chọn
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("DISCOUNT_PERCENT", discount_Percent);
            return super.update(updateValues, "DISCOUNT_ID = '" + discount_ID + "'");
        } catch (Exception e){
            return 0;
        }
    }

    public int delete(String discount_ID){
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("DELETED", 1);
            return super.update(updateValues, "DISCOUNT_ID = '" + discount_ID + "'");
        } catch (Exception e){
            return 0;
        }
    }
}
