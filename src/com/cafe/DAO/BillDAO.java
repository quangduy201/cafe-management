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
    public List<Bill> getBills(){
        List<Bill> billList = new ArrayList<Bill>();
        try {
            BillDAO billDAO = new BillDAO();
            String bill_ID, customer_ID, staff_ID;
            Day dayOfPurchase;
            double total;
            boolean deleted;
            List<List<String>> bills = billDAO.read();
            for (List<String> row : bills) {
                bill_ID = row.get(0);
                customer_ID = row.get(1);
                staff_ID = row.get(2);
                dayOfPurchase = Day.parseDay(row.get(3));
                total = Double.parseDouble(row.get(4));
                if (row.get(5).indexOf("0") != -1){
                    deleted = false;
                }else {
                    deleted = true;
                }
                Bill bill = new Bill(bill_ID, customer_ID, staff_ID, dayOfPurchase, total, deleted);
                billList.add(bill);
            }
        } catch (Exception e){

        }
        return billList;
    }

    public Bill readByPrimayKey (String bill_ID){
        try {
            for (Bill bill: getBills()) {
                if (bill.getBill_ID().indexOf(bill_ID) != -1){
                    return bill;
                }
            }
        } catch (Exception e){
            return null;
        }
        return  null;
    }

    public List<Bill> readByCustomer_ID(String customer_ID){
        List<Bill> result = new ArrayList<>();
        try {
            for (Bill bill: getBills()) {
                if (bill.getCustomer_ID().indexOf(customer_ID) != -1){
                    result.add(bill);
                }
            }
        } catch (Exception e){

        }
        return result;
    }
    public List<Bill> readByStaff_ID(String staff_ID){
        List<Bill> result = new ArrayList<>();
        try {
            for (Bill bill: getBills()) {
                if (bill.getStaff_ID().indexOf(staff_ID) != -1){
                    result.add(bill);
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public List<Bill> readByDayOfPurchase(Day dayOfPurchase){
        List<Bill> result = new ArrayList<>();
        try {
            for (Bill bill: getBills()) {
                if (bill.getDayOfPurchase().equals(dayOfPurchase)){
                    result.add(bill);
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public List<Bill> readByDayOfPurchase(Day dayOfPurchase1, Day dayOfPurchase2){
        List<Bill> result = new ArrayList<>();
        try {
            for (Bill bill: getBills()) {
                if (bill.getDayOfPurchase().compare(dayOfPurchase1,dayOfPurchase2)){
                    result.add(bill);
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public List<Bill> readByTotal(String compare, double total){
        List<Bill> result = new ArrayList<>();
        try {
            if (compare.indexOf("<") != -1){
                for (Bill bill: getBills()) {
                    if (bill.getTotal() < total){
                        result.add(bill);
                    }
                }
            } else if (compare.indexOf("<=") != -1) {
                for (Bill bill: getBills()) {
                    if (bill.getTotal() <= total){
                        result.add(bill);
                    }
                }
            } else if (compare.indexOf(">") != -1) {
                for (Bill bill: getBills()) {
                    if (bill.getTotal() > total){
                        result.add(bill);
                    }
                }
            } else if (compare.indexOf(">=") != -1) {
                for (Bill bill: getBills()) {
                    if (bill.getTotal() >= total){
                        result.add(bill);
                    }
                }
            } else if (compare.indexOf("=") != -1) {
                for (Bill bill: getBills()) {
                    if (bill.getTotal() == total){
                        result.add(bill);
                    }
                }
            }

        } catch (Exception e){

        }
        return result;
    }

    public List<Bill> readByTotal(double total1, double total2){
        List<Bill> result = new ArrayList<>();
        try {
            for (Bill bill: getBills()) {
                if (bill.getTotal() >= total1 && bill.getTotal() <= total2){
                    result.add(bill);
                }
            }

        } catch (Exception e){

        }
        return result;
    }

    public List<Bill> readByDeleted(boolean deleted){
        List<Bill> result = new ArrayList<>();
        try {
            for (Bill bill: getBills()) {
                if (bill.isDeleted() == deleted){
                    result.add(bill);
                }
            }

        } catch (Exception e){

        }
        return result;
    }

    public int create(String bill_ID, String customer_ID, String staff_ID, Day dayOfPurchase, double total){
        if (readByPrimayKey(bill_ID) != null){
            return 0;
        }
        try {
            return super.create(bill_ID, customer_ID, staff_ID, dayOfPurchase, 0, 0); // bill khi tạo mặc định total, deleted = 0
        } catch (Exception ignored) {
            return 0;
        }
    }

    public int update(String bill_ID, String customer_ID, String staff_ID, Day dayOfPurchase){
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

    public int delete(String bill_ID){
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("DELETED", 1);
            return super.update(updateValues, "BILL_ID = '" + bill_ID + "'");
        } catch (Exception e){
            return 0;
        }
    }
}
