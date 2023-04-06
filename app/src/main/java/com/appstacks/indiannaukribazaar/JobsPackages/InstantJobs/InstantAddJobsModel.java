package com.appstacks.indiannaukribazaar.JobsPackages.InstantJobs;

import java.util.ArrayList;
import java.util.List;

public class InstantAddJobsModel {

    String inJobTitle, inJobDes, inJobPosition, inJobBudget, inJobCompany, inJobCompanyType, inJobCompanyImgURL, inJobTimePeriod, inJobID, userAuthID;
    String PostedDate;


    public InstantAddJobsModel() {
    }

    public InstantAddJobsModel(String inJobTitle, String inJobDes, String inJobPosition, String inJobBudget, String inJobCompany, String inJobCompanyType, String inJobCompanyImgURL, String inJobTimePeriod, String inJobID, String userAuthID, String postedDate) {
        this.inJobTitle = inJobTitle;
        this.inJobDes = inJobDes;
        this.inJobPosition = inJobPosition;
        this.inJobBudget = inJobBudget;
        this.inJobCompany = inJobCompany;
        this.inJobCompanyType = inJobCompanyType;
        this.inJobCompanyImgURL = inJobCompanyImgURL;
        this.inJobTimePeriod = inJobTimePeriod;
        this.inJobID = inJobID;
        this.userAuthID = userAuthID;
        PostedDate = postedDate;
    }

    public String getInJobTitle() {
        return inJobTitle;
    }

    public void setInJobTitle(String inJobTitle) {
        this.inJobTitle = inJobTitle;
    }

    public String getInJobDes() {
        return inJobDes;
    }

    public void setInJobDes(String inJobDes) {
        this.inJobDes = inJobDes;
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

    public String getInJobCompanyType() {
        return inJobCompanyType;
    }

    public void setInJobCompanyType(String inJobCompanyType) {
        this.inJobCompanyType = inJobCompanyType;
    }

    public String getInJobCompanyImgURL() {
        return inJobCompanyImgURL;
    }

    public void setInJobCompanyImgURL(String inJobCompanyImgURL) {
        this.inJobCompanyImgURL = inJobCompanyImgURL;
    }

    public String getInJobTimePeriod() {
        return inJobTimePeriod;
    }

    public void setInJobTimePeriod(String inJobTimePeriod) {
        this.inJobTimePeriod = inJobTimePeriod;
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

    public String getPostedDate() {
        return PostedDate;
    }

    public void setPostedDate(String postedDate) {
        PostedDate = postedDate;
    }
}
