package com.cafe.DAL;

import com.cafe.DTO.Discount;
import com.cafe.utils.Day;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public int updateDiscount(Discount discount) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(discount.getDiscountID());
            updateValues.add(discount.getDiscountPercent());
            updateValues.add(discount.getStartDay());
            updateValues.add(discount.getEndDay());
            updateValues.add(discount.getStatus());
            updateValues.add(discount.isDeleted());
            return update(updateValues, "DISCOUNT_ID = '" + discount.getDiscountID() + "'");
        } catch (Exception e) {
            System.out.println("Error occurred in DiscountDAL.updateDiscount(): " + e.getMessage());
        }
        return 0;
    }

    public int deleteDiscount(String... conditions) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(true);
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
