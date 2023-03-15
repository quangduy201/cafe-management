package com.cafe.BLL;

import com.cafe.DAL.ReceiptDetailsDAL;
import com.cafe.DTO.ReceiptDetails;

import java.util.List;

public class ReceiptDetailsBLL {
    private ReceiptDetailsDAL receiptDetailsDAL;

    public ReceiptDetailsBLL() {
        try {
            receiptDetailsDAL = new ReceiptDetailsDAL();
        } catch (Exception ignored) {

        }
    }

    public boolean insertReceiptDetails(ReceiptDetails receiptDetails) {
        return receiptDetailsDAL.insertReceiptDetails(receiptDetails) != 0;
    }

    public List<ReceiptDetails> searchReceiptDetails(String... conditions) {
        return receiptDetailsDAL.searchReceiptDetails(conditions);
    }
}
