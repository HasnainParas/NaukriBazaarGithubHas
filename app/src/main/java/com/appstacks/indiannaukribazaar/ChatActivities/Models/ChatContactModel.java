package com.appstacks.indiannaukribazaar.ChatActivities.Models;

public class ChatContactModel {

    private String UserUUID, jobID;

    public ChatContactModel() {
    }


    public ChatContactModel(String userUUID, String jobID) {
        UserUUID = userUUID;
        this.jobID = jobID;
    }

    public String getUserUUID() {
        return UserUUID;
    }

    public void setUserUUID(String userUUID) {
        UserUUID = userUUID;
    }

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }
}


