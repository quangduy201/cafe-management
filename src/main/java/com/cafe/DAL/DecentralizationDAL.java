package com.cafe.DAL;

import com.cafe.DTO.Decentralization;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DecentralizationDAL extends Manager {
    public DecentralizationDAL() throws SQLException {
        super("decentralization",
            List.of("DECENTRALIZATION_ID",
                "DECENTRALIZATION_NAME",
                "DELETED")
        );
    }

    public List<Decentralization> convertToDecentralization(List<List<String>> data) {
        return convert(data, row -> {
            row.set(row.size() - 1, row.get(row.size() - 1).equals("0") ? "false" : "true");
            return new Decentralization(
                row.get(0), // decentralizationID
                row.get(1), // decentralizationName
                Boolean.parseBoolean(row.get(2)) // deleted
            );
        });
    }

    public int addDecentralization(Decentralization decentralization) {
        try {
            return create(decentralization.getDecentralizationID(),
                decentralization.getDecentralizationName(),
                false
            ); // decentralization khi tạo mặc định deleted = 0
        } catch (Exception e) {
            System.out.println("Error occurred in DecentralizationDAL.addDecentralization(): " + e.getMessage());
        }
        return 0;
    }

    public int updateDecentralization(Decentralization decentralization) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(decentralization.getDecentralizationID());;
            updateValues.add(decentralization.getDecentralizationName());
            updateValues.add(decentralization.isDeleted());
            return update(updateValues, "DECENTRALIZATION_ID = '" + decentralization.getDecentralizationID() + "'");
        } catch (Exception e) {
            System.out.println("Error occurred in DecentralizationDAL.updateDecentralization(): " + e.getMessage());
        }
        return 0;
    }

    public int deleteDecentralization(String... conditions) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(true);
            return update(updateValues, conditions);
        } catch (Exception e) {
            System.out.println("Error occurred in DecentralizationDAL.deleteDecentralization(): " + e.getMessage());
        }
        return 0;
    }

    public List<Decentralization> searchDecentralizations(String... conditions) {
        try {
            return convertToDecentralization(read(conditions));
        } catch (Exception e) {
            System.out.println("Error occurred in DecentralizationDAL.searchDecentralizations(): " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
