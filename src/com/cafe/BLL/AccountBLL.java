package com.cafe.BLL;

import com.cafe.DAL.AccountDAL;
import com.cafe.DTO.Account;

import java.util.List;
import java.util.Map;

public class AccountBLL {
    private AccountDAL accountDAL;

    public AccountBLL() {
        try {
            accountDAL = new AccountDAL();
        } catch (Exception ignored) {

        }
    }

    public boolean insertAccount(Account account) {
        if (searchAccounts("USERNAME = '" + account.getUsername() + "'").size() != 0) {
            System.out.println("Can't insert new account. Username already exists.");
            return false;
        }
        return accountDAL.insertAccount(account) != 0;
    }

    public boolean updateAccount(Map<String, Object> updateValues, String... conditions) {
        return accountDAL.updateAccount(updateValues, conditions) != 0;
    }

    public boolean removeAccount(String id) {
        return accountDAL.deleteAccount(id) != 0;
    }

    public List<Account> searchAccounts(String... conditions) {
        return accountDAL.searchAccounts(conditions);
    }

    public String getAutoID() {
        return accountDAL.getAutoID();
    }
}
