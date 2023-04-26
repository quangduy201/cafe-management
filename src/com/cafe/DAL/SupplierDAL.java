package com.cafe.DAL;

import com.cafe.DTO.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAL extends Manager {
    public SupplierDAL() throws SQLException {
        super("supplier", new ArrayList<>(
            List.of("SUPPLIER_ID",
                "NAME",
                "PHONE",
                "ADDRESS",
                "EMAIL",
                "DELETED")
        ));
    }

    public List<Supplier> convertToSuppliers(List<List<String>> data) {
        return convert(data, row -> {
            row.set(row.size() - 1, row.get(row.size() - 1).equals("0") ? "false" : "true");
            return new Supplier(
                row.get(0), // supplierID
                row.get(1), // name
                row.get(2), // phone
                row.get(3), // address
                row.get(4), // email
                Boolean.parseBoolean(row.get(5)) // deleted
            );
        });
    }

    public int addSupplier(Supplier supplier) {
        try {
            return create(supplier.getSupplierID(),
                supplier.getName(),
                supplier.getPhone(),
                supplier.getAddress(),
                supplier.getEmail(),
                false
            ); // supplier khi tạo mặc định deleted = 0
        } catch (Exception e) {
            System.out.println("Error occurred in SupplierDAL.addSupplier(): " + e.getMessage());
        }
        return 0;
    }

    public int updateSupplier(Supplier supplier) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(supplier.getSupplierID());
            updateValues.add(supplier.getName());
            updateValues.add(supplier.getPhone());
            updateValues.add(supplier.getAddress());
            updateValues.add(supplier.getEmail());
            updateValues.add(supplier.isDeleted());
            return update(updateValues, "SUPPLIER_ID = '" + supplier.getSupplierID() + "'");
        } catch (Exception e) {
            System.out.println("Error occurred in SupplierDAL.updateSupplier(): " + e.getMessage());
        }
        return 0;
    }

    public int deleteSupplier(String... conditions) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(true);
            return update(updateValues, conditions);
        } catch (Exception e) {
            System.out.println("Error occurred in SupplierDAL.deleteSupplier(): " + e.getMessage());
        }
        return 0;
    }

    public List<Supplier> searchSuppliers(String... conditions) {
        try {
            return convertToSuppliers(read(conditions));
        } catch (Exception e) {
            System.out.println("Error occurred in SupplierDAL.searchSuppliers(): " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
