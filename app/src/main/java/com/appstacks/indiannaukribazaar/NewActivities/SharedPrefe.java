package com.appstacks.indiannaukribazaar.NewActivities;

import android.content.Context;
import android.content.SharedPreferences;

import com.appstacks.indiannaukribazaar.data.SharedPref;

public class SharedPrefe {

SharedPreferences sharedPreferences;
public static String JOB_POS="jobpos";
public static String JOBLOC="jobloc";
public static String WORKPL_TYPE = "workpaceT";
public static String DESC="desc";
public static String COMP="comp";
public static String EMPLOTYPE="employType";

    public SharedPrefe(Context context) {

        sharedPreferences=context.getSharedPreferences(JOB_POS,Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(JOBLOC,Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(WORKPL_TYPE,Context.MODE_PRIVATE);

    }

    public void saveJoPosition(String jobPos){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(JOB_POS,jobPos);
        editor.apply();

    }
    public String fetchJobPosition(){
        return sharedPreferences.getString(JOB_POS,null);
    }

    public void  saveWorkplace (String workplace){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(WORKPL_TYPE,workplace);
        editor.apply();

    }



    public String fetchWorkplace(){
        return sharedPreferences.getString(WORKPL_TYPE,null);
    }


    public void saveJobBLocation(String jobLoc){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(JOBLOC,jobLoc);
        editor.apply();
    }

    public String fetchJobLocation(){


        return sharedPreferences.getString(JOBLOC,null);

    }

}
