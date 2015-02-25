package no.ntnu.stud.reservation;

/**
 * Created by sklirg on 20/02/15.
 */
public class Room {
    private String name;
    private int size;
    public Room(String name, int size) {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
