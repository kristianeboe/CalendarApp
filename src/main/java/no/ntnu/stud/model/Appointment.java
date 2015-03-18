package no.ntnu.stud.model;

import no.ntnu.stud.jdbc.InsertData;
import no.ntnu.stud.util.InputValidator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by sklirg on 20/02/15.
 */
public class Appointment {
    private int appointmentID, roomID;
    private int owner;
    private String title, location, description;
    private LocalDate date;
    private LocalTime start, end;
    private int attending;

    private Appointment(String title, LocalDate date, LocalTime startTime, LocalTime endTime, int owner, String description) {
        setTitle(InputValidator.textInputValidator(title));
        setDateTime(date, startTime, endTime);
        setDescription(description);
        this.owner = owner;
    }

    public Appointment(String title, LocalDate date, LocalTime startTime, LocalTime endTime, int owner, String description, String location, int roomID, int attending) {
        this(title, date, startTime, endTime, owner, description);
        if (roomID != -1) {
            this.roomID = roomID;
        } else {
            setLocation(InputValidator.textInputValidator(location));
        }
        setAttending(attending);
        this.appointmentID = -1;
    }

    public Appointment(String title, LocalDate date, LocalTime startTime, LocalTime endTime, int owner, String description, int roomID, int attending) {
        this(title, date, startTime, endTime, owner, description);
        this.roomID = roomID;
        this.attending = attending;
    }

    public Appointment(String title, LocalDate date, LocalTime startTime, LocalTime endTime, int owner, String description, String location) {
        this(title, date, startTime, endTime, owner, description);
        setLocation(location);
    }

    public Appointment(int appointmentID, String title, LocalDate date, LocalTime startTime, LocalTime endTime, int owner, String description, int roomID, int attending) {
        this(title, date, startTime, endTime, owner, description);
        this.appointmentID = appointmentID;
        this.roomID = roomID;
        this.attending = attending;
    }

    public Appointment(int appointmentID, String title, LocalDate date, LocalTime startTime, LocalTime endTime, int owner, String description, String location) {
        this(title, date, startTime, endTime, owner, description);
        this.appointmentID = appointmentID;
        setLocation(location);
    }

    public Appointment(int appointmentID, String title, LocalDate date, LocalTime startTime, LocalTime endTime, int owner, String description, String location, int roomID, int attending) {
        setDateTime(date, startTime, endTime);
        setAttending(attending);

        // appointmentID is something we get from database after appointment is created, and should be used to instantiate Appointments.
        setAppointmentID(appointmentID);

        // Do we need any validation for this?
        // We could implement a generic textInputValidator, which validates that the input is not empty, not longer than x chars +++
        setTitle(title);

        // Get from DB. Map to User-object?
        setOwner(owner);

        // Get from DB. Map to Room-object?
        if (roomID != -1) {
            setRoomID(roomID);
        } else {
            // Do we need any validation for this?
            setLocation(location);
        }

        // Do we need any validation for this?
        setDescription(description);

        // Should alarmTime be at appointment start, 15 minutes before, 60 minutes before…
        //User defined?
    }

    public Appointment(String title, LocalDate date, LocalTime startTime, LocalTime endTime, User owner, String description, String location, int roomID, int attending) {
        this(title, date, startTime, endTime, owner.getUserID(), description, location, roomID, attending);
    }

    public String toString() {
        return "(<Appointment> " + title + ")";
    }

    public void setAppointmentID(int appointmentID){
        if(appointmentID < 0){
            throw new IllegalArgumentException("appointmentID cannot be a negative number");
        }else if(appointmentID == 0){
            throw new IllegalArgumentException("appointmentId cannot be 0");
        }
        this.appointmentID = appointmentID;
    }

    public void setOwner(int owner){
        this.owner = owner;
    }

    public void setAttending(int attending) {
        if (attending == -1)
            this.attending = -1;
        else if (attending < 0)
            throw new IllegalArgumentException("Negative number of attendees is not allowed.");
        else
            this.attending = attending;
    }

    public void setDateTime(LocalDate date, LocalTime start, LocalTime end) {
        if (end.compareTo(start) == -1)
            throw new IllegalArgumentException("End time occurs before start time");
        else if (start.equals(end))
            throw new IllegalArgumentException("Start time same as end time");
        this.date = date;
        this.start = start;
        this.end = end;
    }

    public void setTitle(String title) {
        this.title = InputValidator.textInputValidator(title, 40);
    }

    public void setLocation(String location) {
        this.location = InputValidator.textInputValidator(location, 100);
    }

    public void setDescription(String description) {
        this.description = InputValidator.textInputValidator(description, 250);
    }

    public void setRoomID(int roomID){
        if(roomID < 0){
            throw new IllegalArgumentException("roomID cannot be a negative number");
        }else if(roomID == 0){
            throw new IllegalArgumentException("roomID cannot be 0");
        }
        this.roomID = roomID;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public LocalDate getDate() { return date; }

    public String getTitle(){ return title.toString();
    }

    public LocalDateTime getDateTimeStart() {
        return LocalDateTime.of(date, start);
    }

    public LocalDateTime getDateTimeEnd() {
        return LocalDateTime.of(date, end);
    }

    public int getAttending() {
        return attending;
    }

    public String getDescription(){ return description; }

    public int getAppointmentID() {
        return appointmentID;
    }

    public int getOwner() {
        return owner;
    }

    public int getRoomID() {
        return roomID;
    }

    public String getLocation() {
        return location;
    }

    public Appointment create() {
        return InsertData.createAppointment(this);
    }

    public void inviteUser(User user) {
        InsertData.inviteUser(user, this);
    }
}
