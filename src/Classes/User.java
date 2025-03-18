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
        return 0;
    }

    @Override
    public String getLogin() {
        return "";
    }

    @Override
    public void setLogin(String login) {

    }

    @Override
    public String getPin() {
        return "";
    }

    @Override
    public void setPin(String pin) {

    }

    @Override
    public void setAccountBalance(int balance) {

    }

    @Override
    public void setAccountRole(String role) {

    }

    @Override
    public int getAccountBalance() {
        return 0;
    }

    @Override
    public int getAccountRole() {
        return 0;
    }

    @Override
    public String getRole() {
        return "";
    }

    @Override
    public void setRole(String role) {

    }

    @Override
    public String getAccountName() {
        return "";
    }

    @Override
    public void setAccountName(String accountName) {

    }

    @Override
    public String getAccountStatus() {
        return "";
    }

    @Override
    public void setAccountStatus(String accountStatus) {

    }
}
