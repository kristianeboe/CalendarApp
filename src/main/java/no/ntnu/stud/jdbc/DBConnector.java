package no.ntnu.stud.jdbc;

import java.sql.*;

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

    /**
     * The connection to the database.
     * Connects using <code>DriverManager.getConnection</code>
     * Throws and exception if it fails.
     */
    private static Connection con = null;

    public static Connection getCon() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.err.print("ClassNotFoundException: " + e.getMessage());
        }

        try {
            con = DriverManager.getConnection(url, userid, password);
        } catch (SQLException e) {
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