package com.cafe.BLL;

import com.cafe.DAL.BillDAL;
import com.cafe.DTO.Bill;

import java.util.List;
import java.util.Map;

public class BillBLL {
    private BillDAL billDAL;

    public BillBLL() {
        try {
            billDAL = new BillDAL();
        } catch (Exception ignored) {

        }
    }

    public boolean insertBill(Bill bill) {
        return billDAL.insertBill(bill) != 0;
    }

    public boolean updateBill(Map<String, Object> updateValues, String... conditions) {
        return billDAL.updateBill(updateValues, conditions) != 0;
    }

    public boolean removeBill(String id) {
        return billDAL.deleteBill(id) != 0;
    }

    public List<Bill> searchBills(String... conditions) {
        return billDAL.searchBills(conditions);
    }

    public String getAutoID() {
        return billDAL.getAutoID();
    }
}
