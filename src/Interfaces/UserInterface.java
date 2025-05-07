package Interfaces;

public interface UserInterface {

    int getId();

    String getLogin();

    void setLogin(String login);

    String getPin();

    void setPin(String pin);

    void setAccountBalance(int balance);

    int getAccountBalance();

    String getRole();

    void setRole(String role);

    String getAccountName();

    void setAccountName(String accountName);

    String getAccountStatus();

    void setAccountStatus(String accountStatus);

    int getAccountID();
}
