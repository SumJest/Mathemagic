package ru.romaaaka.mathemagic.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "game_history")
public class GameRecord {
    @PrimaryKey(autoGenerate = true)
    public int id; // Уникальный идентификатор

    public int errors; // Количество ошибок
    public String difficulty; // Уровень сложности (easy, medium, hard)
    public int score; // Набранные баллы
    public long timestamp; // Время игры (для сортировки истории)
}
