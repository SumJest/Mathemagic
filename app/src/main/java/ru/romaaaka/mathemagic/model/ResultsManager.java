package ru.romaaaka.mathemagic.model;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResultsManager {
    private SharedPreferences preferences;

    public ResultsManager(Context context) {
        preferences = context.getSharedPreferences("GameResults", Context.MODE_PRIVATE);
    }

    public void saveResult(String playerName, int score) {
        SharedPreferences.Editor editor = preferences.edit();
        String result = playerName + ":" + score;
        editor.putString("result_" + System.currentTimeMillis(), result);
        editor.apply();
    }

    public List<String> getTopResults() {
        List<String> results = new ArrayList<>();
        for (String key : preferences.getAll().keySet()) {
            if (key.startsWith("result_")) {
                results.add(preferences.getString(key, ""));
            }
        }
        Collections.sort(results, (a, b) -> {
            int scoreA = Integer.parseInt(a.split(":")[1]);
            int scoreB = Integer.parseInt(b.split(":")[1]);
            return Integer.compare(scoreB, scoreA); // Сортировка по убыванию
        });
        return results;
    }
}

