package com.cafe.DAO;

import com.cafe.DTO.Account;
import com.cafe.DTO.Bill;
import com.cafe.DTO.BillDetails;
import com.cafe.utils.Day;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillDetailsDAO extends Manager{
    public BillDetailsDAO() throws SQLException {
        super("bill_details",  new ArrayList<>(
            List.of("BILL_ID",
                    "PRODUCT_ID",
                    "QUANTITY")
        ));
    }

    public List<BillDetails> getBillDetails(){
        List<BillDetails> billDetailsList = new ArrayList<BillDetails>();
        try {
            BillDetailsDAO billDetailsDAO = new BillDetailsDAO();
            String bill_ID, product_ID;
            int quantity;
            List<List<String>> billDetails = billDetailsDAO.read();
            for (List<String> row : billDetails) {
                bill_ID = row.get(0);
                product_ID = row.get(1);
                quantity = Integer.parseInt(row.get(2));
                BillDetails newBillDetails1 = new BillDetails(bill_ID, product_ID, quantity);
                billDetailsList.add(newBillDetails1);
            }
        } catch (Exception e){

        }
        return billDetailsList;
    }

    public BillDetails readByPrimayKey(String bill_ID, String product_ID){
        try {
            for (BillDetails billDetails: getBillDetails()) {
                if (billDetails.getBill_ID() ==  bill_ID && billDetails.getProduct_ID() == product_ID){
                    return billDetails;
                }
            }
        } catch (Exception e){
            return null;
        }
        return null;
    }

    public List<BillDetails> readByBill_id(String bill_ID){
        List<BillDetails> result = new ArrayList<>();
        try {
            for (BillDetails billDetails: getBillDetails()) {
                if (billDetails.getBill_ID().indexOf(bill_ID) != -1){
                    result.add(billDetails);
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public List<BillDetails> readByProduct_ID(String product_ID){
        List<BillDetails> result = new ArrayList<>();
        try {
            for (BillDetails billDetails: getBillDetails()) {
                if (billDetails.getProduct_ID().indexOf(product_ID) != -1){
                    result.add(billDetails);
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public int create(String bill_ID, String product_ID, int quantity){
        if (readByPrimayKey(bill_ID,product_ID) != null){
            return 0;
        }
        try {
            return super.create(bill_ID, product_ID, quantity);
        } catch (Exception e){
            return 0;
        }
    }
}
