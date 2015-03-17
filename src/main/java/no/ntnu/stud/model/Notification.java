package no.ntnu.stud.model;

import no.ntnu.stud.util.InputValidator;

/**
 * Created by Kristoffer on 25/02/2015.
 */
public class Notification {
    private String message;
    private int notificationID, appointmentID;

    public Notification(int notificationID, String message, int appointmentID){
        setNotificationID(notificationID);
        setMessage(message);
        this.appointmentID = appointmentID;
    }

    public int getAppointmentID(){
        return appointmentID;
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

    private void setNotificationID(int notificationID){
        if(notificationID < 0){
            throw new IllegalArgumentException("NotificationID can not be negative");
        }else if(notificationID == 0){
            throw new IllegalArgumentException("NotificationID can not be 0");
        }
        this.notificationID = notificationID;
    }

    private void setMessage(String message){
        this.message = InputValidator.textInputValidator(message);
    }
}
