package com.ibraheem.ibraheem.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by anirban on 04/04/2016.
 */
public class ReusableClass {
    public static double homeLat = 3.141667721099972;
    public static double homeLng = 101.61552473902702;
    public static double officeLat = 3.146689292510596;
    public static double officeLng = 101.6168712079525;

//    public static double homeLat = 28.44186041379514;
//    public static double homeLng = 48.49154680967331;
//    public static double officeLat = 28.3986938105104;
//    public static double officeLng = 48.526309579610825;

    //===================================================================================================================================
    //Preference variable
    //===================================================================================================================================

    //--------------------------------------------
    // method to save variable in preference
    //--------------------------------------------
    public static void saveInPreference(String name, String content, Context myActivity) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(myActivity);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(name, content);
        editor.commit();
    }

    //--------------------------------------------
    // getting content from preferences
    //--------------------------------------------
    public static String getFromPreference(String variable_name, Context myActivity) {
        String preference_return;
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(myActivity);
        preference_return = preferences.getString(variable_name, "");

        return preference_return;
    }


    //===================================================================================================================================
    //Preference variable
    //===================================================================================================================================

}
