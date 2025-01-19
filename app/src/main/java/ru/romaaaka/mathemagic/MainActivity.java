package ru.romaaaka.mathemagic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ru.romaaaka.mathemagic.view.GameActivity;
import ru.romaaaka.mathemagic.view.GameHistoryActivity;
import ru.romaaaka.mathemagic.view.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    private Button startGameButton;
    private Button settingsButton;
    private Button resultsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Убедитесь, что разметка существует

        // Связываем элементы интерфейса
        startGameButton = findViewById(R.id.startGameButton);
        settingsButton = findViewById(R.id.settingsButton);
        resultsButton = findViewById(R.id.resultsButton);

        // Настраиваем обработчики событий для кнопок
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent); // Переход к игровому экрану
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent); // Переход к экрану настроек
            }
        });

        resultsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameHistoryActivity.class);
                startActivity(intent); // Переход к экрану результатов
            }
        });
    }
}
