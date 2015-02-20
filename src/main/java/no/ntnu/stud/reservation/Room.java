package no.ntnu.stud.reservation;

/**
 * Created by sklirg on 20/02/15.
 */
public class Room {
    private String name, location;
    private int size;
    public Room(String name, String location, int size) {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
