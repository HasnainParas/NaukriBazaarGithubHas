package com.appstacks.indiannaukribazaar.ChatActivities.Models;

public class ChatContactModel {

    private String UserUUID;

    public ChatContactModel() {
    }

    public ChatContactModel(String userUUID) {
        UserUUID = userUUID;
    }

    public String getUserUUID() {
        return UserUUID;
    }

    public void setUserUUID(String userUUID) {
        UserUUID = userUUID;
    }
}
