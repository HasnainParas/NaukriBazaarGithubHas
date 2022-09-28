package com.appstacks.indiannaukribazaar.NewActivities.Models;

import android.graphics.drawable.Drawable;

public class SelectedLanguages {

    String name;
    Drawable flag;
    String oralLevel;
    String writtenLevel;


    public SelectedLanguages(String name, Drawable flag, String oralLevel, String writtenLevel) {
        this.name = name;
        this.flag = flag;
        this.oralLevel = oralLevel;
        this.writtenLevel = writtenLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getFlag() {
        return flag;
    }

    public void setFlag(Drawable flag) {
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
}
