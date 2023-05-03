package com.cafe.BLL;

import com.cafe.DAL.ReceiptDetailsDAL;
import com.cafe.DTO.ReceiptDetails;

import java.util.List;
import java.util.Map;

public class ReceiptDetailsBLL extends Manager<ReceiptDetails> {
    private ReceiptDetailsDAL receiptDetailsDAL;
    private List<ReceiptDetails> receiptDetailsList;

    public ReceiptDetailsBLL() {
        try {
            receiptDetailsDAL = new ReceiptDetailsDAL();
            receiptDetailsList = searchReceiptDetails();
        } catch (Exception ignored) {

        }
    }

    public ReceiptDetailsDAL getReceiptDetailsDAL() {
        return receiptDetailsDAL;
    }

    public void setReceiptDetailsDAL(ReceiptDetailsDAL receiptDetailsDAL) {
        this.receiptDetailsDAL = receiptDetailsDAL;
    }

    public List<ReceiptDetails> getReceiptDetailsList() {
        return receiptDetailsList;
    }

    public void setReceiptDetailsList(List<ReceiptDetails> receiptDetailsList) {
        this.receiptDetailsList = receiptDetailsList;
    }

    public Object[][] getData() {
        return getData(receiptDetailsList);
    }

    public boolean addReceiptDetails(ReceiptDetails receiptDetails) {
        receiptDetailsList.add(receiptDetails);
        return receiptDetailsDAL.addReceiptDetails(receiptDetails) != 0;
    }

    public List<ReceiptDetails> searchReceiptDetails(String... conditions) {
        return receiptDetailsDAL.searchReceiptDetails(conditions);
    }

    public List<ReceiptDetails> findReceiptDetailsBy(Map<String, Object> conditions) {
        List<ReceiptDetails> receiptDetails = receiptDetailsList;
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            receiptDetails = findObjectsBy(entry.getKey(), entry.getValue(), receiptDetails);
        return receiptDetails;
    }

    @Override
    public Object getValueByKey(ReceiptDetails receiptDetails, String key) {
        return switch (key) {
            case "RECEIPT_ID" -> receiptDetails.getReceiptID();
            case "INGREDIENT_ID" -> receiptDetails.getIngredientID();
            case "QUANTITY" -> receiptDetails.getQuantity();
            default -> null;
        };
    }
}
