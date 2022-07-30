package com.appstacks.indiannaukribazaar.NewActivities;

import android.content.Context;
import android.content.SharedPreferences;

import com.appstacks.indiannaukribazaar.data.SharedPref;

public class SharedPrefe {

SharedPreferences sharedPreferences;
public static String JOB_POS="jobpos";
public static String JOBLOC="jobloc";
public static String DESC="desc";
public static String COMP="comp";
public static String EMPLOTYPE="employType";

    public SharedPrefe(Context context) {

        sharedPreferences=context.getSharedPreferences(JOB_POS,Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(JOBLOC,Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(COMP,Context.MODE_PRIVATE);

    }

    public void saveJoPosition(String jobPos){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(JOB_POS,jobPos);
        editor.apply();

    }
    public String fetchJobPosition(){
        return sharedPreferences.getString(JOB_POS,null);
    }

    public void  saveCompany (String company){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(COMP,company);
        editor.apply();

    }



    public String fetchCompany(){
        return sharedPreferences.getString(COMP,null);
    }


    public void saveJobBLocation(String jobLoc){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(JOBLOC,jobLoc);
        editor.apply();
    }

    public String fetchJobLocation(){


        return sharedPreferences.getString(JOBLOC,null);

    }

    public void saveDescription(String description){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DESC,description);
        editor.apply();
    }
    public String fetchDescription(){

        return sharedPreferences.getString(DESC,null);
    }


}
