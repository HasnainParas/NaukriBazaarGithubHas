package com.appstacks.indiannaukribazaar.profile;


import android.content.Context;
import android.content.SharedPreferences;

import com.appstacks.indiannaukribazaar.ProfileModels.AddWorkExperience;
import com.appstacks.indiannaukribazaar.profile.Education.Education;
import com.facebook.share.Share;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ProfileUtils {


    private SharedPreferences sharedPreferences;
    private Context context;
    private static final String USER_BIO = "UserBio";
    private static final String WORK_EXPERIENCE = "WorkExperience";
    private static final String LEVEL_EDUCATION = "LevelEducation";
    private static final String INSTITUTE_NAME = "InstituteName";
    private static final String FIELD_OF_STUDY = "FieldStudy";
    private static final String EDUCATION = "Education";
    private static final String SKILLS = "Skills";
    private static final String LANGUAGES = "Languages";
    private static final String SELECTEDSKILLS = "SelectedSkill";

    public ProfileUtils(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(USER_BIO, Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(WORK_EXPERIENCE, Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(INSTITUTE_NAME, Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(FIELD_OF_STUDY, Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(EDUCATION, Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(SKILLS, Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(LANGUAGES,Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(SELECTEDSKILLS,Context.MODE_PRIVATE);
    }


    public void saveLanguages(ArrayList<String> list){
        Gson gson = new Gson();
        String jsonStr =gson.toJson(list);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString(LANGUAGES,jsonStr);
        editor.apply();

    }
    public ArrayList<String> fetchLanguages(){
        String str = sharedPreferences.getString(LANGUAGES, null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(str, type);
    }


    public void saveSkills(ArrayList<String> list) {

        Gson gson = new Gson();
        String jsonStr = gson.toJson(list);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SKILLS, jsonStr);
        editor.apply();

    }

    public ArrayList<String> fetchSkills() {
        String str = sharedPreferences.getString(SKILLS, null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(str, type);
    }

    public void saveUserBio(String bio) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_BIO, bio).apply();
    }

    public String fetchUserBio() {
        return sharedPreferences.getString(USER_BIO, "");
    }

    public void saveEducation(Education education) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(education, Education.class);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EDUCATION, jsonStr);
        editor.apply();

    }

    public Education fetchEducation() {
        String str = sharedPreferences.getString(EDUCATION, null);
        Gson gson = new Gson();
        return gson.fromJson(str, Education.class);
    }

    public void saveWorkExperience(AddWorkExperience workExperience) {

        Gson gson = new Gson();
        String jsonStr = gson.toJson(workExperience, AddWorkExperience.class);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(WORK_EXPERIENCE, jsonStr);
        editor.apply();


    }


    public AddWorkExperience fetchWorkExperience() {

        String str = sharedPreferences.getString(WORK_EXPERIENCE, null);
        Gson gson = new Gson();
        return gson.fromJson(str, AddWorkExperience.class);

    }

    public void saveLevelOfEducation(String levelOfEducation) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LEVEL_EDUCATION, levelOfEducation);
        editor.apply();
    }

    public String fetchLevelOfEducation() {

        return sharedPreferences.getString(LEVEL_EDUCATION, "");
    }

    public void saveInstituteName(String instituteName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(INSTITUTE_NAME, instituteName);
        editor.apply();
    }

    public String fetchInstituteName() {

        return sharedPreferences.getString(INSTITUTE_NAME, "");
    }

    public void saveFieldStudy(String field) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FIELD_OF_STUDY, field);
        editor.apply();
    }

    public String fetchFieldStudy() {

        return sharedPreferences.getString(FIELD_OF_STUDY, "");
    }

    public void saveSelectedSkills(ArrayList<String> list) {

        Gson gson = new Gson();
        String jsonStr = gson.toJson(list);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SELECTEDSKILLS, jsonStr);
        editor.apply();

    }

    public ArrayList<String> fetchSelectedSkills() {
        String str = sharedPreferences.getString(SELECTEDSKILLS, null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(str, type);
    }


    public void deleteSelectedSkills() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(SELECTEDSKILLS);
        editor.apply();
    }

}
