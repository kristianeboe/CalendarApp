package no.ntnu.stud.jdbc;

import no.ntnu.stud.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
                    user = new User(userID, lastName, middleName, givenName, email);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("No Connection");
        }
        return user;
    }
}
