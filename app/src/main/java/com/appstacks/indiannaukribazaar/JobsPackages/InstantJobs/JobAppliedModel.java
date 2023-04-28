package com.appstacks.indiannaukribazaar.JobsPackages.InstantJobs;

public class JobAppliedModel {

   private String information, appliedUserAuthID;
   private String userImage;

    public JobAppliedModel() {
    }


    public JobAppliedModel(String information, String appliedUserAuthID, String userImage) {
        this.information = information;
        this.appliedUserAuthID = appliedUserAuthID;
        this.userImage = userImage;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getAppliedUserAuthID() {
        return appliedUserAuthID;
    }

    public void setAppliedUserAuthID(String appliedUserAuthID) {
        this.appliedUserAuthID = appliedUserAuthID;
    }
}
