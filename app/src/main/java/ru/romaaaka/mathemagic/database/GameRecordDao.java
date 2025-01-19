package ru.romaaaka.mathemagic.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GameRecordDao {

    // Вставка новой записи
    @Insert
    void insert(GameRecord record);

    // Получить всю историю игр, отсортированную по времени
    @Query("SELECT * FROM game_history ORDER BY timestamp DESC")
    LiveData<List<GameRecord>> getAllRecords();

    // Получить максимальный рекорд для указанного уровня сложности
    @Query("SELECT MAX(score) FROM game_history WHERE difficulty = :difficulty")
    int getHighestScore(String difficulty);
}
