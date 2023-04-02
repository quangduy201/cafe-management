package com.cafe.BLL;

import com.cafe.DAL.AccountDAL;
import com.cafe.DTO.Account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountBLL extends Manager<Account> {
    private AccountDAL accountDAL;
    private List<Account> accountList;

    public AccountBLL() {
        try {
            accountDAL = new AccountDAL();
            accountList = searchAccounts();
        } catch (Exception ignored) {

        }
    }

    public AccountDAL getAccountDAL() {
        return accountDAL;
    }

    public void setAccountDAL(AccountDAL accountDAL) {
        this.accountDAL = accountDAL;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public Object[][] getData() {
        return getData(accountList);
    }

    public boolean addAccount(Account account) {
        if (getIndex(account, "USERNAME", accountList) != -1) {
            System.out.println("Can't add new account. Username already exists.");
            return false;
        }
        accountList.add(account);
        return accountDAL.addAccount(account) != 0;
    }

    public boolean updateAccount(Account account) {
        accountList.set(getIndex(account, "ACCOUNT_ID", accountList), account);
        return accountDAL.updateAccount(account) != 0;
    }

    public boolean deleteAccount(Account account) {
        account.setDeleted(true);
        accountList.set(getIndex(account, "ACCOUNT_ID", accountList), account);
        return accountDAL.deleteAccount("ACCOUNT_ID = '" + account.getAccountID() + "'") != 0;
    }

    public List<Account> searchAccounts(String... conditions) {
        return accountDAL.searchAccounts(conditions);
    }

    public List<Account> findAccountsBy(Map<String, Object> conditions) {
        List<Account> accounts = accountList;
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            accounts = findObjectsBy(entry.getKey(), entry.getValue(), accounts);
        return accounts;
    }

    public String getAutoID() {
        try {
            return getAutoID("AC", 3, accountList);
        } catch (Exception e) {
            System.out.println("Error occurred in AccountDAL.getAutoID(): " + e.getMessage());
        }
        return "";
    }

    @Override
    public Object getValueByKey(Account account, String key) {
        return switch (key) {
            case "ACCOUNT_ID" -> account.getAccountID();
            case "USERNAME" -> account.getUsername();
            case "PASSWD" -> account.getPassword();
            case "DECENTRALIZATION_ID" -> account.getDecentralizationID();
            case "STAFF_ID" -> account.getStaffID();
            default -> null;
        };
    }
}
