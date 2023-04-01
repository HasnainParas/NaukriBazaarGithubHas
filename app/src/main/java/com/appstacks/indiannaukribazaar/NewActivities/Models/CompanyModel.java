package com.appstacks.indiannaukribazaar.NewActivities.Models;

import android.net.Uri;

public class CompanyModel {



    private String title;
    private String type;

    private String imageUrl;

    private String pushId;
    private  int image;

    public CompanyModel() {
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public CompanyModel(int image , String title, String type) {
        this.title = title;
        this.type = type;
        this.image = image;
    }

    public CompanyModel(String title, String type, String imageUrl, String pushId) {
        this.title = title;
        this.type = type;
        this.imageUrl = imageUrl;
        this.pushId = pushId;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
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


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


}