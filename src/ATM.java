public class ATM implements ATMInterface {
    private ConfigInterface CONFIG;

    public ATM() {
        CONFIG = new ATMConfig();
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
    public void loginMenu() {

    }

    public static void main(String[] args) {
        System.out.println("Welcome to ATM System");
        ATM ATM = new ATM();
    }

}
