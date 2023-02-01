package com.appstacks.indiannaukribazaar.profile.appreciation;

public class Appreciation {

    private String awardName;
    private String awardCategory;
    private String awardEndDate;
    private String awardDescription;

    public Appreciation() {
    }

    public Appreciation(String awardName, String awardCategory, String awardEndDate, String awardDescription) {
        this.awardName = awardName;
        this.awardCategory = awardCategory;
        this.awardEndDate = awardEndDate;
        this.awardDescription = awardDescription;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public String getAwardCategory() {
        return awardCategory;
    }

    public void setAwardCategory(String awardCategory) {
        this.awardCategory = awardCategory;
    }

    public String getAwardEndDate() {
        return awardEndDate;
    }

    public void setAwardEndDate(String awardEndDate) {
        this.awardEndDate = awardEndDate;
    }

    public String getAwardDescription() {
        return awardDescription;
    }

    public void setAwardDescription(String awardDescription) {
        this.awardDescription = awardDescription;
    }
}
