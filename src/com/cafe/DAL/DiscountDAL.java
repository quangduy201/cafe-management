package com.cafe.DAL;

import com.cafe.DTO.Discount;
import com.cafe.utils.Day;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscountDAL extends Manager {
    public DiscountDAL() throws SQLException {
        super("discount", new ArrayList<>(
            List.of("DISCOUNT_ID",
                "DISCOUNT_PERCENT",
                "START_DATE",
                "END_DATE",
                "STATUS",
                "DELETED")
        ));
    }

    public List<Discount> convertToDiscounts(List<List<String>> data) {
        return convert(data, row -> {
            try {
                return new Discount(
                    row.get(0), // discountID
                    Double.parseDouble(row.get(1)), // discountPercent
                    Day.parseDay(row.get(2)), // startDay
                    Day.parseDay(row.get(3)), // endDay
                    row.get(4), // status
                    Boolean.parseBoolean(row.get(5)) // deleted
                );
            } catch (Exception e) {
                System.out.println("Error occurred in DiscountDAL.convertToDiscounts(): " + e.getMessage());
            }
            return new Discount();
        });
    }

    public int insertDiscount(Discount discount) {
        try {
            return create(discount.getDiscountID(),
                discount.getDiscountID(),
                discount.getDiscountPercent(),
                discount.getStartDay(),
                discount.getEndDay(),
                discount.getStatus(),
                false
            ); // discount khi tạo mặc định deleted = 0
        } catch (Exception e) {
            System.out.println("Error occurred in DiscountDAL.insertDiscount(): " + e.getMessage());
        }
        return 0;
    }

    public int updateDiscount(Map<String, Object> updateValues, String... conditions) {
        try {
            return update(updateValues, conditions);
        } catch (Exception e) {
            System.out.println("Error occurred in DiscountDAL.updateDiscount(): " + e.getMessage());
        }
        return 0;
    }

    public int deleteDiscount(String... conditions) {
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("DELETED", 1);
            return update(updateValues, conditions);
        } catch (Exception e) {
            System.out.println("Error occurred in DiscountDAL.deleteDiscount(): " + e.getMessage());
        }
        return 0;
    }

    public List<Discount> searchDiscounts(String... conditions) {
        try {
            return convertToDiscounts(read(conditions));
        } catch (Exception e) {
            System.out.println("Error occurred in DiscountDAL.searchDiscounts(): " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public String getAutoID() {
        try {
            return getAutoID("DIS", 3);
        } catch (Exception e) {
            System.out.println("Error occurred in DiscountDAL.getAutoID(): " + e.getMessage());
        }
        return "";
    }
}
