package com.appstacks.indiannaukribazaar.NewActivities.Models;

public class UserJobModel {

    private String jobTitle,jobPosition, company, jobLocation, employmentType, typeOfWorkPlace, description;

    public UserJobModel() {
    }

    public UserJobModel(String jobTitle, String jobPosition, String company, String jobLocation, String employmentType, String typeOfWorkPlace, String description) {
        this.jobTitle = jobTitle;
        this.jobPosition = jobPosition;
        this.company = company;
        this.jobLocation = jobLocation;
        this.employmentType = employmentType;
        this.typeOfWorkPlace = typeOfWorkPlace;
        this.description = description;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getTypeOfWorkPlace() {
        return typeOfWorkPlace;
    }

    public void setTypeOfWorkPlace(String typeOfWorkPlace) {
        this.typeOfWorkPlace = typeOfWorkPlace;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
