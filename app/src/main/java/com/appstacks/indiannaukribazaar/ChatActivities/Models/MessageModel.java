package com.appstacks.indiannaukribazaar.ChatActivities.Models;

public class MessageModel {

    private String message,senderId,msgtime;
    public MessageModel() {
    }

    public MessageModel(String message, String senderId, String msgtime) {
        this.message = message;
        this.senderId = senderId;
        this.msgtime = msgtime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMsgtime() {
        return msgtime;
    }

    public void setMsgtime(String msgtime) {
        this.msgtime = msgtime;
    }
}
