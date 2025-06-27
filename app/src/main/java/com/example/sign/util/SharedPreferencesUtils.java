package com.example.sign.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.collection.SimpleArrayMap;

import com.example.sign.MyApplication;


public final class SharedPreferencesUtils {
    private static final SimpleArrayMap<String, SharedPreferencesUtils> mCaches = new SimpleArrayMap<>();
    private static SharedPreferencesUtils utils;
    private SharedPreferences mSharedPreferences;
    private SharedPreferencesUtils(final String spName, final int mode) {
        mSharedPreferences = MyApplication.getInstance().getSharedPreferences(spName, mode);
    }

    public static SharedPreferencesUtils getInstance(String spName) {
        if (utils == null) {
            utils = new SharedPreferencesUtils(spName, Context.MODE_PRIVATE);
        }
        return utils;
    }

    public void put(final String key, final String value) {
        mSharedPreferences.edit().putString(key, value).apply();
    }
    public void put(final String key, final int value) {
        mSharedPreferences.edit().putInt(key, value).apply();
    }
    public void put(final String key, final boolean value) {
        mSharedPreferences.edit().putBoolean(key, value).apply();
    }

    public String get(final String key) {
        return mSharedPreferences.getString(key, "");
    }
    public int getInt(final String key) {
        return mSharedPreferences.getInt(key, 0);
    }
    public int getNumInt(final String key) {
        return mSharedPreferences.getInt(key, 1);
    }
    public Boolean getBoolean(final String key) {
        return mSharedPreferences.getBoolean(key, false);
    }

    public void remove(final String key) {
        mSharedPreferences.edit().remove(key).apply();
    }
}
