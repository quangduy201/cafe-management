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

    public List<Account> getAccounts(){
        List<Account> accountList = new ArrayList<Account>();
        try {
            AccountDAO accountDAO = new AccountDAO();
            String userName, passWord, decentralization_ID, staff_ID;
            boolean deleted;
            List<List<String>> accounts = accountDAO.read();
            for (List<String> row : accounts) {
                userName = row.get(0);
                passWord = row.get(1);
                decentralization_ID = row.get(2);
                staff_ID = row.get(3);
                if (row.get(4).indexOf("0") != -1){
                    deleted = false;
                }else {
                    deleted = true;
                }
                Account account = new Account(userName, passWord, decentralization_ID, staff_ID, deleted);
                accountList.add(account);
            }
        } catch (Exception e){

        }
        return accountList;
    }

    public Account readByPrimayKey (String userName){
        try {
            for (Account account: getAccounts()) {
                if (account.getUserName().indexOf(userName) != -1){
                    return account;
                }
            }
            return null;
        } catch (Exception e){
            return null;
        }
    }

    public List<Account> readByDecentralization_ID (String decentralization_ID){
        List<Account> result = new ArrayList<Account>();
        try {
            for (Account account: getAccounts()) {
                if (account.getDecentralization_ID().indexOf(decentralization_ID) != -1){
                    result.add(account);
                }
            }
        } catch (Exception e){

        }
        return result;

    }

    public List<Account> readByStaff_ID (String staff_ID){
        List<Account> result = new ArrayList<Account>();
        try {
            for (Account account: getAccounts()) {
                if (account.getDecentralization_ID().indexOf(staff_ID) != -1){
                    result.add(account);
                }
            }
        } catch (Exception e){

        }
        return result;
    }
    public List<Account> readByDeleted (boolean deleted){
        List<Account> result = new ArrayList<Account>();
        try {
            for (Account account: getAccounts()) {
                if (account.isDeleted() == deleted){
                    result.add(account);
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public int create(String userName, String passWord, String decentralization_ID, String staff_ID){
        if (readByPrimayKey(userName) != null){
            return 0;
        }
        try {
            return super.create(userName, passWord, decentralization_ID, staff_ID, 0); // tài khoản khi tạo mặc định deleted = 0
        } catch (Exception ignored) {
            return 0;
        }
    }

    public int update(String userName, String passWord, String decentralization_ID, String staff_ID){
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
    public int delete(String userName){
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
