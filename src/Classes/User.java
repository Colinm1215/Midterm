package Classes;

import Interfaces.AccountInterface;
import Interfaces.UserInterface;

public class User implements UserInterface {
    private final int id;
    private String login;
    private String pin;
    private String role;
    private AccountInterface account;

    public User(int id, String login, String pin, String role, AccountInterface account) {
        this.id = id;
        this.login = login;
        this.pin = pin;
        this.role = role;
        this.account = account;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getLogin() {
        return this.login;
    }

    @Override
    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String getPin() {
        return this.pin;
    }

    @Override
    public void setPin(String pin) {
        this.pin = pin;
    }

    @Override
    public void setAccountBalance(int balance) {
        this.account.setAccountBalance(balance);
    }

    @Override
    public void setAccountRole(String role) {
        this.role = role;
    }

    @Override
    public int getAccountBalance() {
        return this.account.getAccountBalance();
    }

    @Override
    public int getAccountRole() {
        return this.account.getAccountBalance();
    }

    @Override
    public String getRole() {
        return this.role;
    }

    @Override
    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String getAccountName() {
        return this.account.getAccountName();
    }

    @Override
    public void setAccountName(String accountName) {
        this.account.setAccountName(accountName);
    }

    @Override
    public String getAccountStatus() {
        return this.account.getAccountStatus();
    }

    @Override
    public void setAccountStatus(String accountStatus) {
        this.account.setAccountStatus(accountStatus);
    }
}
