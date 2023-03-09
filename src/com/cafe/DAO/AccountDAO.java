package com.cafe.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountDAO extends Manager {
    public AccountDAO() throws SQLException {
        super("account", new ArrayList<>(
            List.of("USERNAME",
                    "PASSWD",
                    "DECENTRALIZATION_ID",
                    "STAFF_ID",
                    "DELETED")
        ));
    }

    public static void main(String[] args) {
        try {
            AccountDAO accountDAO = new AccountDAO();
            accountDAO.create("abc", "123", "001", "NV001", 0);
            accountDAO.create("xyz", "123", "001", "NV001", 0);
            Map<String, Object> updateValues = new HashMap<>();
            List<List<String>> accounts = accountDAO.read("PASSWD = '123'", "STAFF_ID = 'NV001'");
            updateValues.put("PASSWD", "12345");
            updateValues.put("DELETED", 1);
            accountDAO.update(updateValues, "USERNAME = 'abc'");
            accountDAO.delete("USERNAME = 'xyz'");
            for (List<String> row : accounts) {
                for (String value : row)
                    System.out.print(value + " ");
                System.out.println();
            }
//            accountDAO.delete("USERNAME = 'abc'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
