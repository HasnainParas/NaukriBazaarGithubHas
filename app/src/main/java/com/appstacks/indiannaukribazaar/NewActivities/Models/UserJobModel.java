package com.appstacks.indiannaukribazaar.NewActivities.Models;

public class UserJobModel {

    private String jobTitle,jobPosition, jobLocation, employmentType, typeOfWorkPlace, description,uniqueKey,userAuthId;
    private String companyName, companyType,companyImage;
    private String jobPostedDate;
    private String salary,qualifications,eligibility,experience,specialization,facilities;




    public UserJobModel() {
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public String getEligibility() {
        return eligibility;
    }

    public void setEligibility(String eligibility) {
        this.eligibility = eligibility;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public UserJobModel(String jobTitle, String jobPosition, String jobLocation, String employmentType, String typeOfWorkPlace, String description, String uniqueKey, String userAuthId, String companyName, String companyType, String companyImage, String jobPostedDate, String salary, String qualifications, String eligibility, String experience, String specialization, String facilities) {
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
        this.jobPostedDate = jobPostedDate;
        this.salary = salary;
        this.qualifications = qualifications;
        this.eligibility = eligibility;
        this.experience = experience;
        this.specialization = specialization;
        this.facilities = facilities;
    }

    public String getJobPostedDate() {
        return jobPostedDate;
    }

    public void setJobPostedDate(String jobPostedDate) {
        this.jobPostedDate = jobPostedDate;
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
