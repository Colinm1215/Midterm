package Classes;

import Interfaces.ATMInterface;

public class ATM implements ATMInterface {
    private boolean admin = false;

    @Override
    public void start() {
        if (this.loginMenu()) {
            if (this.admin) {
                this.adminMenu();
            } else {
                this.customerMenu();
            }
        }
        this.end();
    }

    @Override
    public void end() {

    }

    @Override
    public String listAccounts() {
        return "";
    }

    @Override
    public String getInput(String prompt) {
        return "";
    }

    @Override
    public String atmDisplay(String output) {
        return "";
    }

    // Private helper functions for searchAccount logic
    private String searchForAccount(String login) {
        return "";
    }

    private String searchForAccount(int number) {
        return "";
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
    public String createAccount(String login, int pin, String name, int balance, String status) {
        return "";
    }

    @Override
    public String deleteAccount() {
        return "";
    }

    @Override
    public boolean loginMenu() {
        String username = getInput("Enter login: ");
        String pin = getInput("Enter pin: ");
        // validate logins
        // admin logic
        return true;
    }

    @Override
    public void customerMenu() {
    }

    @Override
    public void adminMenu() {

    }

    @Override
    public boolean isAdmin() {
        return false;
    }

    @Override
    public boolean validateLogin(String username, int pin) {
        return false;
    }

    @Override
    public String withdrawCash(int amount) {
        return "";
    }

    @Override
    public String depositCash(int amount) {
        return "";
    }

    @Override
    public String displayBalance() {
        return "";
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the Classes.ATM");
        ATM ATM = new ATM();
        ATM.start();
    }

}
