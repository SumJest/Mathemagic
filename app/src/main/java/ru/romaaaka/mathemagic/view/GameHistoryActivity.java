package ru.romaaaka.mathemagic.view;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.romaaaka.mathemagic.R;
import ru.romaaaka.mathemagic.adapter.GameHistoryAdapter;
import ru.romaaaka.mathemagic.viewmodel.GameViewModel;

public class GameHistoryActivity extends AppCompatActivity {

    private GameViewModel gameViewModel;
    private RecyclerView recyclerView;
    private GameHistoryAdapter gameHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_history);

        // Инициализация GameViewModel
        gameViewModel = new GameViewModel(getApplication());

        // Инициализация RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Инициализация адаптера
        gameHistoryAdapter = new GameHistoryAdapter();
        recyclerView.setAdapter(gameHistoryAdapter);

        // Наблюдаем за данными из LiveData
        gameViewModel.getAllGameRecords().observe(this, gameRecords -> {
            // Когда данные изменятся, обновим UI
            if (gameRecords != null) {
                gameHistoryAdapter.setGameHistory(gameRecords);
            }
        });

        // Кнопка для закрытия экрана истории игр
        findViewById(R.id.btnCloseHistory).setOnClickListener(v -> finish());
    }
}
