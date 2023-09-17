package com.cafe.DAL;

import com.cafe.DTO.BillDetails;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillDetailsDAL extends Manager {
    public BillDetailsDAL() throws SQLException {
        super("bill_details",
            List.of("BILL_ID",
                "PRODUCT_ID",
                "QUANTITY",
                "NOTE",
                "TOTAL",
                "PERCENT")
        );
    }

    public List<BillDetails> convertToBillDetails(List<List<String>> data) {
        return convert(data, row -> new BillDetails(
            row.get(0), // billID
            row.get(1), // productID
            Integer.parseInt(row.get(2)), // quantity
            row.get(3), // note
            Double.parseDouble(row.get(4)), // total
            Double.parseDouble(row.get(5)) // percent
        ));
    }

    public int addBillDetails(BillDetails billDetails) {
        try {
            return create(billDetails.getBillID(),
                billDetails.getProductID(),
                billDetails.getQuantity(),
                billDetails.getNote(),
                billDetails.getTotal(),
                billDetails.getPercent()
            );
        } catch (Exception e) {
            System.out.println("Error occurred in BillDetailsDAL.addBillDetails(): " + e.getMessage());
        }
        return 0;
    }

    public List<BillDetails> searchBillDetails(String... conditions) {
        try {
            return convertToBillDetails(read(conditions));
        } catch (Exception e) {
            System.out.println("Error occurred in BillDetailsDAL.searchBillDetails(): " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
