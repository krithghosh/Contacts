package com.gojek.krith.contacts.application;

import android.content.SharedPreferences;

import java.util.Set;

import javax.inject.Inject;

/**
 * Created by krith on 01/02/17.
 */

public class SharedPreferenceManager {

    public static SharedPreferences sharedPreferences = null;

    public static final String IS_CONTACTS_FETCHED_FROM_SERVER = "is_contact_fetched_from_server";

    @Inject
    public SharedPreferenceManager(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public static void putString(String key, String value) {
        sharedPreferences.edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    public static void putInt(String key, int value) {
        sharedPreferences.edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    public static void putLong(String key, long value) {
        sharedPreferences.edit().putLong(key, value).commit();
    }

    public static long getLong(String key) {
        return sharedPreferences.getLong(key, 0);
    }

    public static void putFloat(String key, float value) {
        sharedPreferences.edit().putFloat(key, value).commit();
    }

    public static float getFloat(String key) {
        return sharedPreferences.getLong(key, 0);
    }

    public static void putBoolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public static void putStringSet(String key, Set<String> value) {
        sharedPreferences.edit().putStringSet(key, value).commit();
    }

    public static Set<String> getStringSet(String key) {
        return sharedPreferences.getStringSet(key, null);
    }

    public static void removeValue(String key) {
        sharedPreferences.edit().remove(key).commit();
    }

    public static void removeAll() {
        sharedPreferences.edit().clear().commit();
    }
}
