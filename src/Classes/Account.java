package Classes;

import Interfaces.AccountInterface;

public class Account implements AccountInterface {
    private int id;
    private int balance;
    private String status;
    private String name;

    public Account(int id, int balance, String status, String name) {
        this.id = id;
        this.balance = balance;
        this.status = status;
        this.name = name;
    }


    @Override
    public void setAccountBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public void setAccountStatus(String status) {
        this.status = status;
    }

    @Override
    public int getAccountBalance() {
        return this.balance;
    }

    @Override
    public String getAccountStatus() {
        return this.status;
    }

    @Override
    public int getAccountId() {
        return this.id;
    }

    @Override
    public String getAccountName() {
        return "";
    }

    @Override
    public void setAccountName(String accountName) {

    }
}
