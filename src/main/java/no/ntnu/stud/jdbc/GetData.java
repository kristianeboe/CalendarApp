package no.ntnu.stud.jdbc;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import no.ntnu.stud.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by adrianh on 21.02.15.
 */
public class GetData {

    public static User getUser(int userID) {
        Connection con = DBConnector.getCon();
        User user = null;
        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                String strSelect = "SELECT * FROM user WHERE userID='" + userID + "';";
                System.out.println("Performing SQL Query [" + strSelect + "]");
                ResultSet rset = stmt.executeQuery(strSelect);

                while(rset.next()) {
                    String lastName = rset.getString("last_name");
                    String middleName = rset.getString("middle_name");
                    String givenName = rset.getString("given_name");
                    String email = rset.getString("email");
                    user = new User(new SimpleIntegerProperty(userID), new SimpleStringProperty(lastName), new SimpleStringProperty(middleName), new SimpleStringProperty(givenName), new SimpleStringProperty(email));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("No Connection");
        }
        return user;
    }

    public static ArrayList<User> getUsers() {
        Connection con = DBConnector.getCon();
        ArrayList<User> users = new ArrayList<User>();

        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                String strSelect = "SELECT * FROM user";
                System.out.println("Performing SQL Query [" + strSelect + "]");
                ResultSet rset = stmt.executeQuery(strSelect);

                while (rset.next()) {
                    int userID = rset.getInt("userID");
                    String lastName = rset.getString("last_name");
                    String middleName = rset.getString("middle_name");
                    String givenName = rset.getString("given_name");
                    String email = rset.getString("email");
                    User user = new User(new SimpleIntegerProperty(userID), new SimpleStringProperty(lastName), new SimpleStringProperty(middleName), new SimpleStringProperty(givenName), new SimpleStringProperty(email));
                    users.add(user);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("No connection");
        }
        return users;
    }


}
