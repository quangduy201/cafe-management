package com.cafe.BLL;

import com.cafe.DAL.BillDetailsDAL;
import com.cafe.DTO.BillDetails;

import java.util.List;

public class BillDetailsBLL {
    private BillDetailsDAL billDetailsDAL;

    public BillDetailsBLL() {
        try {
            billDetailsDAL = new BillDetailsDAL();
        } catch (Exception ignored) {

        }
    }

    public boolean insertBillDetails(BillDetails billDetails) {
        return billDetailsDAL.insertBillDetails(billDetails) != 0;
    }

    public List<BillDetails> searchBillDetails(String... conditions) {
        return billDetailsDAL.searchBillDetails(conditions);
    }
}
