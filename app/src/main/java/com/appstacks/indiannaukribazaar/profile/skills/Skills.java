package com.appstacks.indiannaukribazaar.profile.skills;

public class Skills {
    String index;
    String skill;

    public Skills(String index, String skill) {
        this.index = index;
        this.skill = skill;
    }

    public Skills() {
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }
}
