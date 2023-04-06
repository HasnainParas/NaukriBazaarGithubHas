package com.appstacks.indiannaukribazaar.NewActivities;

import android.content.Context;
import android.content.SharedPreferences;

import com.appstacks.indiannaukribazaar.data.SharedPref;

public class SharedPrefe {

    SharedPreferences sharedPreferences;
    public static String JOB_POS = "jobpos";
    public static String JOBLOC = "jobloc";
    public static String DESC = "desc";
    public static String TITLE = "title";
    public static String COMP = "COMP";
    public static String COMPTITLE = "COMPTITLE";
    public static String COMPIMAGEURL = "COMPIMAGEURL";
    public static String EMPLOTYPE = "employType";

    public static String INSTANTCOM = "INSTANTCOM";

    public static String INSTANTCOMTYPE = "INSTANTCOMTYPE";

    public static String INSTANTCOMIMAGE = "INSTANTCOMIMAGEURL";
    public static String INSTANTPOS = "INSTANTPOS";
    public static String INSTANTBUDGET = "INSTANTBUDGET";
    public static String INSTANTTIMEPERIOD = "INSTANTTIMEPERIOD";
    public static String INSTANTJOBTITLE = "INSTANTJOBTITLE";
    public static String INSTANTDESCRIPTION = "INSTANTDESCRIPTION";


    public SharedPrefe(Context context) {

        sharedPreferences = context.getSharedPreferences(JOB_POS, Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(JOBLOC, Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(COMP, Context.MODE_PRIVATE);

    }

    public void saveJoPosition(String jobPos) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(JOB_POS, jobPos);
        editor.apply();

    }

    public String fetchJobPosition() {
        return sharedPreferences.getString(JOB_POS, null);
    }

    public void saveCompany(String company) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(COMP, company);
        editor.apply();

    }

    public String fetchCompany() {
        return sharedPreferences.getString(COMP, null);
    }


    public void saveJobBLocation(String jobLoc) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(JOBLOC, jobLoc);
        editor.apply();
    }

    public String fetchJobLocation() {


        return sharedPreferences.getString(JOBLOC, null);

    }

    public void saveDescription(String description) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DESC, description);
        editor.apply();
    }

    public String fetchDescription() {

        return sharedPreferences.getString(DESC, null);
    }

    public void saveTitle(String title) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TITLE, title);
        editor.apply();
    }

    public String fetchTitle() {

        return sharedPreferences.getString(TITLE, null);
    }

    public void saveComTitle(String title) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(COMPTITLE, title);
        editor.apply();
    }

    public String fetchComTitle() {

        return sharedPreferences.getString(COMPTITLE, null);
    }

    public void saveComImageURl(String title) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(COMPIMAGEURL, title);
        editor.apply();
    }

    public String fetchComImageURl() {

        return sharedPreferences.getString(COMPIMAGEURL, null);
    }

    public void saveInstantCom(String title) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(INSTANTCOM, title);
        editor.apply();
    }

    public String fetchInstantCom() {

        return sharedPreferences.getString(INSTANTCOM, null);
    }
    public void saveInstantComType(String title) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(INSTANTCOMTYPE, title);
        editor.apply();
    }

    public String fetchInstantComType() {

        return sharedPreferences.getString(INSTANTCOMTYPE, null);
    }
    public void saveInstantComImgURL(String title) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(INSTANTCOMIMAGE, title);
        editor.apply();
    }

    public String fetchInstantComImgURL() {

        return sharedPreferences.getString(INSTANTCOMIMAGE, null);
    }

    public void saveInstantPos(String title) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(INSTANTPOS, title);
        editor.apply();
    }

    public String fetchInstantPos() {
        return sharedPreferences.getString(INSTANTPOS, null);
    }


    public void saveInstantBudget(String title) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(INSTANTBUDGET, title);
        editor.apply();
    }

    public String fetchInstantBudget() {
        return sharedPreferences.getString(INSTANTBUDGET, null);
    }

    public void saveInstantTimePeriod(String title) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(INSTANTTIMEPERIOD, title);
        editor.apply();
    }

    public String fetchInstantTimePeriod() {
        return sharedPreferences.getString(INSTANTTIMEPERIOD, null);
    }

    public void saveInstantJobTitle(String title) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(INSTANTJOBTITLE, title);
        editor.apply();
    }

    public String fetchInstantJobTitle() {
        return sharedPreferences.getString(INSTANTJOBTITLE, null);
    }

    public void saveInstantDescription(String title) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(INSTANTDESCRIPTION, title);
        editor.apply();
    }

    public String fetchInstantDescription() {
        return sharedPreferences.getString(INSTANTDESCRIPTION, null);
    }


    public void deleteAllsharedPre() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(JOB_POS);
        editor.remove(JOBLOC);
        editor.remove(DESC);
        editor.remove(TITLE);
        editor.remove(COMPTITLE);
        editor.remove(COMP);
        editor.remove(COMPIMAGEURL);
        editor.apply();
    }

    public void deleteCom() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(INSTANTCOM);
        editor.remove(INSTANTPOS);
        editor.remove(INSTANTBUDGET);
        editor.remove(INSTANTTIMEPERIOD);
        editor.remove(INSTANTCOMTYPE);
        editor.remove(INSTANTCOMIMAGE);
        editor.apply();
    }


}
