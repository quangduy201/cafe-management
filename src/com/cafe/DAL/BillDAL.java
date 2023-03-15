package com.cafe.DAL;

import com.cafe.DTO.Bill;
import com.cafe.utils.Day;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillDAL extends Manager {
    public BillDAL() throws SQLException {
        super("bill", new ArrayList<>(
            List.of("BILL_ID",
                "CUSTOMER_ID",
                "STAFF_ID",
                "DOPURCHASE",
                "TOTAL",
                "DELETED")
        ));
    }

    public List<Bill> convertToBills(List<List<String>> data) {
        return convert(data, row -> {
            try {
                return new Bill(
                    row.get(0), // billID
                    row.get(1), // customerID
                    row.get(2), // staffID
                    Day.parseDay(row.get(3)), // dateOfPurchase
                    Double.parseDouble(row.get(4)), // total
                    Boolean.parseBoolean(row.get(5)) // deleted
                );
            } catch (Exception e) {
                System.out.println("Error occurred in BillDAL.convertToBills(): " + e.getMessage());
            }
            return new Bill();
        });
    }

    public int insertBill(Bill bill) {
        try {
            return create(bill.getBillID(),
                bill.getCustomerID(),
                bill.getStaffID(),
                bill.getDateOfPurchase(),
                bill.getTotal(),
                false
            ); // bill khi tạo mặc định total, deleted = 0
        } catch (Exception e) {
            System.out.println("Error occurred in BillDAL.insertBill(): " + e.getMessage());
        }
        return 0;
    }

    public int updateBill(Map<String, Object> updateValues, String... conditions) {
        try {
            return update(updateValues, conditions);
        } catch (Exception e) {
            System.out.println("Error occurred in BillDAL.updateBill(): " + e.getMessage());
            return 0;
        }
    }

    public int deleteBill(String... conditions) {
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("DELETED", 1);
            return update(updateValues, conditions);
        } catch (Exception e) {
            System.out.println("Error occurred in BillDAL.deleteBill(): " + e.getMessage());
        }
        return 0;
    }

    public List<Bill> searchBills(String... conditions) {
        try {
            return convertToBills(read(conditions));
        } catch (Exception e) {
            System.out.println("Error occurred in BillDAL.searchBills(): " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public String getAutoID() {
        try {
            return getAutoID("INV", 4);
        } catch (Exception e) {
            System.out.println("Error occurred in AccountDAL.getAutoID(): " + e.getMessage());
        }
        return "";
    }
}