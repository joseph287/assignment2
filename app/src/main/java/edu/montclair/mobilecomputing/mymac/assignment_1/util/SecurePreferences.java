package edu.montclair.mobilecomputing.mymac.assignment_1.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class SecurePreferences {

    public static void savePreferences(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences("appData",
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void savePreferences(Context context, String key,
                                       boolean value) {
        SharedPreferences preferences = context.getSharedPreferences("appData",
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();

    }

    public static void savePreferences(Context context, String key, int value) {
        SharedPreferences preferences = context.getSharedPreferences("appData",
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();

    }

    public static String getStringPreference(Context context, String key) {
        String value = "";
        if (context != null) {
            SharedPreferences preferences = context.getSharedPreferences(
                    "appData", Context.MODE_PRIVATE);
            value = preferences.getString(key, "");
        }
        return value;
    }

    public static int getIntegerPreference(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences("appData",
                Context.MODE_PRIVATE);
        int value = preferences.getInt(key, 2);
        return value;
    }

    public static int getIntegerPreferencePrivacy(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences("appData",
                Context.MODE_PRIVATE);
        int value = preferences.getInt(key, 0);
        return value;
    }

    public static int getIntegerPreferenceLogin(Context context, String key, int defaultvalue) {
        SharedPreferences preferences = context.getSharedPreferences("appData",
                Context.MODE_PRIVATE);
        int value = preferences.getInt(key, defaultvalue);
        return value;
    }

    public static boolean getBooleanPreference(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences("appData",
                Context.MODE_PRIVATE);
        boolean value = preferences.getBoolean(key, false);
        return value;
    }

    public static boolean isOnline(final Context ctx) {
        final ConnectivityManager cm = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null) {
            return ni.isConnectedOrConnecting();
        } else {
            return false;
        }
    }

}
