package ru.romaaaka.mathemagic.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {GameRecord.class}, version = 1)
public abstract class GameDatabase extends RoomDatabase {

    public abstract GameRecordDao gameRecordDao();

    private static volatile GameDatabase INSTANCE;

    public static GameDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (GameDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            GameDatabase.class,
                            "game_database"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
