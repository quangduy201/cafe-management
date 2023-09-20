package com.cafe.DAL;

import com.cafe.DTO.Account;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAL extends Manager {
    public AccountDAL() {
        super("account",
            List.of("ACCOUNT_ID",
                "USERNAME",
                "PASSWD",
                "DECENTRALIZATION_ID",
                "STAFF_ID",
                "DELETED")
        );
    }

    public List<Account> convertToAccounts(List<List<String>> data) {
        return convert(data, row -> {
            row.set(row.size() - 1, row.get(row.size() - 1).equals("0") ? "false" : "true");
            return new Account(
                row.get(0), // accountID
                row.get(1), // username
                row.get(2), // password
                row.get(3), // decentralizationID
                row.get(4), // staffID
                Boolean.parseBoolean(row.get(5)) // deleted
            );
        });
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

    @Override
    public int create(Object... values) throws SQLException {
        String query = String.format("""
            INSERT INTO `%s` (`ACCOUNT_ID`, `USERNAME`, `PASSWD`, `DECENTRALIZATION_ID`, `STAFF_ID`, `DELETED`)
            VALUES ('%s', '%s', '%s', '%s', '%s', b'0');""",
            getTableName(), values[0], values[1], values[2], values[3], values[4]);
        return executeUpdate(query);
    }

    public int updateAccount(Account account) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(account.getAccountID());
            updateValues.add(account.getUsername());
            updateValues.add(account.getDecentralizationID());
            updateValues.add(account.getStaffID());
            updateValues.add(account.isDeleted());
            return update(updateValues, "ACCOUNT_ID = '" + account.getAccountID() + "'");
        } catch (Exception e) {
            System.out.println("Error occurred in AccountDAL.updateAccount(): " + e.getMessage());
        }
        return 0;
    }

    @Override
    public int update(List<Object> updateValues, String... conditions) throws SQLException {
        String query = String.format("""
            UPDATE `%s` SET ACCOUNT_ID = '%s', USERNAME = '%s', DECENTRALIZATION_ID = '%s', STAFF_ID = '%s', DELETED = %d
            WHERE ACCOUNT_ID = '%s';""",
            getTableName(), updateValues.get(0), updateValues.get(1), updateValues.get(2), updateValues.get(3), (boolean) updateValues.get(4) ? 1 : 0, updateValues.get(0));
        return executeUpdate(query);
    }

    public int updateAccountPassword(Account account) {
        try {
            String query = "UPDATE `" + getTableName() + "` SET PASSWD = '" + account.getPassword() + "' WHERE ACCOUNT_ID = '" + account.getAccountID() + "';";
            return executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Error occurred in AccountDAL.updateAccountPassword(): " + e.getMessage());
        }
        return 0;
    }

    public int deleteAccount(String... conditions) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(true);
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
