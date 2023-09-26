package com.cafe.BLL;

import com.cafe.DAL.DecentralizationDAL;
import com.cafe.DTO.Decentralization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DecentralizationBLL extends Manager<Decentralization> {
    private DecentralizationDAL decentralizationDAL;
    private List<Decentralization> decentralizationList;

    public DecentralizationBLL() {
        decentralizationDAL = new DecentralizationDAL();
        decentralizationList = searchDecentralization("DELETED = 0", "DECENTRALIZATION_ID != 'DE00'");
    }

    public DecentralizationDAL getDecentralizationDAL() {
        return decentralizationDAL;
    }

    public void setDecentralizationDAL(DecentralizationDAL decentralizationDAL) {
        this.decentralizationDAL = decentralizationDAL;
    }

    public List<Decentralization> getDecentralizationList() {
        return decentralizationList;
    }

    public void setDecentralizationList(List<Decentralization> decentralizationList) {
        this.decentralizationList = decentralizationList;
    }

    public Object[][] getData() {
        return getData(decentralizationList);
    }

    public boolean addDecentralization(Decentralization decentralization) {
        decentralizationList.add(decentralization);
        return decentralizationDAL.addDecentralization(decentralization) != 0;
    }

    public boolean updateDecentralization(Decentralization decentralization) {
        decentralizationList.set(getIndex(decentralization, "DECENTRALIZATION_ID", decentralizationList), decentralization);
        return decentralizationDAL.updateDecentralization(decentralization) != 0;
    }

    public boolean deleteDecentralization(Decentralization decentralization) {
        decentralizationList.remove(getIndex(decentralization, "DECENTRALIZATION_ID", decentralizationList));
        return decentralizationDAL.deleteDecentralization("DECENTRALIZATION_ID = '" + decentralization.getDecentralizationID() + "'") != 0;
    }

    public List<Decentralization> searchDecentralization(String... conditions) {
        return decentralizationDAL.searchDecentralizations(conditions);
    }

    public List<Decentralization> findDecentralizations(String key, String value) {
        List<Decentralization> list = new ArrayList<>();
        for (Decentralization decentralization : decentralizationList) {
            if (getValueByKey(decentralization, key).toString().toLowerCase().contains(value.toLowerCase())) {
                list.add(decentralization);
            }
        }
        return list;
    }

    public List<Decentralization> findDecentralizationsBy(Map<String, Object> conditions) {
        List<Decentralization> decentralizations = decentralizationList;
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            decentralizations = findObjectsBy(entry.getKey(), entry.getValue(), decentralizations);
        return decentralizations;
    }

    public boolean exists(Decentralization decentralization) {
        Map<String, Object> conditions = new HashMap<>(Map.of(
            "DECENTRALIZATION_NAME", decentralization.getDecentralizationName()
        ));
        return !findDecentralizationsBy(conditions).isEmpty();
    }

    public boolean exists(Map<String, Object> conditions) {
        if (conditions.containsKey("DECENTRALIZATION_NAME") && conditions.get("DECENTRALIZATION_NAME").equals("admin")) {
            return true;
        }
        return !findDecentralizationsBy(conditions).isEmpty();
    }

    public String getAutoID() {
        return getAutoID("DE", 2, searchDecentralization());
    }

    @Override
    public Object getValueByKey(Decentralization decentralization, String key) {
        return switch (key) {
            case "DECENTRALIZATION_ID" -> decentralization.getDecentralizationID();
            case "DECENTRALIZATION_NAME" -> decentralization.getDecentralizationName();
            default -> null;
        };
    }
}
