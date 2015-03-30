package no.ntnu.stud.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import no.ntnu.stud.util.InputValidator;

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
        this.roomID=roomID;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = InputValidator.textInputValidator(name, 45);
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

    public String toString(){
        return "Room: "+ this.name + " - Capacity: "+this.capacity;
    }
}
