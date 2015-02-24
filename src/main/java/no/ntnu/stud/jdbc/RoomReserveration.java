package no.ntnu.stud.jdbc;

import no.ntnu.stud.model.Room;
import no.ntnu.stud.util.TimeConverter;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by kristoffer on 24.02.15.
 */
public class RoomReserveration {
    DBConnector dbConnector = new DBConnector();
    TimeConverter timeConverter = new TimeConverter();

    /**Gets the smallest available room
     *
     * @return roomID
     */
    public Room getSmallestRoom(LocalDateTime startTime, LocalDateTime endTime, int numPeople){
        Room room = null;
        Connection con = dbConnector.getCon();
        Timestamp startTimestamp = timeConverter.localDateTimeToTimestamp(startTime);
        Timestamp endTimestamp = timeConverter.localDateTimeToTimestamp(endTime);
        if(con != null){
            try{
                Statement stmt = con.createStatement();
                String sql = "SELECT * FROM room WHERE capacity >="+numPeople+" AND roomID NOT IN( SELECT roomID FROM appointment WHERE ( ("+startTimestamp+" > from_time AND "+startTimestamp+" < to_time) OR ("+endTimestamp+" > from_time AND "+endTimestamp+" <to_time) OR ("+startTimestamp+" < from_time AND "+endTimestamp+" > to_time) ) ) ORDER BY capacity ASC LIMIT 1;";
                ResultSet rs = stmt.executeQuery(sql);
                int roomID = rs.getInt("roomID");
                String roomName = rs.getString("name");
                int roomCapacity = rs.getInt("capacity");
                room = new Room(roomID, roomName, roomCapacity);
            }catch (SQLException e) {
                e.printStackTrace();
            }

        }else{

        }
        return room;
    }
}
