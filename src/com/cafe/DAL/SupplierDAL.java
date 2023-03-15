package com.cafe.DAL;

import com.cafe.DTO.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplierDAL extends Manager {
    public SupplierDAL() throws SQLException {
        super("supplier", new ArrayList<>(
            List.of("SUPPLIER_ID",
                "NAME",
                "PHONE",
                "ADDRESS",
                "EMAIL",
                "PRICE",
                "DELETED")
        ));
    }

    public List<Supplier> convertToSupplier(List<List<String>> data) {
        List<Supplier> supplierList = new ArrayList<>();
        try {
            String supplier_ID, name, phone, address, email;
            double price;
            boolean deleted;
            for (List<String> row : data) {
                supplier_ID = row.get(0);
                name = row.get(1);
                phone = row.get(2);
                address = row.get(3);
                email = row.get(4);
                price = Double.parseDouble(row.get(5));
                deleted = !row.get(6).contains("0");
                Supplier supplier = new Supplier(supplier_ID, name, phone, address, email, price, deleted);
                supplierList.add(supplier);
            }
        } catch (Exception ignored) {

        }
        return supplierList;
    }

    public List<Supplier> readSuppliers(String[] conditions) {
        List<Supplier> supplierList = new ArrayList<>();
        try {
            supplierList = convertToSupplier(read(conditions));
        } catch (Exception ignored) {

        }
        return supplierList;
    }

    public int createSupplier(String supplier_ID, String name, String phone, String address, String email, double price) {
        if (readSuppliers(new String[]{"SUPPLIER_ID = '" + supplier_ID + "'"}).size() != 0) {
            return 0;
        }
        try {
            return super.create(supplier_ID, name, phone, address, email, price, 0);
        } catch (Exception e) {
            return 0;
        }
    }

    public int updateSupplier(Map<String, Object> updateValues, String... conditions) {
        try {
            return super.update(updateValues, conditions);
        } catch (Exception e) {
            return 0;
        }
    }

    public int deleteSupplier(String... id) {
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("DELETED", 1);
            return super.update(updateValues, id);
        } catch (Exception e) {
            return 0;
        }
    }
}
