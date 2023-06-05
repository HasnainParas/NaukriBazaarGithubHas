package com.appstacks.indiannaukribazaar.JobsPackages.InstantJobs;

public class JobAppliedModel {

    private String information, appliedUserAuthID, jobID;
    private String userImage;

    public JobAppliedModel() {
    }

    public JobAppliedModel(String information, String appliedUserAuthID, String jobID, String userImage) {
        this.information = information;
        this.appliedUserAuthID = appliedUserAuthID;
        this.jobID = jobID;
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

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
}
