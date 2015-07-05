package com.example.fleps.commentclient.Model;

/**
 * Created by Fleps_000 on 04.07.2015.
 */
public class Comment {
    private String name;
    private String surname;
    private String deviceID;
    private String comment;

    public Comment(String name, String surname, String deviceID, String comment) {
        this.name = name;
        this.surname = surname;
        this.deviceID = deviceID;
        this.comment = comment;
    }

    public Comment() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
