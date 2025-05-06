package Classes.app;

import Classes.database.ATMDatabase;
import Classes.domain.User;
import Interfaces.ATMDatabaseInterface;
import Interfaces.UserInterface;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ATMTest {

    @Test
    void searchForAccount() throws SQLException {
        ATMDatabaseInterface mockDb = mock(ATMDatabase.class);
        UserInterface mockUser = mock(User.class);

        ATM atm = new ATM(mockDb) {
            @Override
            public String getInput(String prompt) {
                return "123";
            }
        };

        when(mockDb.getUser(123)).thenReturn(mockUser);
        when(mockUser.getAccountID()).thenReturn(123);
        when(mockUser.getAccountName()).thenReturn("Alice");
        when(mockUser.getAccountBalance()).thenReturn(1000);
        when(mockUser.getAccountStatus()).thenReturn("Active");
        when(mockUser.getLogin()).thenReturn("alice");
        when(mockUser.getPin()).thenReturn("12345");

        String result = atm.searchForAccount();
        assertTrue(result.contains("Account # 123"));
        assertTrue(result.contains("Holder: Alice"));
    }

    @Test
    void updateAccount() throws SQLException {
        ATMDatabaseInterface mockDb = mock(ATMDatabase.class);
        UserInterface mockUser = mock(User.class);

        String[] inputs = {"123", "newlogin", "54321", "Disabled", "NewName"};

        ATM atm = new ATM(mockDb) {
            private int i = 0;
            @Override public String getInput(String prompt) {
                return inputs[i++];
            }
        };

        when(mockDb.getUser(123)).thenReturn(mockUser);
        when(mockUser.getId()).thenReturn(123);
        when(mockUser.getAccountName()).thenReturn("OldName");
        when(mockUser.getAccountBalance()).thenReturn(500);
        when(mockUser.getAccountStatus()).thenReturn("Active");
        when(mockUser.getLogin()).thenReturn("oldlogin");
        when(mockUser.getPin()).thenReturn("12345");

        String result = atm.updateAccount();

        verify(mockUser).setLogin("newlogin");
        verify(mockUser).setPin("54321");
        verify(mockUser).setAccountStatus("Disabled");
        verify(mockUser).setAccountName("NewName");
        verify(mockDb).updateUser(mockUser);

        assertEquals("User Account Successfully Updated!", result);
    }

    @Test
    void createAccount() throws SQLException {
        ATMDatabaseInterface mockDb = mock(ATMDatabase.class);

        String[] inputs = {"newuser", "12345", "Bob", "500", "Active"};
        ATM atm = new ATM(mockDb) {
            private int i = 0;
            @Override public String getInput(String prompt) {
                return inputs[i++];
            }
        };

        when(mockDb.addUser(any(User.class))).thenReturn(42);

        String result = atm.createAccount();

        verify(mockDb).addUser(any(User.class));

        assertEquals("Account Successfully Created – the account number assigned is: 42", result);
    }

    @Test
    void deleteAccount() throws SQLException {
        ATMDatabaseInterface mockDb = mock(ATMDatabase.class);
        UserInterface mockUser = mock(User.class);

        ATM atm = new ATM(mockDb) {
            @Override public String getInput(String prompt) {
                return "123";
            }
        };

        when(mockDb.getUser(123)).thenReturn(mockUser);
        when(mockUser.getAccountName()).thenReturn("Bob");

        String result = atm.deleteAccount();

        verify(mockDb).deleteAccount(123);

        assertEquals("Account Deleted Successfully", result);
    }

    @Test
    void validateLogin() throws SQLException {
        ATMDatabaseInterface mockDb = mock(ATMDatabase.class);
        ATM atm = new ATM(mockDb);

        when(mockDb.loginUser("user", "12345")).thenReturn(7);
        when(mockDb.isAdmin(7)).thenReturn(false);

        boolean result = atm.validateLogin("user", "12345");
        assertTrue(result);
        assertFalse(atm.isAdmin());
    }

    @Test
    void withdrawCash() throws SQLException {
        ATMDatabaseInterface mockDb = mock(ATMDatabase.class);
        UserInterface mockUser = mock(User.class);

        ATM atm = new ATM(mockDb) {
            {
                this.validateLogin("admin", "12345");
            }
            @Override public String getInput(String prompt) {
                return "100";
            }
            @Override
            public boolean validateLogin(String username, String pin) {
                try {
                    var field = ATM.class.getDeclaredField("user_id");
                    field.setAccessible(true);
                    field.setInt(this, 0);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return true;
            }
        };

        when(mockDb.getUser(0)).thenReturn(mockUser);
        when(mockUser.getAccountBalance()).thenReturn(500);
        when(mockUser.getAccountID()).thenReturn(0);

        String result = atm.withdrawCash();

        verify(mockUser).setAccountBalance(400);
        verify(mockDb).updateUser(mockUser);
        assertTrue(result.contains("Withdrawn: 100"));
    }

    @Test
    void depositCash() throws SQLException {
        ATMDatabaseInterface mockDb = mock(ATMDatabase.class);
        UserInterface mockUser = mock(User.class);

        ATM atm = new ATM(mockDb) {
            {
                this.validateLogin("admin", "12345");
            }
            @Override public String getInput(String prompt) {
                return "100";
            }
            @Override
            public boolean validateLogin(String username, String pin) {
                try {
                    var field = ATM.class.getDeclaredField("user_id");
                    field.setAccessible(true);
                    field.setInt(this, 0);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return true;
            }
        };

        when(mockDb.getUser(0)).thenReturn(mockUser);
        when(mockUser.getAccountBalance()).thenReturn(100);
        when(mockUser.getAccountID()).thenReturn(0);

        String result = atm.depositCash();

        verify(mockUser).setAccountBalance(200);
        verify(mockDb).updateUser(mockUser);
        assertTrue(result.contains("Deposited: 100"));
    }

    @Test
    void displayBalance() throws SQLException {
        ATMDatabaseInterface mockDb = mock(ATMDatabase.class);
        UserInterface mockUser = mock(User.class);

        ATM atm = new ATM(mockDb) {
            {
                this.validateLogin("admin", "12345");
            }
            @Override public String getInput(String prompt) {
                return "100";
            }
            @Override
            public boolean validateLogin(String username, String pin) {
                try {
                    var field = ATM.class.getDeclaredField("user_id");
                    field.setAccessible(true);
                    field.setInt(this, 0);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return true;
            }
        };

        when(mockDb.getUser(0)).thenReturn(mockUser);
        when(mockUser.getAccountID()).thenReturn(0);
        when(mockUser.getAccountBalance()).thenReturn(999);

        String result = atm.displayBalance();
        assertTrue(result.contains("Account #0"));
        assertTrue(result.contains("Balance: 999"));
    }
}