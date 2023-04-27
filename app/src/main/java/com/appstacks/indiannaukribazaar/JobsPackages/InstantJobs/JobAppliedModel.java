package com.appstacks.indiannaukribazaar.JobsPackages.InstantJobs;

public class JobAppliedModel {

    String information, appliedUserAuthID;

    public JobAppliedModel() {
    }



    public JobAppliedModel(String information, String appliedUserAuthID) {
        this.information = information;
        this.appliedUserAuthID = appliedUserAuthID;
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
