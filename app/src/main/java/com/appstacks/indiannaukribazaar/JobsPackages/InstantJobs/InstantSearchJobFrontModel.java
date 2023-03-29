package com.appstacks.indiannaukribazaar.JobsPackages.InstantJobs;

public class InstantSearchJobFrontModel {

    String inJobTitle, inJobPosition, inJobBudget, inJobCompany;
    String inJobID, userAuthID;

    public InstantSearchJobFrontModel() {
    }

    public InstantSearchJobFrontModel(String inJobTitle, String inJobPosition, String inJobBudget, String inJobCompany, String inJobID, String userAuthID) {
        this.inJobTitle = inJobTitle;
        this.inJobPosition = inJobPosition;
        this.inJobBudget = inJobBudget;
        this.inJobCompany = inJobCompany;
        this.inJobID = inJobID;
        this.userAuthID = userAuthID;
    }

    public String getInJobTitle() {
        return inJobTitle;
    }

    public void setInJobTitle(String inJobTitle) {
        this.inJobTitle = inJobTitle;
    }

    public String getInJobPosition() {
        return inJobPosition;
    }

    public void setInJobPosition(String inJobPosition) {
        this.inJobPosition = inJobPosition;
    }

    public String getInJobBudget() {
        return inJobBudget;
    }

    public void setInJobBudget(String inJobBudget) {
        this.inJobBudget = inJobBudget;
    }

    public String getInJobCompany() {
        return inJobCompany;
    }

    public void setInJobCompany(String inJobCompany) {
        this.inJobCompany = inJobCompany;
    }

    public String getInJobID() {
        return inJobID;
    }

    public void setInJobID(String inJobID) {
        this.inJobID = inJobID;
    }

    public String getUserAuthID() {
        return userAuthID;
    }

    public void setUserAuthID(String userAuthID) {
        this.userAuthID = userAuthID;
    }
}
