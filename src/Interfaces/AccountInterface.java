package Interfaces;

public interface AccountInterface {

    void setAccountBalance(int balance);

    void setAccountStatus(String role);

    int getAccountBalance();

    String getAccountStatus();

    int getAccountId();

    String getAccountName();

    void setAccountName(String accountName);
}
