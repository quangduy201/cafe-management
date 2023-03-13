package com.cafe.DAO;

import com.cafe.DTO.Bill;
import com.cafe.utils.Day;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillDAO extends Manager {
    public BillDAO() throws SQLException {
        super("bill", new ArrayList<>(
            List.of("BILL_ID",
                "CUSTOMER_ID",
                "STAFF_ID",
                "DOPURCHASE",
                "TOTAL",
                "DELETED")
        ));
    }
    public List<Bill> convertToBill(List<List<String>> data){
        List<Bill> billList = new ArrayList<>();
        try {
            String bill_ID, customer_ID, staff_ID;
            Day dayOfPurchase;
            double total;
            boolean deleted;
            for (List<String> row : data) {
                bill_ID = row.get(0);
                customer_ID = row.get(1);
                staff_ID = row.get(2);
                dayOfPurchase = Day.parseDay(row.get(3));
                total = Double.parseDouble(row.get(4));
                deleted = !row.get(5).contains("0");
                Bill bill = new Bill(bill_ID, customer_ID, staff_ID, dayOfPurchase, total, deleted);
                billList.add(bill);
            }
        } catch (Exception ignored){

        }
        return billList;
    }

    public List<Bill> readBills(String[] conditions){
        List<Bill> billList = new ArrayList<>();
        try {
            billList = convertToBill(read(conditions));
        } catch (Exception ignored){

        }
        return billList;
    }

    public int createBill(String bill_ID, String customer_ID, String staff_ID, Day dayOfPurchase, double total){
        if (readBills(new String[] {"BILL_ID = '" + bill_ID + "'"}).size() != 0){
            return 0;
        }
        try {
            return super.create(bill_ID, customer_ID, staff_ID, dayOfPurchase, 0, 0); // bill khi tạo mặc định total, deleted = 0
        } catch (Exception ignored) {
            return 0;
        }
    }

    public int updateBill(String bill_ID, String customer_ID, String staff_ID, Day dayOfPurchase){
        // Những giá trị là _ID sẽ được combobox cho user chọn
        try {
            Map<String, Object> updateValues = new HashMap<>();
            if (customer_ID != null){
                updateValues.put("CUSTOMER_ID", customer_ID);
            }
            if (staff_ID != null){
                updateValues.put("STAFF_ID", staff_ID);
            }
            if (dayOfPurchase != null){
                updateValues.put("DOPURCHASE",dayOfPurchase);
            }
            return super.update(updateValues, "BILL_ID = '" + bill_ID + "'");
        } catch (Exception e){
            return 0;
        }
    }

    public int deleteBill(String bill_ID){
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("DELETED", 1);
            return super.update(updateValues, "BILL_ID = '" + bill_ID + "'");
        } catch (Exception e){
            return 0;
        }
    }
}
