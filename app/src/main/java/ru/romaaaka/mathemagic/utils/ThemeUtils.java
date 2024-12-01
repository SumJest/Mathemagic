package ru.romaaaka.mathemagic.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ThemeUtils {
    private static final String PREF_NAME = "ThemePrefs";
    private static final String KEY_THEME = "app_theme";

    public static void applyTheme(Context context, int themeResId) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_THEME, themeResId);
        editor.apply();
    }

    public static int getTheme(Context context, int defaultThemeResId) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_THEME, defaultThemeResId);
    }
}
