package Classes;

import Interfaces.ATMDatabaseInterface;
import Interfaces.ATMInterface;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ATM implements ATMInterface {
    private boolean admin = false;
    private int user_id;
    private static final Scanner scanner = new Scanner(System.in);
    private ATMDatabaseInterface database;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    @Override
    public void start() {
        try {
            this.database = new ATMDatabase(new ATMConfig());
            if (this.loginMenu()) {
                if (this.admin) {
                    this.adminMenu();
                } else {
                    this.customerMenu();
                }
            }
            this.end();
        } catch (Exception e) {
            atmDisplay(e.getMessage());
            this.end();
        }
    }

    @Override
    public void end() {
        atmDisplay("Thank you for using the ATM!");
        System.exit(0);
    }

    @Override
    public String listAccounts() {
        return "";
    }

    @Override
    public String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    @Override
    public void atmDisplay(String output) {
        System.out.println(output);
    }

    @Override
    public String searchForAccount() {
        return "";
    }

    @Override
    public String updateAccount() {
        return "";
    }

    @Override
    public String createAccount() {
         // String login, int pin, String name, int balance, String status
        return "";
    }

    @Override
    public String deleteAccount() {
        return "";
    }

    @Override
    public boolean loginMenu() {
        boolean loop = true;
        boolean success = false;
        int c = 0;
        while (loop) {
            if (c == 2) {
                atmDisplay("Login Failed Too Many Times!");
                loop = false;
                break;
            }
            String username = getInput("Enter login: ");
            String pin = getInput("Enter pin: ");
            success = validateLogin(username, pin);
            if (success) {
                loop = false;
                break;
            }
            atmDisplay("Login Failed! Please try again.");
            c++;
        }
        return success;
    }

    @Override
    public void customerMenu() throws SQLException {
        boolean loop = true;
        String menu = """
                ----------------- Customer Menu -----------------\n
                1 - Withdraw Cash\n
                2 - Deposit Cash\n
                3 - Display Balance\n
                4 - Exit\n
                -------------------------------------------------
                """;
        while (loop) {
            atmDisplay(menu);
            String input = getInput("Enter your choice: ");
            switch (input) {
                case "1":
                    atmDisplay(withdrawCash());
                    getInput("Press enter to continue...");
                    atmDisplay("\n\n");
                    break;
                case "2":
                    atmDisplay(depositCash());
                    getInput("Press enter to continue...");
                    atmDisplay("\n\n");
                    break;
                case "3":
                    atmDisplay(displayBalance());
                    getInput("Press enter to continue...");
                    atmDisplay("\n\n");
                    break;
                case "4":
                    loop = false;
                    break;
            }
        }
    }

    @Override
    public void adminMenu() throws SQLException {
        boolean loop = true;
        String menu = """
                ------------------ Admin Menu -------------------\n
                1 - Create Account\n
                2 - Delete Account\n
                3 - Update Account\n
                4 - Search Account\n
                5 - Exit\n
                -------------------------------------------------
                """;
        while (loop) {
            atmDisplay(menu);
            String input = getInput("Enter your choice: ");
            switch (input) {
                case "1":
                    withdrawCash();
                    break;
                case "2":
                    depositCash();
                    break;
                case "3":
                    displayBalance();
                    break;
                case "4":
                    loop = false;
                    break;
            }
        }
    }

    @Override
    public boolean isAdmin() {
        return this.admin;
    }

    @Override
    public boolean validateLogin(String username, String pin) {
        try {
            int userID = this.database.loginUser(username, pin);
            if (userID != -1) {
                this.user_id = userID;
                this.admin = this.database.isAdmin(this.user_id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            atmDisplay(e.getMessage());
            return false;
        }
    }

    @Override
    public String withdrawCash() throws SQLException {
        String amount = getInput("Enter the withdrawal amount: ");
        LocalDate currentDate = LocalDate.now();
        try {
            int processedAmount = Integer.parseInt(amount);
            User user = this.database.getUser(this.user_id);
            if (processedAmount < 0) {
                return "You cannot withdraw a negative amount!";
            } else if (processedAmount > user.getAccountBalance()) {
                return "You cannot withdraw more than " + user.getAccountBalance() + "!";
            } else {
                user.setAccountBalance(user.getAccountBalance() - processedAmount);
                this.database.updateUser(user);
                return String.format("""
        Cash Successfully Withdrawn.
        Account #%d
        Date: %s
        Withdrawn: %d
        Balance: %d
        """, user.getAccountID(), currentDate.format(formatter), processedAmount, user.getAccountBalance());
            }
        } catch (NumberFormatException e) {
            return "Invalid input entered!";
        }
    }

    @Override
    public String depositCash() throws SQLException {
        String amount = getInput("Enter the deposit amount: ");
        LocalDate currentDate = LocalDate.now();
        try {
            int processedAmount = Integer.parseInt(amount);
            User user = this.database.getUser(this.user_id);
            if (processedAmount < 0) {
                return "You cannot deposit a negative amount!";
            } else {
                user.setAccountBalance(user.getAccountBalance() + processedAmount);
                this.database.updateUser(user);
                return String.format("""
        Cash successfully deposited.
        Account #%d
        Date: %s
        Deposited: %d
        Balance: %d
        """, user.getAccountID(), currentDate.format(formatter), processedAmount, user.getAccountBalance());
            }
        } catch (NumberFormatException e) {
            return "Invalid input entered!";
        }
    }

    @Override
    public String displayBalance() throws SQLException {
        User user = this.database.getUser(this.user_id);
        LocalDate currentDate = LocalDate.now();
        return String.format("""
        \nAccount #%d
        Date: %s
        Balance: %d
        """, user.getAccountID(), currentDate.format(formatter), user.getAccountBalance());
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the ATM");
        ATM ATM = new ATM();
        ATM.start();
    }

}
