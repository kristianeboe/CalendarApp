package no.ntnu.stud.reservation;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import no.ntnu.stud.jdbc.GetData;
import no.ntnu.stud.jdbc.StoreData;

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
}
