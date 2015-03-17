package no.ntnu.stud.model;

import no.ntnu.stud.util.InputValidator;

/**
 * Created by Kristoffer on 25/02/2015.
 */
public class Notification {
    private String message;
    Appointment appointment;
    int notificationID;

    public Notification(Appointment appointment, String message) {
        setAppointment(appointment);
        setMessage(message);
        notificationID = -1;
    }

    public Notification(int notificationID, Appointment appointment, String message){
        this(appointment, message);
        this.notificationID = notificationID;
    }

    public String toString() {
        return "(<Notification> " + message + ")";
    }

    public String getMessage(){
        return message;
    }

    public int getNotificationID(){
        return notificationID;
    }

    public void setNotificationID(int notificationID){
        if(notificationID < 0){
            throw new IllegalArgumentException("NotificationID can not be negative");
        }else if(notificationID == 0){
            throw new IllegalArgumentException("NotificationID can not be 0");
        }
        this.notificationID = notificationID;
    }

    public void setAppointment(Appointment appointment) {
        if (appointment.getAppointmentID() != -1) {
            this.appointment = appointment;
        } else {
            throw new IllegalArgumentException("Appointment har no ID (does not exist in database - probably not synced yet)");
        }
    }

    public Appointment getAppointment() {
        return this.appointment;
    }

    private void setMessage(String message){
        this.message = InputValidator.textInputValidator(message);
    }
}
