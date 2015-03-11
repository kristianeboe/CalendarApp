package no.ntnu.stud.jdbc;

import no.ntnu.stud.SetUp;
import no.ntnu.stud.model.User;
import no.ntnu.stud.security.Authentication;
import no.ntnu.stud.security.SHAHashGenerator;
import org.apache.log4j.Logger;
import org.junit.*;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Created by adrianh on 02.03.15.
 */
public class PasswordTest {

    private static User user;
    private static Connection conn;
    private static Logger logger;

    @BeforeClass
    public static void setUp() {
        logger = Logger.getLogger("swag");
        conn = DBConnector.getTestCon();
        SetUp.setUpDatabase(conn);
    }

    @AfterClass
    public static void tearDown() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void createUser() {
        user = new User("Testerson", "Testing", "Test", "test@testing.test", "12345");
        user = user.create();
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

    @Test
    public void testForgotPassword() {
        String newPassword = "";
        newPassword = EditData.forgotPassword(user.getEmail());
        System.out.println(newPassword);
        assertTrue(Authentication.authenticate(user.getEmail(), newPassword));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChangeWrongPassword() {
        byte[] newSalt = SHAHashGenerator.getSalt();
        try {
            EditData.changePassword(user, "wrongPassword", "password".toCharArray(), newSalt);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        assertFalse(Authentication.authenticate(user.getEmail(), "password"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChangeBlankPassword() {
        byte[] newSalt = SHAHashGenerator.getSalt();
        try {
            EditData.changePassword(user, "", "password".toCharArray(), newSalt);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        assertFalse(Authentication.authenticate(user.getEmail(), "password"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChangeNullPassword() {
        byte[] newSalt = SHAHashGenerator.getSalt();
        try{
            EditData.changePassword(user, null, "password".toCharArray(), newSalt);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        assertFalse(Authentication.authenticate(user.getEmail(), "password"));
    }

    @Test
    public void testWrongEmailForgotPassword() {
        assertNull(EditData.forgotPassword("no.no@no.yes"));
    }

    @Test
    public void testBlankEmailForgotPassword() {
        assertNull(EditData.forgotPassword(""));
    }
}