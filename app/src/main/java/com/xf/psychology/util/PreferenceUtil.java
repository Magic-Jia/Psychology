package com.xf.psychology.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.xf.psychology.App;


public class PreferenceUtil {
    private PreferenceUtil() {
    }

    private static PreferenceUtil instance = new PreferenceUtil();
    private static SharedPreferences preferences;

    public static PreferenceUtil getInstance() {
        preferences = App.getContext().getSharedPreferences("simpleDate", Context.MODE_PRIVATE);
        return instance;
    }

    public void save(String key, boolean value) {
        preferences.edit().putBoolean(key, value).apply();
    }

    public void save(String key, String value) {
        preferences.edit().putString(key, value).commit();
    }

    public void save(String key, int value) {
        preferences.edit().putInt(key, value).apply();
    }

    public boolean get(String key, boolean defValue) {
        return preferences.getBoolean(key, defValue);
    }

    public String get(String key, String value) {
        return preferences.getString(key, value);
    }

    public int get(String key, int defValue) {
        return preferences.getInt(key, defValue);
    }

    public void remove(String key) {
        preferences.edit().remove(key).apply();
    }

    public <T> void save(String key, T value) {
        String s = new Gson().toJson(value);
        save(key, s);
    }

    public <T> T get(String key, Class<T> tClass) {
        String s = get(key, "");
        if (s.isEmpty()) {
            return null;
        }
        return new Gson().fromJson(s, tClass);
    }
}
