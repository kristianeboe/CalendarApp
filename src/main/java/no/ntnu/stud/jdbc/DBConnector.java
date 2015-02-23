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
     * The connection to the datbase.
     * Connects using <code>DriverManager.getConnection</code>
     * Throws and exception if it fails.
     */
    private static Connection con = null;

    protected static Connection getCon() {
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

    public static void makeSomething(String somethingNavn, int somethingID) {
        Connection con = getCon();
        if (con != null) {

            String query = "INSERT INTO something ("
                    + " somethingNavn,"
                    + " somethingID) VALUES ("
                    + "?, ?)";
            try {
                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.setString(1, somethingNavn);
                preparedStmt.setInt(2, somethingID);
                preparedStmt.execute();
            } catch (SQLException e) {
                System.err.println("SQLException: " + e.getMessage());
            }
        }
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