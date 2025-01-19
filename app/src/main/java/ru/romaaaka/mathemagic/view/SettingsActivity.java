package ru.romaaaka.mathemagic.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ru.romaaaka.mathemagic.R;

public class SettingsActivity extends AppCompatActivity {

    private RadioGroup difficultyGroup;
    private Button saveButton;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Инициализация SharedPreferences
        sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);

        // Связываем элементы интерфейса
        difficultyGroup = findViewById(R.id.difficultyGroup);
        saveButton = findViewById(R.id.saveButton);

        // Загружаем ранее сохранённые настройки
        loadSettings();

        // Обработчик нажатия кнопки "Сохранить"
        saveButton.setOnClickListener(v -> {
            saveSettings();
            Toast.makeText(SettingsActivity.this, "Settings saved", Toast.LENGTH_SHORT).show();
            finish(); // Закрываем экран настроек
        });
    }

    private void loadSettings() {
        int savedDifficulty = sharedPreferences.getInt("difficulty", R.id.difficultyEasy);

        // Устанавливаем сохранённые значения
        difficultyGroup.check(savedDifficulty);
    }

    private void saveSettings() {
        int selectedDifficulty = difficultyGroup.getCheckedRadioButtonId();

        // Сохраняем выбранные значения
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("difficulty", selectedDifficulty);
        editor.apply();
    }
}
