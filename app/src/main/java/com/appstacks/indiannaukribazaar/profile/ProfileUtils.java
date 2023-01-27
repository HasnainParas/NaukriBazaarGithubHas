package com.appstacks.indiannaukribazaar.profile;


import android.content.Context;
import android.content.SharedPreferences;

import com.appstacks.indiannaukribazaar.ProfileModels.AddWorkExperience;
import com.google.gson.Gson;

public class ProfileUtils {


    private SharedPreferences sharedPreferences;
    private Context context;
    private static final String USER_BIO = "UserBio";
    private static final String WORK_EXPERIENCE = "WorkExperience";

    public ProfileUtils(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(USER_BIO, Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(WORK_EXPERIENCE, Context.MODE_PRIVATE);
    }

    public void saveUserBio(String bio) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_BIO, bio).apply();
    }

    public String fetchUserBio() {
        return sharedPreferences.getString(USER_BIO, "");
    }

    public void saveWorkExperience(AddWorkExperience workExperience) {

        Gson gson = new Gson();
        String jsonStr = gson.toJson(workExperience, AddWorkExperience.class);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(WORK_EXPERIENCE, jsonStr);
        editor.apply();


    }

    public AddWorkExperience fetchWorkExperience() {

        String str = sharedPreferences.getString(WORK_EXPERIENCE, "N/A");
        Gson gson = new Gson();
        return gson.fromJson(str, AddWorkExperience.class);

    }
}
