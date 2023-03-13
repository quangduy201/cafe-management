package com.cafe.DAO;

import com.cafe.DTO.Account;

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

    public List<Account> convertToAccount(List<List<String>> data){
        List<Account> accountList = new ArrayList<>();
        try {
            String userName, passWord, decentralization_ID, staff_ID;
            boolean deleted;
            for (List<String> row : data) {
                userName = row.get(0);
                passWord = row.get(1);
                decentralization_ID = row.get(2);
                staff_ID = row.get(3);
                deleted = !row.get(4).contains("0");
                Account account = new Account(userName, passWord, decentralization_ID, staff_ID, deleted);
                accountList.add(account);
            }
        } catch (Exception ignored){

        }
        return accountList;
    }

    public List<Account> readAccounts(String[] conditions){
        List<Account> accountList = new ArrayList<>();
        try {
            accountList = convertToAccount(read(conditions));
        } catch (Exception ignored){

        }
        return accountList;
    }

    public int createAccount(String userName, String passWord, String decentralization_ID, String staff_ID){
        if (readAccounts(new String[]{"USERNAME = '" + userName + "'"}).size() != 0){
            return 0;
        }
        try {
            return super.create(userName, passWord, decentralization_ID, staff_ID, 0); // tài khoản khi tạo mặc định deleted = 0
        } catch (Exception ignored) {
            return 0;
        }
    }

    public int updateAccount(String userName, String passWord, String decentralization_ID, String staff_ID){
        // Những giá trị là _ID sẽ được combobox cho user chọn
        try {
            Map<String, Object> updateValues = new HashMap<>();
            if (passWord != null){
                updateValues.put("PASSWD", passWord);
            }
            if (decentralization_ID != null){
                updateValues.put("DECENTRALIZATION_ID", decentralization_ID);
            }
            if (staff_ID != null){
                updateValues.put("STAFF_ID",staff_ID);
            }
            return super.update(updateValues, "USERNAME = '" + userName + "'");
        } catch (Exception e){
            return 0;
        }
    }
    public int deleteAcount(String userName){
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("DELETED", 1);
            return super.update(updateValues, "USERNAME = '" + userName + "'");
        } catch (Exception e){
            return 0;
        }
    }
    public static void main(String[] args) {
        try {
            AccountDAO accountDAO = new AccountDAO();
            accountDAO.create("abc", "123", "000", "NV001");
            accountDAO.create("xyz", "123", "001", "NV001");
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
            accountDAO.delete("USERNAME = 'abc'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
