package com.appstacks.indiannaukribazaar.profile.Education;

public class Education {


    String levelOfEducation,instituteName,fieldOfStudy,startDate,endDate,description;
    boolean isPosition;

    public Education() {
    }

    public Education(String levelOfEducation, String instituteName, String fieldOfStudy, String startDate, String endDate, String description, boolean isPosition) {
        this.levelOfEducation = levelOfEducation;
        this.instituteName = instituteName;
        this.fieldOfStudy = fieldOfStudy;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.isPosition = isPosition;
    }

    public String getLevelOfEducation() {
        return levelOfEducation;
    }

    public void setLevelOfEducation(String levelOfEducation) {
        this.levelOfEducation = levelOfEducation;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
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

    public boolean isPosition() {
        return isPosition;
    }

    public void setPosition(boolean position) {
        isPosition = position;
    }
}