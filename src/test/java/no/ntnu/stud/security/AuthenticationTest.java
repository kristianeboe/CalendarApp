package no.ntnu.stud.security;

import no.ntnu.stud.jdbc.EditData;
import no.ntnu.stud.jdbc.InsertData;
import no.ntnu.stud.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Created by adrianh on 02.03.15.
 */
public class AuthenticationTest {

    Authentication auth = new Authentication();
    private User user;

    @Before
    public void createUser() {
        user = InsertData.createUser("Testerson", "Testing", "Test", "test@testing.test", "12345");
    }
    @After
    public void deleteUser() {
        try {
            EditData.deleteUser(user.getUserID());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAuthenticate() {
        assertTrue(Authentication.authenticate(user.getEmail(), "12345"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAuthenticateMailNull() {
        assertFalse(Authentication.authenticate(null, "12345"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAuthenticationPassNull() {
        assertFalse(Authentication.authenticate(user.getEmail(), null));
    }

    @Test
    public void testLogin() {
        auth.login(user.getEmail(), "12345");
        assertEquals(user.getEmail(), auth.getLoggedInUser().getEmail());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testLoginEmailNull() {
        auth.login(null, "12345");
        assertNotEquals(user.getEmail(), auth.getLoggedInUser().getEmail());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testLoginPassNull() {
        auth.login(user.getEmail(), null);
        assertNotEquals(user.getEmail(), auth.getLoggedInUser().getEmail());
    }
}
