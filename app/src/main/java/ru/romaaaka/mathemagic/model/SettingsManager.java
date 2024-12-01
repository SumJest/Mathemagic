package ru.romaaaka.mathemagic.model;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsManager {
    private SharedPreferences preferences;

    public SettingsManager(Context context) {
        preferences = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE);
    }

    public void saveSettings(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String loadSettings(String key, String defaultValue) {
        return preferences.getString(key, defaultValue);
    }

    public void saveDifficulty(int difficulty) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("difficulty", difficulty);
        editor.apply();
    }

    public int loadDifficulty() {
        return preferences.getInt("difficulty", 1); // По умолчанию уровень 1
    }
}
