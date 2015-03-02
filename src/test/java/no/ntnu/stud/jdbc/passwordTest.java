package no.ntnu.stud.jdbc;

import no.ntnu.stud.model.User;
import no.ntnu.stud.security.Authentication;
import no.ntnu.stud.security.SHAHashGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Created by adrianh on 02.03.15.
 */
public class PasswordTest {

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
    public void testChangePassword() {
        byte[] newSalt = SHAHashGenerator.getSalt();
        try {
            EditData.changePassword(user, "12345", "password".toCharArray(), newSalt);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertTrue(Authentication.authenticate(user.getEmail(), "password"));
    }
}
