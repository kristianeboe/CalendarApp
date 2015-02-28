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
    private ArrayList<User> group;

    public Group() {
        super();
    }

    public Group(int groupID, String name) {
        this.groupID = groupID;
        this.name = name;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID=groupID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }
}
