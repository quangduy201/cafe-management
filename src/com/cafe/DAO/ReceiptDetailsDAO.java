package com.cafe.DAO;

import com.cafe.DTO.ReceiptDetails;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReceiptDetailsDAO extends Manager{
    public ReceiptDetailsDAO() throws SQLException {
        super("receipt_details", new ArrayList<>(
            List.of("RECEIPT_ID",
                "INGREDIENT_ID",
                "QUANTITY",
                "SUPPLIER_ID")
        ));
    }

    public List<ReceiptDetails> convertToReceiptDetail(List<List<String>> data){
        List<ReceiptDetails> receiptDetailsList = new ArrayList<>();
        try{
            String receipt_ID, ingredient_ID, supplier_ID;
            double quantity;
            for (List<String> row : data){
                receipt_ID = row.get(0);
                ingredient_ID = row.get(1);
                quantity = Double.parseDouble(row.get(2));
                supplier_ID = row.get(3);
                ReceiptDetails newReceiptDetails = new ReceiptDetails(receipt_ID, ingredient_ID, supplier_ID, quantity);
                receiptDetailsList.add(newReceiptDetails);
            }
        } catch(Exception ignored){

        }
        return receiptDetailsList;
    }

    public List<ReceiptDetails> readReceiptDetails(String[] conditions){
        List<ReceiptDetails> receiptDetailsList = new ArrayList<>();
        try {
            receiptDetailsList = convertToReceiptDetail(read(conditions));
        } catch (Exception ignored){

        }
        return receiptDetailsList;
    }

    public int createReceiptDetails(String receipt_ID, String ingredient_ID, String supplier_ID, double quantity){
        if (readReceiptDetails(new String[]{"RECEIPT_ID = '" + receipt_ID + "'", "INGREDIENT_ID = '" + ingredient_ID + "'"}).size() != 0){
            return 0;
        }
        try {
            return super.create(receipt_ID, ingredient_ID, quantity, supplier_ID, 0); // tài khoản khi tạo mặc định deleted = 0
        } catch (Exception ignored) {
            return 0;
        }
    }
}
