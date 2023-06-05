package com.appstacks.indiannaukribazaar.ChatActivities.Models;

public class MessageModel {

    private String message, senderId, msgtime,msgID;
    private String offerDeliveryTime, offerBudget, offerDescription, offerStatus, offerJobID,jobTitle;

    public MessageModel() {
    }

    public MessageModel(String message, String senderId, String msgtime, String msgID) {
        this.message = message;
        this.senderId = senderId;
        this.msgtime = msgtime;
        this.msgID = msgID;
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

    public String getMsgID() {
        return msgID;
    }

    public void setMsgID(String msgID) {
        this.msgID = msgID;
    }

    public String getOfferDeliveryTime() {
        return offerDeliveryTime;
    }

    public void setOfferDeliveryTime(String offerDeliveryTime) {
        this.offerDeliveryTime = offerDeliveryTime;
    }

    public String getOfferBudget() {
        return offerBudget;
    }

    public void setOfferBudget(String offerBudget) {
        this.offerBudget = offerBudget;
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public void setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;
    }

    public String getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(String offerStatus) {
        this.offerStatus = offerStatus;
    }

    public String getOfferJobID() {
        return offerJobID;
    }

    public void setOfferJobID(String offerJobID) {
        this.offerJobID = offerJobID;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}
