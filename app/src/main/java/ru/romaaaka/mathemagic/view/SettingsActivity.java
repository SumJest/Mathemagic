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
    private RadioGroup operationGroup;
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
        operationGroup = findViewById(R.id.operationGroup);
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
        int savedOperation = sharedPreferences.getInt("operation", R.id.operationAdd);

        // Устанавливаем сохранённые значения
        difficultyGroup.check(savedDifficulty);
        operationGroup.check(savedOperation);
    }

    private void saveSettings() {
        int selectedDifficulty = difficultyGroup.getCheckedRadioButtonId();
        int selectedOperation = operationGroup.getCheckedRadioButtonId();

        // Сохраняем выбранные значения
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("difficulty", selectedDifficulty);
        editor.putInt("operation", selectedOperation);
        editor.apply();
    }
}
