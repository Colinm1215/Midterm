package Classes.database;

import Classes.domain.User;
import Config.ATMConfig;
import Interfaces.ATMDatabaseInterface;
import Interfaces.ConfigInterface;
import Interfaces.UserInterface;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ATMDatabaseTest {

    @Test
    void addUser() throws SQLException {
        ATMDatabaseInterface db = new ATMDatabase(new ATMConfig());
        String login = "testuser_" + System.nanoTime();

        UserInterface user = mock(UserInterface.class);
        when(user.getLogin()).thenReturn(login);
        when(user.getPin()).thenReturn("1234");
        when(user.getRole()).thenReturn("customer");
        when(user.getAccountBalance()).thenReturn(1000);
        when(user.getAccountName()).thenReturn("Test");
        when(user.getAccountStatus()).thenReturn("active");

        int id = db.addUser(user);

        assertTrue(id > 0);
    }

    @Test
    void updateUser() throws SQLException {
        ATMDatabaseInterface db = new ATMDatabase(new ATMConfig());
        String before_update = "login_" + System.nanoTime();
        String after_update = before_update + "_new";

        UserInterface user = mock(User.class);
        when(user.getLogin()).thenReturn(before_update);
        when(user.getPin()).thenReturn("1111");
        when(user.getRole()).thenReturn("customer");
        when(user.getAccountBalance()).thenReturn(200);
        when(user.getAccountName()).thenReturn("OldName");
        when(user.getAccountStatus()).thenReturn("active");

        int id = db.addUser(user);
        UserInterface update = db.getUser(id);


        update.setLogin(after_update);

        db.updateUser(update);

        UserInterface afterUpdate = db.getUser(id);

        assertEquals(after_update, afterUpdate.getLogin());
    }

    @Test
    void getUser() throws SQLException {
        ATMDatabaseInterface db = new ATMDatabase(new ATMConfig());
        String login = "testuser_" + System.nanoTime();

        UserInterface user = mock(UserInterface.class);
        when(user.getLogin()).thenReturn(login);
        when(user.getPin()).thenReturn("1234");
        when(user.getRole()).thenReturn("customer");
        when(user.getAccountBalance()).thenReturn(1000);
        when(user.getAccountName()).thenReturn("Test");
        when(user.getAccountStatus()).thenReturn("active");

        int id = db.addUser(user);

        UserInterface retrievedUser = db.getUser(id);

        assertNotNull(retrievedUser);
        assertEquals(login, retrievedUser.getLogin());
        assertEquals("Test", retrievedUser.getAccountName());
        assertEquals(1000, retrievedUser.getAccountBalance());
        assertEquals("active", retrievedUser.getAccountStatus());
        assertEquals("customer", retrievedUser.getRole());
        assertEquals(id, retrievedUser.getId());
        assertEquals("1234", retrievedUser.getPin());

    }

    @Test
    void deleteAccount() throws SQLException {
        ATMDatabaseInterface db = new ATMDatabase(new ATMConfig());

        String name = "TooDelete-" + System.nanoTime();

        UserInterface user = mock(User.class);
        when(user.getLogin()).thenReturn(name);
        when(user.getPin()).thenReturn("1111");
        when(user.getRole()).thenReturn("customer");
        when(user.getAccountBalance()).thenReturn(200);
        when(user.getAccountName()).thenReturn("OldName");
        when(user.getAccountStatus()).thenReturn("active");

        int id = db.addUser(user);

        db.deleteAccount(id);

        User retrievedUser = (User) db.getUser(id);

        assertNull(retrievedUser.getAccountStatus());
        assertNull(retrievedUser.getAccountName());
    }

    @Test
    void loginUser() throws SQLException {
        ATMDatabaseInterface db = new ATMDatabase(new ATMConfig());
        int loginId = db.loginUser("admin", "12345");
        assertEquals(1, loginId);
    }

    @Test
    void testATMConfig() {
        ATMConfig config = new ATMConfig();
        assertNotNull(config.getDB_URL());
    }

    @Test
    void isAdmin() throws SQLException {
        ATMDatabaseInterface db = new ATMDatabase(new ATMConfig());

        UserInterface retrievedUser = db.getUser(1);

        assertNotNull(retrievedUser);
        assertEquals("admin", retrievedUser.getLogin());
        assertEquals("admin", retrievedUser.getRole());
        assertTrue(db.isAdmin(1));
    }

    @Test
    void isAdminNull() throws SQLException {
        ATMDatabaseInterface db = new ATMDatabase(new ATMConfig());

        UserInterface retrievedUser = db.getUser(-1);

        assertNull(retrievedUser);
    }

    @Test
    void getUserNull() throws SQLException {
        ATMDatabaseInterface db = new ATMDatabase(new ATMConfig());

        UserInterface retrievedUser = db.getUser(-1);

        assertNull(retrievedUser);
    }
}