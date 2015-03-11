package no.ntnu.stud.security;

import no.ntnu.stud.SetUp;
import no.ntnu.stud.jdbc.DBConnector;
import no.ntnu.stud.jdbc.EditData;
import no.ntnu.stud.jdbc.InsertData;
import no.ntnu.stud.model.User;
import org.apache.log4j.Logger;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Created by adrianh on 02.03.15.
 */
public class AuthenticationTest {

    Authentication auth = new Authentication();
    private User user;
    String password;
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
        password = "12345";
        user = new User("Testerson", "Testing", "Test", "howdoyoueven@test.com", password);
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
    public void testAuthenticate() {
        assertTrue(Authentication.authenticate(user.getEmail(), password));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAuthenticateMailNull() {
        assertFalse(Authentication.authenticate(null, password));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAuthenticationPassNull() {
        assertFalse(Authentication.authenticate(user.getEmail(), null));
    }

    @Test
    public void testLogin() {
        auth.login(user.getEmail(), password);
        assertEquals(user.getEmail(), auth.getLoggedInUser().getEmail());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testLoginEmailNull() {
        auth.login(null, password);
        assertNotEquals(user.getEmail(), auth.getLoggedInUser().getEmail());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testLoginPassNull() {
        auth.login(user.getEmail(), null);
        assertNotEquals(user.getEmail(), auth.getLoggedInUser().getEmail());
    }
}
