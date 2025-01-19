package ru.romaaaka.mathemagic.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.romaaaka.mathemagic.database.GameDatabase;
import ru.romaaaka.mathemagic.database.GameRecord;
import ru.romaaaka.mathemagic.database.GameRecordDao;

public class GameRepository {

    private final GameRecordDao gameRecordDao;

    public GameRepository(Context context) {
        GameDatabase db = GameDatabase.getDatabase(context);
        gameRecordDao = db.gameRecordDao();
    }

    // Метод для добавления записи
    public void saveGameRecord(int errors, String difficulty, int score) {
        GameRecord record = new GameRecord();
        record.errors = errors;
        record.difficulty = difficulty;
        record.score = score;
        record.timestamp = System.currentTimeMillis();

        new Thread(() -> gameRecordDao.insert(record)).start();
    }

    // Метод для получения рекорда по уровню сложности
    public void getHighestScore(String difficulty, OnResultListener listener) {
        new Thread(() -> {
            int highScore = gameRecordDao.getHighestScore(difficulty);
            listener.onResult(highScore);
        }).start();
    }

    // Получаем все записи из базы данных через LiveData (автоматически обрабатывается в фоновом потоке)
    public LiveData<List<GameRecord>> getAllRecords() {
        return gameRecordDao.getAllRecords();
    }

    // Интерфейс для возврата результата
    public interface OnResultListener {
        void onResult(int result);
    }
}

