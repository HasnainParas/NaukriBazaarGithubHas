package com.appstacks.indiannaukribazaar.ProfileModels;

public class AddWorkExperience {

    private String jobTitle, company, startDate, endDate, description,userId;
    private boolean positionNow;


    public AddWorkExperience() {

    }

    public AddWorkExperience(String jobTitle, String company, String startDate, String endDate, String description, String userId, boolean positionNow) {
        this.jobTitle = jobTitle;
        this.company = company;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.userId = userId;
        this.positionNow = positionNow;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isPositionNow() {
        return positionNow;
    }

    public void setPositionNow(boolean positionNow) {
        this.positionNow = positionNow;
    }
}
