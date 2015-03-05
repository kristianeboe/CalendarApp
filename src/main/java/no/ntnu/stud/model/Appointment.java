package no.ntnu.stud.model;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;

import no.ntnu.stud.util.InputValidator;

/**
 * Created by sklirg on 20/02/15.
 */
public class Appointment {
    private int appointmentID, ownerID, roomID;
    private String title, location, description;
    private LocalDate date;
    private LocalTime start, end;
    private LocalDateTime alarmTime;
    private int attending;

//Appointment(title, date, startTime, endTime, ownerID, description, location, roomID, attending);

    public Appointment(String title, LocalDate date, LocalTime startTime, LocalTime endTime, int ownerID, String description, String location, int roomID, int attending) {
        this.title = InputValidator.textInputValidator(title);
        this.description = InputValidator.textInputValidator(description);
        if (roomID != -1) {
            this.roomID = roomID;
        } else {
            this.location = InputValidator.textInputValidator(location);
        }
        this.ownerID = ownerID;
        setDateTime(date, startTime, endTime);
        setAttending(attending);
    }

    public Appointment(int appointmentID, String title, LocalDate date, LocalTime startTime, LocalTime endTime, int ownerID, String description, String location, int roomID, int attending, LocalDateTime alarmTime) {
        setDateTime(date, startTime, endTime);
        setAttending(attending);

        // appointmentID is something we get from database after appointment is created, and should be used to instantiate Appointments.
        setAppointmentID(appointmentID);

        // Do we need any validation for this?
        // We could implement a generic textInputValidator, which validates that the input is not empty, not longer than x chars +++
        setTitle(title);

        // Get from DB. Map to User-object?
        setOwnerID(ownerID);

        // Do we need any validation for this?
        setLocation(location);

        // Get from DB. Map to Room-object?
        setRoomID(roomID);

        // Do we need any validation for this?
        setDescription(description);

        // Should alarmTime be at appointment start, 15 minutes before, 60 minutes before…
        //User defined?
        setAlarmTime(alarmTime);
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

    public void setOwnerID(int ownerID){
        if(ownerID < 0){
            throw new IllegalArgumentException("ownerID cannot be a negative number");
        }else if(ownerID == 0){
            throw new IllegalArgumentException("ownerID cannot be 0");
        }
        this.ownerID = ownerID;
    }

    public void setAttending(int attending) {
        if (attending < 0)
            throw new IllegalArgumentException("Negative number of attendees is not allowed.");
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
        if (title.isEmpty()){
            throw new IllegalArgumentException("Title must be defined");
        } else if (title.length() > 40){
            throw new IllegalArgumentException("Title cannot be longer than 40 characters");
        }
        this.title = title;
    }

    public void setLocation(String location) {
        if(location.isEmpty()){
            throw new IllegalArgumentException("location cannot be empty");
        }else if(location.length()>100){
            throw new IllegalArgumentException("location cannot be longer than 100 characters");
        }
        this.location = location;
    }

    public void setDescription(String description) {
        if(description.isEmpty()){
            throw new IllegalArgumentException("description cannot be empty");
        }else if(description.length()>250){
            throw new IllegalArgumentException("description cannot be longer than 250 characters");
        }
        this.description = description;
    }

    public void setRoomID(int roomID){
        if(roomID < 0){
            throw new IllegalArgumentException("roomID cannot be a negative number");
        }else if(roomID == 0){
            throw new IllegalArgumentException("roomID cannot be 0");
        }
        this.roomID = roomID;
    }

    public void setAlarmTime(LocalDateTime alarmTime){
        this.alarmTime = alarmTime;
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

    public int getOwnerID() {
        return ownerID;
    }

    public int getRoomID() {
        return roomID;
    }

    public String getLocation() {
        return location;
    }

    public LocalDateTime getAlarmTime() {
        return alarmTime;
    }
}
