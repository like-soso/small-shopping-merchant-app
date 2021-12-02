package com.sososhopping.merchant.utils.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.sososhopping.merchant.utils.Constant;

public class SharedPreferenceManager {

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(Constant.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static void setString(Context context, String key, String value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(Context context, String key) {
        SharedPreferences prefs = getPreferences(context);
        return prefs.getString(key, Constant.SHARED_PREFERENCE_DEFAULT_STRING);
    }
}
