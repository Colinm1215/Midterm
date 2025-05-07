package Classes.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void setAccountBalance() {
        Account account = new Account(1, 1000, "active", "test");
        assertEquals(1000, account.getAccountBalance());
        account.setAccountBalance(500);
        assertEquals(500, account.getAccountBalance());
    }

    @Test
    void setAccountStatus() {
        Account account = new Account(1, 1000, "active", "test");
        assertEquals("active", account.getAccountStatus());
        account.setAccountStatus("disabled");
        assertEquals("disabled", account.getAccountStatus());
    }

    @Test
    void getAccountBalance() {
        Account account = new Account(1, 1000, "active", "test");
        assertEquals(1000, account.getAccountBalance());
    }

    @Test
    void getAccountStatus() {
        Account account = new Account(1, 1000, "active", "test");
        assertEquals("active", account.getAccountStatus());
    }

    @Test
    void getAccountId() {
        Account account = new Account(1, 1000, "active", "test");
        assertEquals(1, account.getAccountId());
    }

    @Test
    void getAccountName() {
        Account account = new Account(1, 1000, "active", "test");
        assertEquals("test", account.getAccountName());
    }

    @Test
    void setAccountName() {
        Account account = new Account(1, 1000, "active", "test");
        assertEquals("test", account.getAccountName());
        account.setAccountName("NewTest");
        assertEquals("NewTest", account.getAccountName());
    }
}