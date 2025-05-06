package Classes.app;

import Classes.domain.Account;
import Classes.domain.User;
import Classes.database.ATMDatabase;
import Config.ATMConfig;
import Interfaces.ATMDatabaseInterface;
import Interfaces.ATMInterface;
import Interfaces.UserInterface;

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

    public ATM(ATMDatabaseInterface database) {
        this.database = database;
    }

    public ATM() throws SQLException {
        this(new ATMDatabase(new ATMConfig()));
    }


    @Override
    public void start() {
        try {
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
    public String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    @Override
    public void atmDisplay(String output) {
        System.out.println(output);
    }

    @Override
    public String searchForAccount() throws SQLException {
        String input = getInput("Enter account number: ");
        try {
            int processed = Integer.parseInt(input);
            UserInterface user = this.database.getUser(processed);
            if (user != null) {
                return String.format("""
                        The account information is:
                        Account # %d
                        Holder: %s
                        Balance: %d
                        Status: %s
                        Login: %s
                        Pin Code: %s
                        """,
                        user.getAccountID(),
                        user.getAccountName(),
                        user.getAccountBalance(),
                        user.getAccountStatus(),
                        user.getLogin(),
                        user.getPin());
            }
            return """
                        Account could not be found!
                        """;
        } catch (NumberFormatException e) {
            return "Invalid input entered!";
        }
    }

    @Override
    public String updateAccount() throws SQLException {
        String input = getInput("Enter account number: ");
        try {
            int processed = Integer.parseInt(input);
            UserInterface user = this.database.getUser(processed);
            if (user != null) {
                atmDisplay("""
                        Current Account Details
                        Account # %d
                        Holder: %s
                        Balance: %d
                        Status: %s
                        Login: %s
                        Pin Code: %s
                        """.formatted(user.getId(),
                        user.getAccountName(),
                        user.getAccountBalance(),
                        user.getAccountStatus(),
                        user.getLogin(),
                        user.getPin()));
                input = getInput("Enter new Login (or press Enter to keep current): ");
                if (!input.isEmpty()) {
                    user.setLogin(input);
                }
                input = getInput("Enter new Pin (or press Enter to keep current): ");
                if (!input.isEmpty()) {
                    user.setPin(input);
                }
                input = getInput("Enter new Account Status (or press Enter to keep current): ");
                if (!input.isEmpty()) {
                    user.setAccountStatus(input);
                }
                input = getInput("Enter new Account Holder (or press Enter to keep current): ");
                if (!input.isEmpty()) {
                    user.setAccountName(input);
                }
                this.database.updateUser(user);
                return "User Account Successfully Updated!";
            }
            return "User Account could not be found!";
        } catch (NumberFormatException e) {
            return "Invalid input entered!";
        }
    }

    @Override
    public String createAccount() throws SQLException {
        atmDisplay("Creating account...");
        String login = getInput("Enter login: ");
        String pin = getInput("Enter pin: ");
        if (!pin.matches("\\d{5}")) return "Invalid pin!";
        String name = getInput("Enter account name: ");
        String balance = getInput("Enter balance: ");
        try {
            int processed = Integer.parseInt(balance);
            String status = getInput("Enter account status: ");
            if (status.equals("Active") || status.equals("Disabled")) {
                User newUser = new User(-1, login, pin, "customer", new Account(-1, processed, status, name));
                int id = this.database.addUser(newUser);
                return "Account Successfully Created – the account number assigned is: %d".formatted(id);
            }
            return "Invalid Status";
        } catch (NumberFormatException e) {
            return "Invalid balance input entered!";
        }
    }

    @Override
    public String deleteAccount() throws SQLException {
        String input = getInput("Enter the account number to which you want to delete: ");
        try {
            int processed = Integer.parseInt(input);
            UserInterface user = this.database.getUser(processed);
            if (user != null) {
                input = getInput(String.format(
                        "You wish to delete the account held by %s. If this information is correct, please re-enter the account number: ", user.getAccountName()));
                try {
                    int processed_new = Integer.parseInt(input);
                    if (processed_new == processed) {
                        this.database.deleteAccount(processed);
                        return "Account Deleted Successfully";
                    }
                } catch (NumberFormatException e) {
                    return "Invalid input entered!";
                }
            } else {
                return "User not found!";
            }
        } catch (NumberFormatException e) {
            return "Invalid input entered!";
        }
        return "Invalid input entered!";
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
                    atmDisplay(createAccount());
                    getInput("Press enter to continue...");
                    atmDisplay("\n\n");
                    break;
                case "2":
                    atmDisplay(deleteAccount());
                    getInput("Press enter to continue...");
                    atmDisplay("\n\n");
                    break;
                case "3":
                    atmDisplay(updateAccount());
                    getInput("Press enter to continue...");
                    atmDisplay("\n\n");
                    break;
                case "4":
                    atmDisplay(searchForAccount());
                    getInput("Press enter to continue...");
                    atmDisplay("\n\n");
                    break;
                case "5":
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
            UserInterface user = this.database.getUser(this.user_id);
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
            UserInterface user = this.database.getUser(this.user_id);
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
        UserInterface user = this.database.getUser(this.user_id);
        LocalDate currentDate = LocalDate.now();
        return String.format("""
        \nAccount #%d
        Date: %s
        Balance: %d
        """, user.getAccountID(), currentDate.format(formatter), user.getAccountBalance());
    }

    public static void main(String[] args) throws SQLException {
        System.out.println("Welcome to the ATM");
        ATM ATM = new ATM();
        ATM.start();
    }

}
