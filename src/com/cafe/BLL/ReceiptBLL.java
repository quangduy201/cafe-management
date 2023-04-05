package com.cafe.BLL;

import com.cafe.DAL.ReceiptDAL;
import com.cafe.DTO.Receipt;

import java.util.List;
import java.util.Map;

public class ReceiptBLL extends Manager<Receipt> {
    private ReceiptDAL receiptDAL;
    private List<Receipt> receiptList;

    public ReceiptBLL() {
        try {
            receiptDAL = new ReceiptDAL();
            receiptList = searchReceipts();
        } catch (Exception ignored) {

        }
    }

    public ReceiptDAL getReceiptDAL() {
        return receiptDAL;
    }

    public void setReceiptDAL(ReceiptDAL receiptDAL) {
        this.receiptDAL = receiptDAL;
    }

    public List<Receipt> getReceiptList() {
        return receiptList;
    }

    public void setReceiptList(List<Receipt> receiptList) {
        this.receiptList = receiptList;
    }

    public Object[][] getData() {
        return getData(receiptList);
    }

    public boolean addReceipt(Receipt receipt) {
        receiptList.add(receipt);
        return receiptDAL.addReceipt(receipt) != 0;
    }

    public boolean updateReceipt(Receipt receipt) {
        receiptList.set(getIndex(receipt, "RECEIPT_ID", receiptList), receipt);
        return receiptDAL.updateReceipt(receipt) != 0;
    }

    public boolean removeReceipt(Receipt receipt) {
        receiptList.remove(getIndex(receipt, "RECEIPT_ID", receiptList));
        return receiptDAL.deleteReceipt("RECEIPT_ID = '" + receipt.getReceiptID() + "'") != 0;
    }

    public List<Receipt> searchReceipts(String... conditions) {
        return receiptDAL.searchReceipts(conditions);
    }

    public List<Receipt> findReceiptsBy(Map<String, Object> conditions) {
        List<Receipt> receipts = receiptList;
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            receipts = findObjectsBy(entry.getKey(), entry.getValue(), receipts);
        return receipts;
    }

    public String getAutoID() {
        return getAutoID("REC", 3, searchReceipts());
    }

    @Override
    public Object getValueByKey(Receipt receipt, String key) {
        return switch (key) {
            case "CATEGORY_ID" -> receipt.getReceiptID();
            case "STAFF_ID" -> receipt.getStaffID();
            case "DOR" -> receipt.getDor();
            case "GRAND_TOTAL" -> receipt.getGrandTotal();
            default -> null;
        };
    }
}
