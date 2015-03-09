package no.ntnu.stud;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by sklirg on 09/03/15.
 */
public class SetUp {
    private static Logger logger;

    public static void setUpDatabase(Connection conn) {
        try {
//            logger.debug("Setting up database");
            PreparedStatement statement = conn.prepareStatement("CREATE TABLE appointment\n" +
                    "(\n" +
                    "    appointmentID INT PRIMARY KEY NOT NULL,\n" +
                    "    title VARCHAR(45),\n" +
                    "    ownerID INT NOT NULL,\n" +
                    "    startTime TIME,\n" +
                    "    endTime TIME,\n" +
                    "    roomID INT,\n" +
                    "    location VARCHAR(45),\n" +
                    "    description VARCHAR(45),\n" +
                    "    attending INT NOT NULL,\n" +
                    "    alarmTime DATETIME,\n" +
                    "    appointmentDate DATE\n" +
                    ");\n" +
                    "CREATE TABLE groupAttends\n" +
                    "(\n" +
                    "    appointmentID INT NOT NULL,\n" +
                    "    groupID INT NOT NULL,\n" +
                    "    PRIMARY KEY (appointmentID, groupID)\n" +
                    ");\n" +
                    "CREATE TABLE hasNotification\n" +
                    "(\n" +
                    "    notificationID INT NOT NULL,\n" +
                    "    userID INT NOT NULL,\n" +
                    "    seen TINYINT DEFAULT 0,\n" +
                    "    PRIMARY KEY (notificationID, userID)\n" +
                    ");\n" +
                    "CREATE TABLE notification\n" +
                    "(\n" +
                    "    notificationID INT PRIMARY KEY NOT NULL,\n" +
                    "    message VARCHAR(45)\n" +
                    ");\n" +
                    "CREATE TABLE room\n" +
                    "(\n" +
                    "    roomID INT PRIMARY KEY NOT NULL,\n" +
                    "    name VARCHAR(45) NOT NULL,\n" +
                    "    capacity INT NOT NULL\n" +
                    ");\n" +
                    "CREATE TABLE subGroup\n" +
                    "(\n" +
                    "    groupID INT NOT NULL,\n" +
                    "    subGroupID INT NOT NULL,\n" +
                    "    PRIMARY KEY (groupID, subGroupID)\n" +
                    ");\n" +
                    "CREATE TABLE user\n" +
                    "(\n" +
                    "    userID INT PRIMARY KEY NOT NULL,\n" +
                    "    middleName VARCHAR(45),\n" +
                    "    email VARCHAR(45),\n" +
                    "    password LONGBLOB,\n" +
                    "    salt LONGBLOB,\n" +
                    "    givenName VARCHAR(45),\n" +
                    "    lastName VARCHAR(45)\n" +
                    ");\n" +
                    "CREATE TABLE userAttends\n" +
                    "(\n" +
                    "    appointmentID INT NOT NULL,\n" +
                    "    userID INT NOT NULL,\n" +
                    "    attending CHAR(2),\n" +
                    "    PRIMARY KEY (appointmentID, userID)\n" +
                    ");\n" +
                    "CREATE TABLE userGroup\n" +
                    "(\n" +
                    "    groupID INT PRIMARY KEY NOT NULL,\n" +
                    "    name VARCHAR(45)\n" +
                    ");\n" +
                    "CREATE TABLE userInGroup\n" +
                    "(\n" +
                    "    userID INT NOT NULL,\n" +
                    "    groupID INT NOT NULL,\n" +
                    "    PRIMARY KEY (userID, groupID)\n" +
                    ");\n" +
                    "ALTER TABLE appointment ADD FOREIGN KEY (roomID) REFERENCES room (roomID);\n" +
                    "ALTER TABLE appointment ADD FOREIGN KEY (ownerID) REFERENCES user (userID);\n" +
                    "CREATE INDEX ownerID_UNIQUE ON appointment (ownerID);\n" +
                    "ALTER TABLE groupAttends ADD FOREIGN KEY (appointmentID) REFERENCES appointment (appointmentID) ON DELETE CASCADE;\n" +
                    "ALTER TABLE groupAttends ADD FOREIGN KEY (groupID) REFERENCES userGroup (groupID);\n" +
                    "CREATE INDEX fk_Appointment_has_Group_Appointment1_idx ON groupAttends (appointmentID);\n" +
                    "CREATE INDEX fk_Appointment_has_Group_Group1_idx ON groupAttends (groupID);\n" +
                    "ALTER TABLE hasNotification ADD FOREIGN KEY (notificationID) REFERENCES notification (notificationID);\n" +
                    "ALTER TABLE hasNotification ADD FOREIGN KEY (userID) REFERENCES user (userID);\n" +
                    "CREATE INDEX fk_Notification_has_User_Notification1_idx ON hasNotification (notificationID);\n" +
                    "CREATE INDEX fk_Notification_has_User_User1_idx ON hasNotification (userID);\n" +
                    "ALTER TABLE subGroup ADD FOREIGN KEY (groupID) REFERENCES userGroup (groupID);\n" +
                    "ALTER TABLE subGroup ADD FOREIGN KEY (subGroupID) REFERENCES userGroup (groupID) ON DELETE CASCADE;\n" +
                    "CREATE INDEX fk_Group_has_Group_Group1_idx ON subGroup (groupID);\n" +
                    "CREATE INDEX fk_Group_has_Group_Group2_idx ON subGroup (subGroupID);\n" +
                    "CREATE UNIQUE INDEX unique_email ON user (email);\n" +
                    "ALTER TABLE userAttends ADD FOREIGN KEY (appointmentID) REFERENCES appointment (appointmentID) ON DELETE CASCADE;\n" +
                    "ALTER TABLE userAttends ADD FOREIGN KEY (userID) REFERENCES user (userID) ON DELETE CASCADE;\n" +
                    "CREATE INDEX fk_Appointment_has_User_Appointment1_idx ON userAttends (appointmentID);\n" +
                    "CREATE INDEX fk_Appointment_has_User_User1_idx ON userAttends (userID);\n" +
                    "ALTER TABLE userInGroup ADD FOREIGN KEY (groupID) REFERENCES userGroup (groupID) ON DELETE CASCADE;\n" +
                    "ALTER TABLE userInGroup ADD FOREIGN KEY (userID) REFERENCES user (userID) ON DELETE CASCADE;\n" +
                    "CREATE INDEX fk_User_has_Group_Group1_idx ON userInGroup (groupID);\n" +
                    "CREATE INDEX fk_User_has_Group_User1_idx ON userInGroup (userID);\n");

            statement.execute();
//            logger.debug("Done");


//            logger.debug("Successfully set up database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
