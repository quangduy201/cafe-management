package com.cafe.DAL;

import com.cafe.DTO.DecentralizationDetail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DecentralizationDetailDAL extends Manager {
    public DecentralizationDetailDAL() throws SQLException {
        super("decentralization_detail",
            List.of("DECENTRALIZATION_ID",
                "MODULE_ID",
                "CAN_ADD",
                "CAN_EDIT",
                "CAN_REMOVE",
                "DELETED")
        );
    }

    public List<DecentralizationDetail> convertToDecentralizationDetail(List<List<String>> data) {
        return convert(data, row -> {
            row.set(2, row.get(2).equals("0") ? "false" : "true");
            row.set(3, row.get(3).equals("0") ? "false" : "true");
            row.set(4, row.get(4).equals("0") ? "false" : "true");
            row.set(row.size() - 1, row.get(row.size() - 1).equals("0") ? "false" : "true");
            return new DecentralizationDetail(
                row.get(0), // decentralizationID
                row.get(1), // decentralizationName
                Boolean.parseBoolean(row.get(2)), // canADD
                Boolean.parseBoolean(row.get(3)), // canEDIT
                Boolean.parseBoolean(row.get(4)), // canREMOVE
                Boolean.parseBoolean(row.get(5)) // deleted
            );
        });
    }

    public int addDecentralizationDetail(DecentralizationDetail DecentralizationDetail) {
        try {
            return create(DecentralizationDetail.getDecentralizationID(),
                DecentralizationDetail.getModuleID(),
                DecentralizationDetail.isCanADD(),
                DecentralizationDetail.isCanEDIT(),
                DecentralizationDetail.isCanREMOVE(),
                false
            ); // decentralization khi tạo mặc định deleted = 0
        } catch (Exception e) {
            System.out.println("Error occurred in DecentralizationDetailDAL.addDecentralizationDetail(): " + e.getMessage());
        }
        return 0;
    }

    public int updateDecentralizationDetail(DecentralizationDetail decentralizationDetail) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(decentralizationDetail.getDecentralizationID());
            updateValues.add(decentralizationDetail.getModuleID());
            updateValues.add(decentralizationDetail.isCanADD());
            updateValues.add(decentralizationDetail.isCanEDIT());
            updateValues.add(decentralizationDetail.isCanREMOVE());
            updateValues.add(decentralizationDetail.isDeleted());
            return update(updateValues, "DECENTRALIZATION_ID = '" + decentralizationDetail.getDecentralizationID() + "'", "MODULE_ID = '" + decentralizationDetail.getModuleID() + "'");
        } catch (Exception e) {
            System.out.println("Error occurred in DecentralizationDetailDAL.updateDecentralizationDetail(): " + e.getMessage());
        }
        return 0;
    }

    public int deleteDecentralizationDetail(String... conditions) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(true);
            return update(updateValues, conditions);
        } catch (Exception e) {
            System.out.println("Error occurred in DecentralizationDetailDAL.deleteDecentralizationDetails(): " + e.getMessage());
        }
        return 0;
    }

    public List<DecentralizationDetail> searchDecentralizationDetails(String... conditions) {
        try {
            return convertToDecentralizationDetail(read(conditions));
        } catch (Exception e) {
            System.out.println("Error occurred in DecentralizationDetailDAL.searchDecentralizationDetails(): " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
