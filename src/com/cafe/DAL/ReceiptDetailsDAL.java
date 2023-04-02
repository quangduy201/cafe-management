package com.cafe.DAL;

import com.cafe.DTO.ReceiptDetails;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReceiptDetailsDAL extends Manager {
    public ReceiptDetailsDAL() throws SQLException {
        super("receipt_details", new ArrayList<>(
            List.of("RECEIPT_ID",
                "INGREDIENT_ID",
                "QUANTITY",
                "SUPPLIER_ID")
        ));
    }

    public List<ReceiptDetails> convertToReceiptDetails(List<List<String>> data) {
        return convert(data, row -> new ReceiptDetails(
            row.get(0), // receiptDetailsID
            row.get(1), // ingredientID
            Double.parseDouble(row.get(2)), // quantity
            row.get(3) // supplierID
        ));
    }

    public int addReceiptDetails(ReceiptDetails receiptDetails) {
        try {
            return create(receiptDetails.getReceiptID(),
                receiptDetails.getIngredientID(),
                receiptDetails.getQuantity(),
                receiptDetails.getSupplierID()
            );
        } catch (Exception e) {
            System.out.println("Error occurred in ReceiptDetailsDAL.addReceiptDetails(): " + e.getMessage());
        }
        return 0;
    }

    public List<ReceiptDetails> searchReceiptDetails(String... conditions) {
        try {
            return convertToReceiptDetails(read(conditions));
        } catch (Exception e) {
            System.out.println("Error occurred in ReceiptDetailsDAL.searchReceiptDetails(): " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
