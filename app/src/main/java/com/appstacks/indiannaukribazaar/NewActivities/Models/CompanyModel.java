package com.appstacks.indiannaukribazaar.NewActivities.Models;

import android.net.Uri;

public class CompanyModel {


    private int image;
    private String title;
    private String type;
    private Uri filepath;

    public CompanyModel() {
    }

    public CompanyModel(int image, String title, String type) {
        this.image = image;
        this.title = title;
        this.type = type;
    }

    public CompanyModel(String title, String type, Uri filepath) {
        this.title = title;
        this.type = type;
        this.filepath = filepath;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Uri getFilepath() {
        return filepath;
    }

    public void setFilepath(Uri filepath) {
        this.filepath = filepath;
    }
}
