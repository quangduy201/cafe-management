package com.cafe.BLL;

import com.cafe.DAL.SupplierDAL;
import com.cafe.DTO.Supplier;

import java.util.List;

public class SupplierBLL {
    private SupplierDAL supplierDAL;

    public SupplierBLL() {
        try {
            supplierDAL = new SupplierDAL();
        } catch (Exception ignored) {

        }
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
        return supplierDAL.deleteSupplier(id) != 0;
    }

    public List<Supplier> searchSuppliers(String... conditions) {
        return supplierDAL.searchSuppliers(conditions);
    }

    public String getAutoID() {
        return supplierDAL.getAutoID();
    }
}
