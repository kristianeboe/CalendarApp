package no.ntnu.stud.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;

/**
 * Created by adrianh on 26.02.15.
 */
public class Group extends ArrayList<User> {

    private int groupID;
    private String name;

    public Group() {
        super();
    }

    public Group(int groupID, String name) {
        setGroupID(groupID);
        setName(name);
    }

    public int getGroupID() {
        return groupID;
    }

    public String toString() {
        return "(<Group> " + name + ")";
    }

    public void setGroupID(int groupID) {
        if(groupID < 0){
            throw new IllegalArgumentException("groupID cannot be a negative number");
        }else if(groupID == 0){
            throw new IllegalArgumentException("groupID cannot be 0");
        }
        this.groupID=groupID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name.isEmpty()){
            throw new IllegalArgumentException("Name cannot be empty");
        }else if(name.length()>45){
            throw new IllegalArgumentException("Name cannot be longer than 45 characters");
        }
        this.name=name;
    }
}
