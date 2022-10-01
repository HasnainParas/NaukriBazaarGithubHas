package com.appstacks.indiannaukribazaar.ProfileModels;

import android.graphics.drawable.Drawable;

public class SelectedLanguages {

    String name;
    String flag;
    String oralLevel;
    String writtenLevel;
    String UUID;
    String userId;

    public SelectedLanguages() {
    }

    public SelectedLanguages(String name, String flag, String oralLevel, String writtenLevel, String UUID, String userId) {
        this.name = name;
        this.flag = flag;
        this.oralLevel = oralLevel;
        this.writtenLevel = writtenLevel;
        this.UUID = UUID;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getOralLevel() {
        return oralLevel;
    }

    public void setOralLevel(String oralLevel) {
        this.oralLevel = oralLevel;
    }

    public String getWrittenLevel() {
        return writtenLevel;
    }

    public void setWrittenLevel(String writtenLevel) {
        this.writtenLevel = writtenLevel;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

