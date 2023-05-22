package com.cafe.BLL;

import com.cafe.DAL.BillDetailsDAL;
import com.cafe.DTO.BillDetails;

import java.util.List;
import java.util.Map;

public class BillDetailsBLL extends Manager<BillDetails> {
    private BillDetailsDAL billDetailsDAL;
    private List<BillDetails> billDetailsList;

    public BillDetailsBLL() {
        try {
            billDetailsDAL = new BillDetailsDAL();
            billDetailsList = searchBillDetails();
        } catch (Exception ignored) {

        }
    }

    public BillDetailsDAL getBillDetailsDAL() {
        return billDetailsDAL;
    }

    public void setBillDetailsDAL(BillDetailsDAL billDetailsDAL) {
        this.billDetailsDAL = billDetailsDAL;
    }

    public List<BillDetails> getBillDetailsList() {
        return billDetailsList;
    }

    public void setBillDetailsList(List<BillDetails> billDetailsList) {
        this.billDetailsList = billDetailsList;
    }

    public Object[][] getData() {
        return getData(billDetailsList);
    }

    public boolean addBillDetails(BillDetails billDetails) {
        billDetailsList.add(billDetails);
        return billDetailsDAL.addBillDetails(billDetails) != 0;
    }

    public List<BillDetails> searchBillDetails(String... conditions) {
        return billDetailsDAL.searchBillDetails(conditions);
    }

    public List<BillDetails> findBillDetailsBy(Map<String, Object> conditions) {
        List<BillDetails> bills = billDetailsList;
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            bills = findObjectsBy(entry.getKey(), entry.getValue(), bills);
        return bills;
    }

    @Override
    public Object getValueByKey(BillDetails billDetails, String key) {
        return switch (key) {
            case "BILL_ID" -> billDetails.getBillID();
            case "PRODUCT_ID" -> billDetails.getProductID();
            case "QUANTITY" -> billDetails.getQuantity();
            case "TOTAL" -> billDetails.getTotal();
            case "PERCENT" -> billDetails.getPercent();
            default -> null;
        };
    }
}
