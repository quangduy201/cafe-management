package com.cafe.BLL;

import com.cafe.DAL.SupplierDAL;
import com.cafe.DTO.Supplier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SupplierBLL extends Manager<Supplier> {
    private SupplierDAL supplierDAL;
    private List<Supplier> supplierList;

    public SupplierBLL() {
        try {
            supplierDAL = new SupplierDAL();
            supplierList = searchSuppliers("DELETED = 0");
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

    public Object[][] getData() {
        return getData(supplierList);
    }

    public boolean addSupplier(Supplier supplier) {
        supplierList.add(supplier);
        return supplierDAL.addSupplier(supplier) != 0;
    }

    public boolean updateSupplier(Supplier supplier) {
        supplierList.set(getIndex(supplier, "SUPPLIER_ID", supplierList), supplier);
        return supplierDAL.updateSupplier(supplier) != 0;
    }

    public boolean deleteSupplier(Supplier supplier) {
        supplierList.remove(getIndex(supplier, "SUPPLIER_ID", supplierList));
        return supplierDAL.deleteSupplier("SUPPLIER_ID = '" + supplier.getSupplierID() + "'") != 0;
    }

    public List<Supplier> searchSuppliers(String... conditions) {
        return supplierDAL.searchSuppliers(conditions);
    }

    public List<Supplier> findSuppliers(String key, Object value) {
        List<Supplier> list = new ArrayList<>();
        for (Supplier supplier : supplierList) {
            if (getValueByKey(supplier, key).toString().toLowerCase().contains(value.toString().toLowerCase())) {
                list.add(supplier);
            }
        }
        return list;
    }

    public List<Supplier> findSuppliersBy(Map<String, Object> conditions) {
        List<Supplier> suppliers = supplierList;
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            suppliers = findObjectsBy(entry.getKey(), entry.getValue(), suppliers);
        return suppliers;
    }

    public boolean exists(Supplier supplier) {
        return !findSuppliersBy(Map.of(
            "NAME", supplier.getName(),
            "PHONE", supplier.getPhone(),
            "ADDRESS", supplier.getAddress(),
            "EMAIL", supplier.getEmail()
        )).isEmpty();
    }

    public boolean exists(Map<String, Object> conditions) {
        return !findSuppliersBy(conditions).isEmpty();
    }

    public String getAutoID() {
        return getAutoID("SUP", 3, searchSuppliers());
    }

    @Override
    public Object getValueByKey(Supplier supplier, String key) {
        return switch (key) {
            case "SUPPLIER_ID" -> supplier.getSupplierID();
            case "NAME" -> supplier.getName();
            case "PHONE" -> supplier.getPhone();
            case "ADDRESS" -> supplier.getAddress();
            case "EMAIL" -> supplier.getEmail();
            default -> null;
        };
    }
}
