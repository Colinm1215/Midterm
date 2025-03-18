public class ATM implements ATMInterface {
    private ConfigInterface CONFIG;
    private boolean isAdmin = false;


    public ATM() {
        CONFIG = new ATMConfig();
    }

    @Override
    public void start() {
        if (this.loginMenu()) {
            if (this.isAdmin) {
                this.adminMenu();
            } else {
                this.customerMenu();
            }
        }
    }

    @Override
    public String listAccounts() {
        return "";
    }

    @Override
    public String searchForAccount(String login) {
        return "";
    }

    @Override
    public String searchForAccount(int number) {
        return "";
    }

    @Override
    public String updateAccount(int number) {
        return "";
    }

    @Override
    public String depositAccount(int number) {
        return "";
    }

    @Override
    public String createAccount(String login, int pin, String name, int balance, String status) {
        return "";
    }

    @Override
    public String displayBalance() {
        return "";
    }

    @Override
    public String depositCash(int amount) {
        return "";
    }

    @Override
    public String withdrawCash(int amount) {
        return "";
    }

    @Override
    public boolean validateLogin(String username, int pin) {
        return false;
    }

    @Override
    public boolean isAdmin() {
        return false;
    }

    @Override
    public void updateAccountMenu() {

    }

    @Override
    public void adminMenu() {

    }

    @Override
    public void customerMenu() {
    }

    @Override
    public boolean loginMenu() {
        return false;
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the ATM");
        ATM ATM = new ATM();
        ATM.start();
    }

}
