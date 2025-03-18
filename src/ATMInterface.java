public interface ATMInterface {
    // Startpoint
    void start();

    // Menu Functions
    boolean loginMenu();
    void customerMenu();
    void adminMenu();

    // Validation Functions
    boolean isAdmin();
    boolean validateLogin(String username, int pin);

    // Customer Functionality
    String withdrawCash(int amount);
    String depositCash(int amount);
    String displayBalance();

    // Admin Functionality
    String createAccount(String login, int pin, String name, int balance, String status);
    String deleteAccount();
    String updateAccount();
    String searchForAccount();

    // Not in the requirements but added for my own ease of use
    String listAccounts();

}
