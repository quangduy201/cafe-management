package com.cafe.BLL;

import com.cafe.DAL.DecentralizationDetailDAL;
import com.cafe.DTO.DecentralizationDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DecentralizationDetailBLL extends Manager<DecentralizationDetail>{
    private DecentralizationDetailDAL decentralizationDetailDAL;
    private List<DecentralizationDetail> decentralizationDetailList;

    public DecentralizationDetailBLL() {
        decentralizationDetailDAL = new DecentralizationDetailDAL();
        decentralizationDetailList = searchDecentralizationDetail("DELETED = 0", "DECENTRALIZATION_ID != 'DE00'");
    }

    public DecentralizationDetailDAL getDecentralizationDetailDAL() {
        return decentralizationDetailDAL;
    }

    public void setDecentralizationDetailDAL(DecentralizationDetailDAL decentralizationDetailDAL) {
        this.decentralizationDetailDAL = decentralizationDetailDAL;
    }

    public boolean addDecentralizationDetail(DecentralizationDetail decentralizationDetail) {
        decentralizationDetailList.add(decentralizationDetail);
        return decentralizationDetailDAL.addDecentralizationDetail(decentralizationDetail) != 0;
    }

    public boolean updateDecentralizationDetail(DecentralizationDetail decentralizationDetail) {
        decentralizationDetailList.set(getIndex(decentralizationDetail, "DECENTRALIZATION_ID", decentralizationDetailList), decentralizationDetail);
        return decentralizationDetailDAL.updateDecentralizationDetail(decentralizationDetail) != 0;
    }

    public boolean deleteDecentralizationDetail(DecentralizationDetail decentralizationDetail) {
        decentralizationDetailList.remove(getIndex(decentralizationDetail, "DECENTRALIZATION_ID", decentralizationDetailList));
        return decentralizationDetailDAL.deleteDecentralizationDetail("DECENTRALIZATION_ID = '" + decentralizationDetail.getDecentralizationID() + "'") != 0;
    }

    public List<DecentralizationDetail> searchDecentralizationDetail(String... conditions) {
        return decentralizationDetailDAL.searchDecentralizationDetails(conditions);
    }

    public List<DecentralizationDetail> findDecentralizationDetails(String key, String value) {
        List<DecentralizationDetail> list = new ArrayList<>();
        for (DecentralizationDetail decentralizationDetail : decentralizationDetailList) {
            if (getValueByKey(decentralizationDetail, key).toString().toLowerCase().contains(value.toLowerCase())) {
                list.add(decentralizationDetail);
            }
        }
        return list;
    }

    public List<DecentralizationDetail> findDecentralizationDetailsBy(Map<String, Object> conditions) {
        List<DecentralizationDetail> decentralizationDetails = decentralizationDetailList;
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            decentralizationDetails = findObjectsBy(entry.getKey(), entry.getValue(), decentralizationDetails);
        return decentralizationDetails;
    }

    @Override
    public Object getValueByKey(DecentralizationDetail decentralizationDetail, String key) {
        return switch (key) {
            case "DECENTRALIZATION_ID" -> decentralizationDetail.getDecentralizationID();
            case "MODULE_ID" -> decentralizationDetail.getModuleID();
            case "IS_ADD" -> decentralizationDetail.isCanADD();
            case "IS_EDIT" -> decentralizationDetail.isCanEDIT();
            case "IS_REMOVE" -> decentralizationDetail.isCanREMOVE();
            default -> null;
        };
    }

}
