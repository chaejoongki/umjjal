package com.guers.umjjal.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by chae on 2017-11-28.
 */

public class SaveSharedPreference {

    public static final String PREF_PROVIDE_TARGET ="PROVIDE_TARGET";
    public static final String PREF_PROVIDE_KAKAO ="PROVIDE_KAKAO";
    public static final String PREF_PROVIDE_FACEBOOK ="PROVIDE_FACEBOOK";
    public static final String PREF_PROVIDE_GOOGLE ="PROVIDE_GOOGLE";
    public static final String PREF_USER_ID = "USER_ID";
    public static final String PREF_USER_NICK_NAME = "USER_NICK_NAME";
    public static final String PREF_USER_IMAGE_PATH = "USER_IMAGE_PATH";




    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserInfo(Context ctx, String provideTarget, String userId, String userNickName, String imagePath) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_PROVIDE_TARGET, provideTarget);
        editor.putString(PREF_USER_ID, userId);
        editor.putString(PREF_USER_NICK_NAME, userNickName);
        editor.putString(PREF_USER_IMAGE_PATH, imagePath);
        editor.commit();
    }

    public static String getUserId(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_ID, "");
    }

    public static String getUserNickName(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_NICK_NAME, "");
    }


    public static String getUserImagePath(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_IMAGE_PATH, "");
    }

    public static String getProvideTarget(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_PROVIDE_TARGET, "");
    }

    public static void clearUserName(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data editor.commit(); }


    }
}

