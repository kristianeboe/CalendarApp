package no.ntnu.stud.model;

import no.ntnu.stud.util.InputValidator;

/**
 * Created by Kristoffer on 25/02/2015.
 */
public class Notification {
    private String message;
    int notificationID;

    public Notification(int notificationID, String message){
        setNotificationID(notificationID);
        setMessage(message);
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
