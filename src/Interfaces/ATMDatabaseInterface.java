package Interfaces;

import Classes.User;

import java.sql.SQLException;
import java.util.List;

public interface ATMDatabaseInterface {
    void addUser(User user) throws SQLException;
    void updateUser(User user) throws SQLException;
    void deleteAccount(int id) throws SQLException;
    int loginUser(String username, String pin) throws SQLException;
    boolean isAdmin(int id) throws SQLException;
    User getUser(int id) throws SQLException;
    List<User> getAllUsers() throws SQLException;
}
