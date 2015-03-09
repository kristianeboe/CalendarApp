package no.ntnu.stud.jdbc;

import org.h2.jdbcx.JdbcConnectionPool;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

/**
 * Manages the connection to the database and serves as a general
 * purpose connection object.
 *
 * @author Adrian Hundseth
 */
public class DBConnector {

    private static String userid = "adrianah_pu_g12", password = "banan11";
    private static String url = "jdbc:mysql://mysql.stud.ntnu.no/adrianah_pu_g12";
    private static String driver = "com.mysql.jdbc.Driver";
    private static String url_test = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MODE=MySQL;IGNORECASE=TRUE";
    private static String driver_test = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";

    /**
     * The connection to the database.
     * Connects using <code>DriverManager.getConnection</code>
     * Throws and exception if it fails.
     *
     * @return <code>Connection</code> object con
     */
    private static Connection con = null;


    // Slightly hacky way of finding out if we're running JUnit
    public static boolean isJUnitTest() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        List<StackTraceElement> list = Arrays.asList(stackTrace);
        for (StackTraceElement element : list) {
            if (element.getClassName().startsWith("org.junit.")) {
                return true;
            }
        }
        return false;
    }

    public static Connection getCon() {
        if (!isJUnitTest()) {
            try {
                Class.forName(driver);
                System.out.println("Class for: " + Class.forName(driver));
            } catch (ClassNotFoundException e) {
                System.err.print("ClassNotFoundException: " + e.getMessage());
            }

            try {
                con = DriverManager.getConnection(url, userid, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return con;
        } else {
            return getTestCon();
        }
    }

    public static Connection getTestCon() {
        try {
            DataSource ds = JdbcConnectionPool.create(url_test, "user", "pw");
            con = ds.getConnection();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }


    public static void main(String[] args) {
        Connection con = getCon();
        String query = "SELECT * FROM TEST";
        try {
            Statement stmt = con.createStatement();
            ResultSet rset = stmt.executeQuery(query);
            while (rset.next()) {
                String test = rset.getString("test1");
                System.out.println(test);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}