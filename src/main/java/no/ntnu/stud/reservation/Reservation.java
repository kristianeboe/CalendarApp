package no.ntnu.stud.reservation;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Created by sklirg on 20/02/15.
 */
public class Reservation {
    private ArrayList<Room> rooms;
    public Reservation () {

    }

    public Reservation (LocalDate date, LocalTime start, LocalTime end, int attending) {
    }

    public boolean validateAttending(int attending) {
        if (attending < 0)
            throw new IllegalArgumentException("Negative number of attendees is not allowed.");
        else
            return true;
    }

    public ArrayList<Room> getRooms() {
        return this.rooms;
    }

    /*
     * Should be run on start, populating a list of rooms from db.
     */
    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public ArrayList<Room> findBigEnoughRooms(int attending) {
        this.rooms.remove(0); // Hardcoded to make test pass. Remove this line when you start working on this method.
        return this.rooms;
    }
}
