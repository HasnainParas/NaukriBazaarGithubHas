package com.appstacks.indiannaukribazaar.NewActivities.Models;

public class UserJobModel {

    private String jobTitle,jobPosition, jobLocation, employmentType, typeOfWorkPlace, description,uniqueKey,userAuthId;
    private String companyName, companyType,companyImage;



    public UserJobModel() {
    }

    public UserJobModel(String jobTitle, String jobPosition, String jobLocation, String employmentType, String typeOfWorkPlace, String description, String uniqueKey, String userAuthId, String companyName, String companyType, String companyImage) {
        this.jobTitle = jobTitle;
        this.jobPosition = jobPosition;
        this.jobLocation = jobLocation;
        this.employmentType = employmentType;
        this.typeOfWorkPlace = typeOfWorkPlace;
        this.description = description;
        this.uniqueKey = uniqueKey;
        this.userAuthId = userAuthId;
        this.companyName = companyName;
        this.companyType = companyType;
        this.companyImage = companyImage;
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

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public String getUserAuthId() {
        return userAuthId;
    }

    public void setUserAuthId(String userAuthId) {
        this.userAuthId = userAuthId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getCompanyImage() {
        return companyImage;
    }

    public void setCompanyImage(String companyImage) {
        this.companyImage = companyImage;
    }
}
