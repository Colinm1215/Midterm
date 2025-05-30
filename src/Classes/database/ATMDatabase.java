package Classes.database;

import Classes.domain.Account;
import Classes.domain.User;
import Interfaces.ATMDatabaseInterface;
import Interfaces.AccountInterface;
import Interfaces.ConfigInterface;
import Interfaces.UserInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ATMDatabase implements ATMDatabaseInterface {
    private final Connection con;
    private final ConfigInterface config;

    public ATMDatabase(ConfigInterface config) throws SQLException {
        this.config = config;
        con = DriverManager.getConnection(
                this.config.getDB_URL(), this.config.getDB_USER(), this.config.getDB_PASSWORD());
    }

    @Override
    public int addUser(UserInterface user) throws SQLException {
        String query = "INSERT INTO users (login, pin, role) VALUES (?, ?, ?)";
        List<Object> params = new ArrayList<>();
        params.add(user.getLogin());
        params.add(user.getPin());
        params.add(user.getRole());
        int newUser = updateDB(query, params).getFirst();
        if (newUser == -1) throw new SQLException();
        query = "INSERT INTO accounts (user_id, balance, name, status) VALUES (?, ?, ?, ?)";
        params = new ArrayList<>();
        params.add(newUser);
        params.add(user.getAccountBalance());
        params.add(user.getAccountName());
        params.add(user.getAccountStatus());
        updateDB(query, params);
        return newUser;
    }

    @Override
    public void updateUser(UserInterface user) throws SQLException {
        String query = "UPDATE users SET login = ?, pin = ?, role = ? WHERE id = ?";
        List<Object> params = new ArrayList<>();
        params.add(user.getLogin());
        params.add(user.getPin());
        params.add(user.getRole());
        params.add(user.getId());
        updateDB(query, params);
        query = "UPDATE accounts SET balance = ?, name = ?,  status = ? WHERE user_id = ?";
        params = new ArrayList<>();
        params.add(user.getAccountBalance());
        params.add(user.getAccountName());
        params.add(user.getAccountStatus());
        params.add(user.getId());
        updateDB(query, params);
    }

    @Override
    public void deleteAccount(int id) throws SQLException {
        String deleteAccountQuery = "DELETE FROM accounts WHERE id = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(getAccountID(id));
        updateDB(deleteAccountQuery, params);
    }

    @Override
    public int loginUser(String username, String pin) throws SQLException {
        String query = "SELECT * FROM users WHERE login = ? AND pin = ?";
        List<Object> params = new ArrayList<>();
        params.add(username);
        params.add(pin);

        UserInterface user = dbGetUsers(query, params).getFirst();

        return (user == null) ? -1 : user.getId();
    }

    @Override
    public boolean isAdmin(int id) throws SQLException {
        UserInterface user = getUser(id);
        if (user == null) return false;
        return (user.getRole().equals("admin"));
    }

    @Override
    public UserInterface getUser(int id) throws SQLException {
        String query = "SELECT * FROM users WHERE id = ?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        List<UserInterface> users = dbGetUsers(query, params);

        return users.isEmpty() ? null : users.getFirst();
    }

    private List<UserInterface> dbGetUsers(String query, List<Object> params) throws SQLException {
        List<UserInterface> users = new ArrayList<>();
        PreparedStatement stmt = con.prepareStatement(query);

        for (int i = 0; i < params.size(); i++) {
            stmt.setObject(i + 1, params.get(i));
        }

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(parseUser(rs));
            }
        }

        return users;
    }

    private ArrayList<Integer> updateDB(String query, List<Object> params) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        for (int i = 0; i < params.size(); i++) {
            stmt.setObject(i + 1, params.get(i));
        }
        stmt.executeUpdate();
        ArrayList<Integer> ids = new ArrayList<>();
        try (ResultSet rs = stmt.getGeneratedKeys()) {
            if (rs.next()) {
                ids.add(rs.getInt(1));
            }
        }
        return ids;
    }

    private UserInterface parseUser(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String login = rs.getString("login");
        String pin = rs.getString("pin");
        String role = rs.getString("role");
        return new User(id, login, pin, role, getAccount(id));
    }

    private AccountInterface getAccount(int id) throws SQLException {
        String query = "SELECT balance, status, name FROM accounts WHERE user_id = ?";

        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, id);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                int balance = rs.getInt("balance");
                String status = rs.getString("status");
                String name = rs.getString("name");
                return new Account(id, balance, status, name);
            } else {
                return null;
            }
        }
    }

    private int getAccountID(int id) throws SQLException {
        String query = "SELECT id FROM accounts WHERE user_id = ?";

        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, id);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                return -1;
            }
        }
    }
}
