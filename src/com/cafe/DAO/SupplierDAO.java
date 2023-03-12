package com.cafe.DAO;

import com.cafe.DTO.Staff;
import com.cafe.DTO.Supplier;
import com.cafe.utils.Day;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplierDAO extends Manager{
    public SupplierDAO() throws SQLException {
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

    public List<Supplier> getSuppliers(){
        List<Supplier> supplierList = new ArrayList<Supplier>() {
        };
        try{
            SupplierDAO supplierDAO = new SupplierDAO();
            List<List<String>> suppliers = supplierDAO.read();
            for (List<String> row : suppliers){
                String supplier_ID, name, phone, address, email;
                double price;
                boolean deleted;
                supplier_ID = row.get(0);
                name = row.get(1);
                phone = row.get(2);
                address = row.get(3);
                email = row.get(4);
                price = Double.parseDouble(row.get(5));
                if (row.get(6).indexOf("0") != -1){
                    deleted = false;
                }else {
                    deleted = true;
                }
                Supplier supplier = new Supplier(supplier_ID, name, phone, address, email, price, deleted);
                supplierList.add(supplier);
            }
        } catch(Exception e){

        }
        return supplierList;
    }

    public Supplier readByPrimaryKey(String supplier_ID){
        try {
            for (Supplier supplier : getSuppliers()){
                if (supplier.getSupplier_ID().indexOf(supplier_ID) != -1){
                    return supplier;
                }
            }
        } catch(Exception e){
            return null;
        }
        return null;
    }

    public List<Supplier> readByName(String name){
        List<Supplier> result = new ArrayList<Supplier>();
        try {
            for (Supplier supplier : getSuppliers()){
                if (supplier.getName().indexOf(name) != -1){
                    result.add(supplier);
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public List<Supplier> readByPrice(String compare, double price){
        List<Supplier> result = new ArrayList<Supplier>();
        try {
            if (compare.indexOf("<") != -1){
                for (Supplier supplier : getSuppliers() ) {
                    if (supplier.getPrice() < price){
                        result.add(supplier);
                    }
                }
            } else if (compare.indexOf("<=") != -1) {
                for (Supplier supplier : getSuppliers() ) {
                    if (supplier.getPrice() <= price){
                        result.add(supplier);
                    }
                }
            } else if (compare.indexOf(">") != -1) {
                for (Supplier supplier : getSuppliers() ) {
                    if (supplier.getPrice() > price){
                        result.add(supplier);
                    }
                }
            } else if (compare.indexOf(">=") != -1) {
                for (Supplier supplier : getSuppliers() ) {
                    if (supplier.getPrice() >= price){
                        result.add(supplier);
                    }
                }
            } else if (compare.indexOf("=") != -1) {
                for (Supplier supplier : getSuppliers() ) {
                    if (supplier.getPrice() == price){
                        result.add(supplier);
                    }
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public List<Supplier> readByPrice(double price1, double price2){
        List<Supplier> result = new ArrayList<Supplier>();
        try {
            for (Supplier supplier : getSuppliers()){
                if (supplier.getPrice() >= price1 && supplier.getPrice() <= price2){
                    result.add(supplier);
                }
            }
        } catch(Exception e){
        }
        return result;
    }

    public List<Supplier> readByDeleted(boolean deleted){
        List<Supplier> result = new ArrayList<Supplier>();
        try {
            for (Supplier supplier : getSuppliers()){
                if (supplier.isDeleted() == deleted){
                    result.add(supplier);
                }
            }
        } catch(Exception e){
        }
        return result;
    }

    public int create(String supplier_ID, String name, String phone, String address, String email, double price){
        if (readByPrimaryKey(supplier_ID) != null){
            return 0;
        }
        try {
            return super.create(supplier_ID, name, phone, address, email, price, 0);
        } catch (Exception e){
            return 0;
        }
    }

    public int update(String supplier_ID, String name, String phone, String address, String email){
        // Những giá trị là _ID sẽ được combobox cho user chọn
        try {
            Map<String, Object> updateValues = new HashMap<>();
            if (name != null){
                updateValues.put("NAME", name);
            }
            if (address != null){
                updateValues.put("ADDRESS",address);
            }
            if (phone != null){
                updateValues.put("PHONE",phone);
            }
            if (email != null){
                updateValues.put("EMAIL",email);
            }
            return super.update(updateValues, "SUPPLIER_ID = '" + supplier_ID + "'");
        } catch (Exception e){
            return 0;
        }
    }

    public int update(String supplier_ID , double price){
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("PRICE", price);
            return super.update(updateValues, "SUPPLIER_ID = '" + supplier_ID + "'");
        } catch (Exception e){
            return 0;
        }
    }

    public int delete(String supplier_ID){
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("DELETED", 1);
            return super.update(updateValues, "SUPPLIER_ID = '" + supplier_ID + "'");
        } catch (Exception e){
            return 0;
        }
    }
}
