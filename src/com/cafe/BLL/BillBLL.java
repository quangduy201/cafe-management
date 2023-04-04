package com.cafe.BLL;

import com.cafe.DAL.BillDAL;
import com.cafe.DTO.Bill;

import java.util.List;
import java.util.Map;

public class BillBLL extends Manager<Bill> {
    private BillDAL billDAL;
    private List<Bill> billList;

    public BillBLL() {
        try {
            billDAL = new BillDAL();
            billList = searchBills();
        } catch (Exception ignored) {

        }
    }

    public BillDAL getBillDAL() {
        return billDAL;
    }

    public void setBillDAL(BillDAL billDAL) {
        this.billDAL = billDAL;
    }

    public List<Bill> getBillList() {
        return billList;
    }

    public void setBillList(List<Bill> billList) {
        this.billList = billList;
    }

    public Object[][] getData() {
        return getData(billList);
    }

    public boolean addBill(Bill bill) {
        billList.add(bill);
        return billDAL.addBill(bill) != 0;
    }

    public boolean updateBill(Bill bill) {
        billList.set(getIndex(bill, "BILL_ID", billList), bill);
        return billDAL.updateBill(bill) != 0;
    }

    public boolean deleteBill(Bill bill) {
        billList.remove(getIndex(bill, "BILL_ID", billList));
        return billDAL.deleteBill("BILL_ID = '" + bill.getBillID() + "'") != 0;
    }

    public List<Bill> searchBills(String... conditions) {
        return billDAL.searchBills(conditions);
    }

    public List<Bill> findBillsBy(Map<String, Object> conditions) {
        List<Bill> bills = billList;
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            bills = findObjectsBy(entry.getKey(), entry.getValue(), bills);
        return bills;
    }

    public String getAutoID() {
        try {
            return getAutoID("BI", 4, searchBills());
        } catch (Exception e) {
            System.out.println("Error occurred in BillBLL.getAutoID(): " + e.getMessage());
        }
        return "";
    }

    @Override
    public Object getValueByKey(Bill bill, String key) {
        return switch (key) {
            case "BILL_ID" -> bill.getBillID();
            case "CUSTOMER_ID" -> bill.getCustomerID();
            case "STAFF_ID" -> bill.getStaffID();
            case "DOPURCHASE" -> bill.getDateOfPurchase();
            case "TOTAL" -> bill.getTotal();
            default -> null;
        };
    }
}
