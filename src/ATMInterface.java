public interface ATMInterface {
    // Menu Functions
    void loginMenu();
    void customerMenu();
    void adminMenu();
    void updateAccountMenu();

    // Validation Functions
    boolean isAdmin();
    boolean validateLogin(String username, int pin);

    // Customer Functionality
    String withdrawCash(int amount);
    String depositCash(int amount);
    String displayBalance();

    // Admin Functionality
    String createAccount(String login, int pin, String name, int balance, String status);
    String depositAccount(int number);
    String updateAccount(int number);
    String searchForAccount(int number);

    // Not in the requirements but added for my own ease of use
    String searchForAccount(String login);
    String listAccounts();

}
