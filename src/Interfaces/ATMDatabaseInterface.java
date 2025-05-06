package Interfaces;

import java.sql.SQLException;

public interface ATMDatabaseInterface {
    int addUser(UserInterface user) throws SQLException;
    void updateUser(UserInterface user) throws SQLException;
    void deleteAccount(int id) throws SQLException;
    int loginUser(String username, String pin) throws SQLException;
    boolean isAdmin(int id) throws SQLException;
    UserInterface getUser(int id) throws SQLException;
}
