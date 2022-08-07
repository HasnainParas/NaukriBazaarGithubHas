package com.appstacks.indiannaukribazaar.NewActivities.Models;

public class CompanyModel {


    private int image;
    private String title;
    private String internet;

    public CompanyModel(int image, String title, String internet) {
        this.image = image;
        this.title = title;
        this.internet = internet;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInternet() {
        return internet;
    }

    public void setInternet(String internet) {
        this.internet = internet;
    }
}
