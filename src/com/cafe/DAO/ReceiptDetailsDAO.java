package com.cafe.DAO;

import com.cafe.DTO.ReceiptDetails;
import com.cafe.DTO.ReceivedNote;
import com.cafe.utils.Day;

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

    public List<ReceiptDetails> getReceiptDetails(){
        List<ReceiptDetails> receiptDetailsList = new ArrayList<ReceiptDetails>() {
        };
        try{
            ReceiptDetailsDAO receiptDetailsDAO = new ReceiptDetailsDAO();
            List<List<String>> receiptDetails = receiptDetailsDAO.read();
            for (List<String> row : receiptDetails){
                String receipt_ID, ingredient_ID, supplier_ID;
                double quantity;
                receipt_ID = row.get(0);
                ingredient_ID = row.get(1);
                quantity = Double.parseDouble(row.get(2));
                supplier_ID = row.get(3);
                ReceiptDetails newReceiptDetails = new ReceiptDetails(receipt_ID, ingredient_ID, supplier_ID, quantity);
                receiptDetailsList.add(newReceiptDetails);
            }
        } catch(Exception e){

        }
        return receiptDetailsList;
    }

    public ReceiptDetails readByPrimaryKey(String receipt_ID, String ingredient_ID){
        try {
            for (ReceiptDetails receiptDetails : getReceiptDetails()){
                if (receiptDetails.getReceipt_ID().indexOf(receipt_ID) != -1 && receiptDetails.getIngredient_ID().indexOf(ingredient_ID) != -1){
                    return receiptDetails;
                }
            }
        } catch(Exception e){
            return null;
        }
        return null;
    }

    public List<ReceiptDetails> readByReceipt_ID(String receipt_ID){
        List<ReceiptDetails> result = new ArrayList<ReceiptDetails>();
        try {
            for (ReceiptDetails receiptDetails : getReceiptDetails()){
                if (receiptDetails.getReceipt_ID().indexOf(receipt_ID) != -1){
                    result.add(receiptDetails);
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public List<ReceiptDetails> readByIngredient_ID(String ingredient_ID){
        List<ReceiptDetails> result = new ArrayList<ReceiptDetails>();
        try {
            for (ReceiptDetails receiptDetails : getReceiptDetails()){
                if (receiptDetails.getIngredient_ID().indexOf(ingredient_ID) != -1){
                    result.add(receiptDetails);
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public List<ReceiptDetails> readBySupplier_ID(String supplier_ID){
        List<ReceiptDetails> result = new ArrayList<ReceiptDetails>();
        try {
            for (ReceiptDetails receiptDetails : getReceiptDetails()){
                if (receiptDetails.getSupplier_ID().indexOf(supplier_ID) != -1){
                    result.add(receiptDetails);
                }
            }
        } catch (Exception e){

        }
        return result;
    }

}
