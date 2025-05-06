package Interfaces;

import Classes.domain.User;

import java.sql.SQLException;

public interface ATMDatabaseInterface {
    int addUser(User user) throws SQLException;
    void updateUser(User user) throws SQLException;
    void deleteAccount(int id) throws SQLException;
    int loginUser(String username, String pin) throws SQLException;
    boolean isAdmin(int id) throws SQLException;
    User getUser(int id) throws SQLException;
}
