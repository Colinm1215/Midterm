package Interfaces;

public interface ATMInterface {
    // Startpoint
    void start();

    // Endpoint
    void end();

    // Menu Functions
    boolean loginMenu();
    void customerMenu();
    void adminMenu();

    // Validation Functions
    boolean isAdmin();
    boolean validateLogin(String username, String pin);

    // Customer Functionality
    String withdrawCash();
    String depositCash();
    String displayBalance();

    // Admin Functionality
    String createAccount();
    String deleteAccount();
    String updateAccount();
    String searchForAccount();

    // Not in the requirements but added for my own ease of use
    String listAccounts();
    String getInput(String prompt);
    void atmDisplay(String output);
}
