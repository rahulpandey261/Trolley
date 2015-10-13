package com.trolley.trolley.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Preference manager class, methods and keys for preferences, defined here
 * Created by mihir.shah on 10/13/2015.
 */

public class PreferenceUtils {

    public static final String NUMBER = "number", PASSWORD = "password";

    public static final String getNumber(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(NUMBER, "");
    }

    public static final boolean isSignedIn(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.contains(NUMBER);
    }

    public static final void updateProfile(Context context, String number, String password) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(NUMBER, number).putString(PASSWORD, password).apply();
    }
}
