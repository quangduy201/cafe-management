package com.cafe.BLL;

import com.cafe.DAL.SupplierDAL;
import com.cafe.DTO.Supplier;
import com.cafe.DTO.Supplier;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class SupplierBLL {
    private SupplierDAL supplierDAL;
    private List<Supplier> supplierList;
    public SupplierBLL() {
        try {
            supplierDAL = new SupplierDAL();
            supplierList = searchSuppliers();
        } catch (Exception ignored) {

        }
    }

    public SupplierDAL getSupplierDAL() {
        return supplierDAL;
    }

    public void setSupplierDAL(SupplierDAL supplierDAL) {
        this.supplierDAL = supplierDAL;
    }

    public List<Supplier> getSupplierList() {
        return supplierList;
    }

    public void setSupplierList(List<Supplier> supplierList) {
        this.supplierList = supplierList;
    }

    public boolean insertSupplier(Supplier supplier) {
        if (searchSuppliers("PHONE = '" + supplier.getPhone() + "'").size() != 0) {
            System.out.println("Can't insert new supplier. Phone already exists.");
            return false;
        }
        return supplierDAL.insertSupplier(supplier) != 0;
    }

    public boolean updateSupplier(Supplier supplier) {
        return supplierDAL.updateSupplier(supplier) != 0;
    }

    public boolean removeSupplier(String id) {
        return supplierDAL.deleteSupplier("SUPPLIER_ID = '" + id + "'") != 0;
    }

    public List<Supplier> searchSuppliers(String... conditions) {
        return supplierDAL.searchSuppliers(conditions);
    }

    public Object[][] getData (){
        Object [][] data = new Object[supplierList.size()][];
        for (int i=0; i<data.length; i++){
            data[i] = supplierList.get(i).toString().split(" \\| ");
        }
        return data;
    }

    public int getIndex(Supplier supplier, String key) {
        return IntStream.range(0, supplierList.size())
            .filter(i -> Objects.equals(getValueByKey(supplierList.get(i), key), getValueByKey(supplier, key)))
            .findFirst()
            .orElse(-1);
    }

    public Object getValueByKey(Supplier supplier, String key) {
        return switch (key) {
            case "SUPPLIER_ID" -> supplier.getSupplierID();
            case "NAME" -> supplier.getName();
            case "PHONE" -> supplier.getPhone();
            case "ADDRESS" -> supplier.getAddress();
            case "EMAIL" -> supplier.getEmail();
            case "PRICE" -> supplier.getPrice();
            default -> null;
        };
    }

    public String getAutoID() {
        return supplierDAL.getAutoID();
    }
}
