package com.cafe.BLL;

import com.cafe.DAL.SupplierDAL;
import com.cafe.DTO.Supplier;
import java.util.ArrayList;
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
            readSupplier();
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
        supplierList.add(supplier);
        return supplierDAL.insertSupplier(supplier) != 0;
    }

    public boolean updateSupplier(Supplier supplier) {
        supplierList.set(getIndex(supplier, "SUPPLIER_ID"), supplier);
        return supplierDAL.updateSupplier(supplier) != 0;
    }

    public boolean removeSupplier(String id) {
        List<Supplier> list = new ArrayList<>();
        for (Supplier supplier : supplierList){
            if (!supplier.getSupplierID().contains(id)){
                list.add(supplier);
            }
        }
        supplierList = list;
        return supplierDAL.deleteSupplier("SUPPLIER_ID = '" + id + "'") != 0;
    }

    public List<Supplier> searchSuppliers(String... conditions) {
        this.supplierList = supplierDAL.searchSuppliers(conditions);
        return this.supplierList;
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

    public void readSupplier(){
        List<Supplier> list = new ArrayList<>();
        for (Supplier supplier : supplierList){
            if (!supplier.isDeleted()){
                list.add(supplier);
            }
        }
        supplierList = list;
    }

    public List<Supplier> findSuppliers(String key, String value) {
        List<Supplier> list = new ArrayList<>();
        for (Supplier supplier : supplierList){
            if (getValueByKey(supplier, key).toString().toLowerCase().contains(value.toLowerCase())){
                list.add(supplier);
            }
        }
        return list;
    }

    public String getAutoID() {
        return supplierDAL.getAutoID();
    }

}
