package no.ntnu.stud.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Adrian on 23.02.2015.
 */
public class Room {

    private IntegerProperty roomID = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private IntegerProperty capacity = new SimpleIntegerProperty();

    public Room(IntegerProperty roomID, StringProperty name, IntegerProperty capacity) {
        setRoomID(roomID.get());
        setName(name.get());
        setCapacity(capacity.get());
    }

    public Room(int roomID, String name, int capacity) {
        setRoomID(roomID);
        setName(name);
        setCapacity(capacity);
    }

    public int getRoomID() {
        return roomID.get();
    }

    public IntegerProperty roomIDProperty() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        if (roomID < 0)
            throw new IllegalArgumentException("Negative Room ID not allowed");
        if(roomID == 0){
            throw new IllegalArgumentException("RoomID can not be 0");
        }
        this.roomID.set(roomID);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        if (name == "")
            throw new IllegalArgumentException("Empty room name is not allowed");
        this.name.set(name);
    }

    public int getCapacity() {
        return capacity.get();
    }

    public IntegerProperty capacityProperty() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        if (capacity < 0)
            throw new IllegalArgumentException("Negative room capacity is not allowed");
        else if (capacity == 0)
            throw new IllegalArgumentException("No room capacity is not allowed");
        this.capacity.set(capacity);
    }
}
