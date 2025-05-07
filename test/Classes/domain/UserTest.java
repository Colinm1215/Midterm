package Classes.domain;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void getId() {
        User user = new User(1, "testuser", "1234", "customer", null);
        assertEquals(1, user.getId());
    }

    @Test
    void getLogin() {
        User user = new User(1, "testuser", "1234", "customer", null);
        assertEquals("testuser", user.getLogin());
    }

    @Test
    void setLogin() {
        User user = new User(1, "testuser", "1234", "customer", null);
        assertEquals("testuser", user.getLogin());
        user.setLogin("newuser");
        assertEquals("newuser", user.getLogin());
    }

    @Test
    void getPin() {
        User user = new User(1, "testuser", "1234", "customer", null);
        assertEquals("1234", user.getPin());
    }

    @Test
    void setPin() {
        User user = new User(1, "testuser", "1234", "customer", null);
        assertEquals("1234", user.getPin());
        user.setPin("5678");
        assertEquals("5678", user.getPin());
    }

    @Test
    void setAccountBalance() {
        Account account = new Account(1, 1000, "active", "test");
        User user = new User(1, "testuser", "1234", "customer", account);
        assertEquals(1000, user.getAccountBalance());
        user.setAccountBalance(500);
        assertEquals(500, user.getAccountBalance());
    }

    @Test
    void getAccountBalance() {
        Account account = new Account(1, 1000, "active", "test");
        User user = new User(1, "testuser", "1234", "customer", account);
        assertEquals(1000, user.getAccountBalance());
    }

    @Test
    void getRole() {
        User user = new User(1, "testuser", "1234", "customer", null);
        assertEquals("customer", user.getRole());
    }

    @Test
    void setRole() {
        User user = new User(1, "testuser", "1234", "customer", null);
        assertEquals("customer", user.getRole());
        user.setRole("admin");
        assertEquals("admin", user.getRole());
    }

    @Test
    void getAccountName() {
        Account account = new Account(1, 1000, "active", "test");
        User user = new User(1, "testuser", "1234", "customer", account);
        assertEquals("test", user.getAccountName());
    }

    @Test
    void setAccountName() {
        Account account = new Account(1, 1000, "active", "test");
        User user = new User(1, "testuser", "1234", "customer", account);
        assertEquals("test", user.getAccountName());
        user.setAccountName("newTest");
        assertEquals("newTest", user.getAccountName());
    }

    @Test
    void getAccountStatus() {
        Account account = new Account(1, 1000, "active", "test");
        User user = new User(1, "testuser", "1234", "customer", account);
        assertEquals("active", user.getAccountStatus());
    }

    @Test
    void setAccountStatus() {
        Account account = new Account(1, 1000, "active", "test");
        User user = new User(1, "testuser", "1234", "customer", account);
        assertEquals("active", user.getAccountStatus());
        user.setAccountStatus("disabled");
        assertEquals("disabled", user.getAccountStatus());
    }

    @Test
    void getAccountID() {
        Account account = new Account(1, 1000, "active", "test");
        User user = new User(1, "testuser", "1234", "customer", account);
        assertEquals(1, user.getAccountID());
    }

    @Test
    void getAccountBalanceNull() {
        User user = new User(1, "testuser", "1234", "customer", null);
        assertEquals(-1,user.getAccountBalance());
    }

    @Test
    void getAccountNameNull() {
        User user = new User(1, "testuser", "1234", "customer", null);
        assertNull(user.getAccountName());
    }

    @Test
    void getAccountStatusNull() {
        User user = new User(1, "testuser", "1234", "customer", null);
        assertNull(user.getAccountStatus());
    }

    @Test
    void getAccountIDNull() {
        User user = new User(1, "testuser", "1234", "customer", null);
        assertEquals(-1, user.getAccountID());
    }
}