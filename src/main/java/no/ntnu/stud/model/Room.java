package no.ntnu.stud.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Adrian on 23.02.2015.
 */
public class Room {

    private int roomID;
    private String name;
    private int capacity;

    public Room(int roomID, String name, int capacity) {
        setRoomID(roomID);
        setName(name);
        setCapacity(capacity);
    }

    public int getRoomID() {
        return roomID;
    }


    public void setRoomID(int roomID) {
        if (roomID < 0)
            throw new IllegalArgumentException("Negative Room ID not allowed");
        if(roomID == 0){
            throw new IllegalArgumentException("RoomID can not be 0");
        }
        this.roomID=roomID;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        if (name == "")
            throw new IllegalArgumentException("Empty room name is not allowed");
        this.name=name;
    }

    public int getCapacity() {
        return capacity;
    }


    public void setCapacity(int capacity) {
        if (capacity < 0)
            throw new IllegalArgumentException("Negative room capacity is not allowed");
        else if (capacity == 0)
            throw new IllegalArgumentException("No room capacity is not allowed");
        this.capacity=capacity;
    }
}
