package com.shakticonsultant.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class AppPrefrences
{
    public static final String SHARED_PREFERENCE_NAME = "KaaryApp";
    public static final String NAME = "name";
    public static final String LNAME = "lname";
    public static final String USERID = "user_id";
    public static final String Provider_id = "provider_id";
    public static final String MOBILE = "mobile";
    public static final String STATUS = "status";
    public static final String USERSTATUS = "user_status";
    public static final String MESSAGE = "message";
    public static final String MAIL = "mail";
    public static final String ADDRESS = "address";

    public static final String USER_TOKEN = "user_token";
    public static final String ServiceId = "serviceid";
    public static final String Main_ServiceID = "main_serviceid";
    public static final String PACKAGE = "package";
    public static final String TypeOFBooking = "Book Now";
    public static final String ScheduleDate = "ScheduleDate";
    public static final String ScheduleTime = "ScheduleTime";

    Context context;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    SharedPreferences.Editor editor2;
    public static final String LATITUDE = "latitude";
    public static final String SKILL_ID = "skill_id";
    public static final String Location = "location";
    public static final String LONGITUDE = "longitude";
    public static final String IS_LOGIN = "isLogin";
    public static final String IS_SIGNUP = "isSignUp";
    public static final String VIEWPAGER = "viewpager";
    public static final String KEY_NAME = "user_fullname";
    public static final String Profileimg = "AppPrefrences.getName(MainActivity.this)";


    public static String getName(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(NAME, "");
    }

    public static void setName(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(NAME, value);
        editor.commit();
    }
    public static String getSkillId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(SKILL_ID, "");
    }

    public static void setSkillId(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SKILL_ID, value);
        editor.commit();
    }
    public static String getProfileImg(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(Profileimg, "");
    }

    public static void setProfileImg(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Profileimg, value);
        editor.commit();
    }
    public static String getLocation(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(Location, "");
    }

    public static void setLocation(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Location, value);
        editor.commit();
    }
public static String getPACKAGE(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(PACKAGE, "");
    }

    public static void setPACKAGE(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PACKAGE, value);
        editor.commit();
    }

    public static String getTypeOFBooking(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(TypeOFBooking, "");
    }

    public static void setTypeOFBooking(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TypeOFBooking, value);
        editor.commit();
    }
    public static String getLatitude(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(LATITUDE, "");
    }

    public static void setLatitude(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LATITUDE, value);
        editor.commit();
    }public static String getLongitude(Context context) {
    SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
    return preferences.getString(LONGITUDE, "");
}

    public static void setLongitude(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LONGITUDE, value);
        editor.commit();
    }
    public static String getLastName(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(LNAME, "");
    }

    public static void setLastName(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LNAME, value);
        editor.commit();
    }


    public static String getUserid(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(USERID, "");
    }

    public static void setUserid(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USERID, value);
        editor.commit();
    }
    public static String getproviderid(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(Provider_id, "");
    }

    public static void setProviderid(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Provider_id, value);
        editor.commit();
    }
 public static String getServiceId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(ServiceId, "");
    }

    public static void setServiceId(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ServiceId, value);
        editor.commit();
    }
public static String getMainServiceId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(Main_ServiceID, "");
    }

    public static void setMainServiceId(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Main_ServiceID, value);
        editor.commit();
    }



    public static String getStatus(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(STATUS, "");
    }

    public static void setStatus(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(STATUS, value);
        editor.commit();
    }

    public static String getUserstatus(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(USERSTATUS, "");
    }

    public static void setUserstatus(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USERSTATUS, value);
        editor.commit();
    }
    public static String getMessage(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(MESSAGE, "");
    }

    public static void setMessage(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MESSAGE, value);
        editor.commit();
    }


    public static String getMail(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(MAIL, "");
    }

    public static void setMail(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MAIL, value);
        editor.commit();
    }

    public static String getAddress(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(ADDRESS, "");
    }

    public static void setAddress(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ADDRESS, value);
        editor.commit();
    }

    public static void setMobile(Context context, String headname) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MOBILE, headname);
        editor.commit();
    }

    public static String getMobile(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(MOBILE, "");
    }

    public static void setScheduleDate(Context context, String headname) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ScheduleDate, headname);
        editor.commit();
    }

    public static String getScheduleDate(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(ScheduleDate, "");
    }



    public static void setScheduleTime(Context context, String headname) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ScheduleTime, headname);
        editor.commit();
    }

    public static String getScheduleTime(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(ScheduleTime, "");
    }



    public static String getUserToken(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(USER_TOKEN, "");
    }

    public static void setUserToken(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_TOKEN, value);
        editor.commit();
    }


    public static void setLogin_status(Context context, Boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(IS_LOGIN,value);
        editor.commit();
    }
    public static Boolean getLogin_status(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getBoolean(IS_LOGIN, false);
    }

    public static void setSignupStatus(Context context, Boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(IS_SIGNUP,value);
        editor.commit();
    }
    public static Boolean getSignUpStatus(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getBoolean(IS_SIGNUP, false);
    }



    public static Boolean getViewPager(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getBoolean(VIEWPAGER, false);
    }

    public static void setViewPager(Context context, Boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(VIEWPAGER,value);
        editor.commit();
    }
    public void clear_Prefrences(){
        editor.clear();
        editor2.clear();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(IS_LOGIN, false);
    }

}
