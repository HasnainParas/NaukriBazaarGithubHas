package com.appstacks.indiannaukribazaar.profile.Lanuages;


public class SelectedLanguages {

    private String name;
    private String flag;
    private int drawable;
    private String oralLevel;
    private String writtenLevel;
    private String UUID;
    private String userId;
private boolean firstLanguage;
    public SelectedLanguages() {
    }


    public SelectedLanguages(String name, int drawable) {
        this.name = name;
        this.drawable = drawable;
    }

    public SelectedLanguages(String name, String flag, String oralLevel, String writtenLevel, String UUID, String userId,boolean firstLanguage) {
        this.name = name;
        this.flag = flag;
        this.oralLevel = oralLevel;
        this.writtenLevel = writtenLevel;
        this.UUID = UUID;
        this.userId = userId;
        this.firstLanguage=firstLanguage;
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

    public boolean isFirstLanguage() {
        return firstLanguage;
    }

    public void setFirstLanguage(boolean firstLanguage) {
        this.firstLanguage = firstLanguage;
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

    public int getDrawable() {
        return drawable;
    }
}

