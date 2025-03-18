package Interfaces;

import java.sql.SQLException;

public interface ATMInterface {
    // Startpoint
    void start();

    // Endpoint
    void end();

    // Menu Functions
    boolean loginMenu();
    void customerMenu() throws SQLException;
    void adminMenu() throws SQLException;

    // Validation Functions
    boolean isAdmin();
    boolean validateLogin(String username, String pin);

    // Customer Functionality
    String withdrawCash() throws SQLException;
    String depositCash() throws SQLException;
    String displayBalance() throws SQLException;

    // Admin Functionality
    String createAccount() throws SQLException;
    String deleteAccount() throws SQLException;
    String updateAccount() throws SQLException;
    String searchForAccount() throws SQLException;

    // Not in the requirements but added for my own ease of use
    String getInput(String prompt);
    void atmDisplay(String output);
}
