package com.cafe.DAL;

import com.cafe.DTO.Account;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAL extends Manager {
    public AccountDAL() throws SQLException {
        super("account", new ArrayList<>(
            List.of("ACCOUNT_ID",
                "USERNAME",
                "PASSWD",
                "DECENTRALIZATION_ID",
                "STAFF_ID",
                "DELETED")
        ));
    }

    public List<Account> convertToAccounts(List<List<String>> data) {
        return convert(data, row -> new Account(
            row.get(0), // accountID
            row.get(1), // username
            row.get(2), // password
            row.get(3), // decentralizationID
            row.get(4), // staffID
            Boolean.parseBoolean(row.get(5)) // deleted
        ));
    }

    public int addAccount(Account account) {
        try {
            return create(account.getAccountID(),
                account.getUsername(),
                account.getPassword(),
                account.getDecentralizationID(),
                account.getStaffID(),
                false
            ); // account khi tạo mặc định deleted = 0
        } catch (Exception e) {
            System.out.println("Error occurred in AccountDAL.addAccount(): " + e.getMessage());
        }
        return 0;
    }

    public int updateAccount(Account account) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(account.getAccountID());
            updateValues.add(account.getUsername());
            updateValues.add(account.getPassword());
            updateValues.add(account.getDecentralizationID());
            updateValues.add(account.getStaffID());
            updateValues.add(account.isDeleted());
            return update(updateValues, "ACCOUNT_ID = " + account.getAccountID());
        } catch (Exception e) {
            System.out.println("Error occurred in AccountDAL.updateAccount(): " + e.getMessage());
        }
        return 0;
    }

    public int deleteAccount(String... conditions) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(1);
            return update(updateValues, conditions);
        } catch (Exception e) {
            System.out.println("Error occurred in AccountDAL.deleteAccount(): " + e.getMessage());
        }
        return 0;
    }

    public List<Account> searchAccounts(String... conditions) {
        try {
            return convertToAccounts(read(conditions));
        } catch (Exception e) {
            System.out.println("Error occurred in AccountDAL.searchAccounts(): " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
