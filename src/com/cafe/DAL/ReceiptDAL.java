package com.cafe.DAL;

import com.cafe.DTO.Receipt;
import com.cafe.utils.Day;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReceiptDAL extends Manager {
    public ReceiptDAL() throws SQLException {
        super("receipt", new ArrayList<>(
            List.of("RECEIPT_ID",
                "STAFF_ID",
                "DOR",
                "GRAND_TOTAL",
                "DELETED")
        ));
    }

    public List<Receipt> convertToReceipts(List<List<String>> data) {
        return convert(data, row -> {
            try {
                return new Receipt(
                    row.get(0), // receiptID
                    row.get(1), // staffID
                    Day.parseDay(row.get(2)),// dor
                    Double.parseDouble(row.get(3)), // grandTotal
                    Boolean.parseBoolean(row.get(5)) // deleted
                );
            } catch (Exception e) {
                System.out.println("Error occurred in ReceiptDAL.convertToReceipts(): " + e.getMessage());
            }
            return new Receipt();
        });
    }

    public int insertReceipt(Receipt receipt) {
        try {
            return create(receipt.getReceiptID(),
                receipt.getStaffID(),
                receipt.getDor(),
                receipt.getGrandTotal(),
                false
            ); // receipt khi tạo mặc định deleted = 0
        } catch (Exception e) {
            System.out.println("Error occurred in ReceiptDAL.insertReceipt(): " + e.getMessage());
        }
        return 0;
    }

    public int updateReceipt(Receipt receipt) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(receipt.getReceiptID());
            updateValues.add(receipt.getStaffID());
            updateValues.add(receipt.getDor());
            updateValues.add(receipt.getGrandTotal());
            updateValues.add(receipt.isDeleted());
            return update(updateValues, "RECEIPT_ID = " + receipt.getReceiptID());
        } catch (Exception e) {
            System.out.println("Error occurred in ReceiptDAL.updateReceipt(): " + e.getMessage());
        }
        return 0;
    }

    public int deleteReceipt(String... conditions) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(1);
            return update(updateValues, conditions);
        } catch (Exception e) {
            System.out.println("Error occurred in ReceiptDAL.deleteReceipt(): " + e.getMessage());
        }
        return 0;
    }

    public List<Receipt> searchReceipts(String... conditions) {
        try {
            return convertToReceipts(read(conditions));
        } catch (Exception e) {
            System.out.println("Error occurred in ReceiptDAL.searchReceipts(): " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public String getAutoID() {
        try {
            return getAutoID("REC", 3);
        } catch (Exception e) {
            System.out.println("Error occurred in ReceiptDAL.getAutoID(): " + e.getMessage());
        }
        return "";
    }
}
