package com.cafe.BLL;

import com.cafe.DAL.DecentralizationDAL;
import com.cafe.DTO.Decentralization;

import java.util.List;

public class DecentralizationBLL {
    private DecentralizationDAL decentralizationDAL;

    public DecentralizationBLL() {
        try {
            decentralizationDAL = new DecentralizationDAL();
        } catch (Exception ignored) {

        }
    }

    public boolean insertDecentralization(Decentralization decentralization) {
        return decentralizationDAL.insertDecentralization(decentralization) != 0;
    }

    public boolean updateDecentralization(Decentralization decentralization) {
        return decentralizationDAL.updateDecentralization(decentralization) != 0;
    }

    public List<Decentralization> searchDecentralization(String... conditions) {
        return decentralizationDAL.searchDecentralization(conditions);
    }

    public String getAutoID() {
        return decentralizationDAL.getAutoID();
    }
}
