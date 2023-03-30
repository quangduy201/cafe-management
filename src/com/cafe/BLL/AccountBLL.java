package com.cafe.BLL;

import com.cafe.DAL.AccountDAL;
import com.cafe.DTO.Account;

import java.util.List;

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

    public boolean updateAccount(Account account) {
        return accountDAL.updateAccount(account) != 0;
    }

    public boolean removeAccount(String id) {
        return accountDAL.deleteAccount("ACCOUNT_ID = '" + id + "'" ) != 0;
    }

    public List<Account> searchAccounts(String... conditions) {
        return accountDAL.searchAccounts(conditions);
    }

    public String getAutoID() {
        return accountDAL.getAutoID();
    }
}
