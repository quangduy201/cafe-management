package com.cafe.BLL;

import com.cafe.DAL.ReceiptDAL;
import com.cafe.DTO.Receipt;

import java.util.List;

public class ReceiptBLL {
    private ReceiptDAL receiptDAL;

    public ReceiptBLL() {
        try {
            receiptDAL = new ReceiptDAL();
        } catch (Exception ignored) {

        }
    }

    public boolean insertReceipt(Receipt receipt) {
        return receiptDAL.insertReceipt(receipt) != 0;
    }

    public boolean updateReceipt(Receipt receipt) {
        return receiptDAL.updateReceipt(receipt) != 0;
    }

    public boolean removeReceipt(String id) {
        return receiptDAL.deleteReceipt(id) != 0;
    }

    public List<Receipt> searchReceipts(String... conditions) {
        return receiptDAL.searchReceipts(conditions);
    }

    public String getAutoID() {
        return receiptDAL.getAutoID();
    }
}
