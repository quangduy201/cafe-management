package com.cafe.DAO;

import com.cafe.DTO.BillDetails;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillDetailsDAO extends Manager{
    public BillDetailsDAO() throws SQLException {
        super("bill_details",  new ArrayList<>(
            List.of("BILL_ID",
                    "PRODUCT_ID",
                    "QUANTITY")
        ));
    }

    public List<BillDetails> convertToBillDetails(List<List<String>> data){
        List<BillDetails> billDetailsList = new ArrayList<>();
        try {
            String bill_ID, product_ID;
            int quantity;
            for (List<String> row : data) {
                bill_ID = row.get(0);
                product_ID = row.get(1);
                quantity = Integer.parseInt(row.get(2));
                BillDetails newBillDetails1 = new BillDetails(bill_ID, product_ID, quantity);
                billDetailsList.add(newBillDetails1);
            }
        } catch (Exception ignored){

        }
        return billDetailsList;
    }

    public List<BillDetails> readBillDetails(String[] conditions){
        List<BillDetails> billDetailsList = new ArrayList<>();
        try {
            billDetailsList = convertToBillDetails(read(conditions));
        } catch (Exception ignored){

        }
        return billDetailsList;
    }

    public int createBillDetails(String bill_ID, String product_ID, int quantity){
        if (readBillDetails(new String[]{"BILL_ID = '" + bill_ID + "'", "PRODUCT_ID = '" + product_ID + "'"}).size() != 0){
            return 0;
        }
        try {
            return super.create(bill_ID, product_ID, quantity);
        } catch (Exception e){
            return 0;
        }
    }
}
