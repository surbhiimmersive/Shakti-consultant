package com.shakticonsultant.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    public static final String APP_PREF = "BluRadioAppPreferences";
    public static SharedPreferences sp;
    public static String LATITUDE = "LATITUDE";
    public static String LONGITUDE = "LONGITUDE";


    /* Used to check current state of player thorughout the application.
     * If radio player will be play it will be true
     * And it will be false when radio player will be at paused state
     */
    public static Boolean isPlay = true;
    public static String KEY_POSITION  = "keypositoonname";

    public static void setData(Context context, String value, String Key) {
        sp = context.getSharedPreferences(APP_PREF, 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(Key, value);
        edit.commit();
    }

    public static String getData(Context context, String key) {
        sp = context.getSharedPreferences(APP_PREF, 0);
        return sp.getString(key, "");
    }

    public static void setIntData(Context context, int value, String Key) {
        sp = context.getSharedPreferences(APP_PREF, 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(Key, value);
        edit.commit();
    }

    public static int getIntData(Context context, String key) {
        sp = context.getSharedPreferences(APP_PREF, 0);
        return sp.getInt(key, -1);
    }

    public static void setLongData(Context context, long value, String Key) {
        sp = context.getSharedPreferences(APP_PREF, 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putLong(Key, value);
        edit.commit();
    }

    public static long getLongData(Context context, String key) {
        sp = context.getSharedPreferences(APP_PREF, 0);
        return sp.getLong(key, 0);
    }



    public static void setBooleanData(Context context, Boolean value, String Key) {
        sp = context.getSharedPreferences(APP_PREF, 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(Key, value);
        edit.commit();
    }

    public static Boolean getBooleanData(Context context, String key) {
        sp = context.getSharedPreferences(APP_PREF, 0);
        return sp.getBoolean(key, false);
    }

    public static void clearPreference(Context context) {
        sp = context.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.clear();
        edit.apply();
    }

}
