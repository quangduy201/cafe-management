package com.cafe.DAL;

import com.cafe.DTO.ReceiptDetails;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReceiptDetailsDAL extends Manager {
    public ReceiptDetailsDAL() {
        super("receipt_details",
            List.of("RECEIPT_ID",
                "INGREDIENT_ID",
                "QUANTITY")
        );
    }

    public List<ReceiptDetails> convertToReceiptDetails(List<List<String>> data) {
        return convert(data, row -> new ReceiptDetails(
            row.get(0), // receiptDetailsID
            row.get(1), // ingredientID
            Double.parseDouble(row.get(2)) // quantity
        ));
    }

    public int addReceiptDetails(ReceiptDetails receiptDetails) {
        try {
            return create(receiptDetails.getReceiptID(),
                receiptDetails.getIngredientID(),
                receiptDetails.getQuantity()
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
