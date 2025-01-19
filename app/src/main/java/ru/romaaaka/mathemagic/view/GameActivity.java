package ru.romaaaka.mathemagic.view;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import ru.romaaaka.mathemagic.R;
import ru.romaaaka.mathemagic.repository.GameRepository;
import ru.romaaaka.mathemagic.utils.TimerHelper;
import ru.romaaaka.mathemagic.viewmodel.GameViewModel;
import ru.romaaaka.mathemagic.viewmodel.GameViewModelFactory;

public class GameActivity extends AppCompatActivity {
    private GameViewModel gameViewModel;
    private ProgressBar progressBar;
    private TimerHelper timerHelper;
    private StringBuilder currentInput = new StringBuilder();
    private TextView exampleTextView, scoreTextView, livesTextView, timerTextView, answerDisplay;
    private Button answerButton;

    private GameRepository repository;

    private List<ImageView> hearts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        repository = new GameRepository(getApplication());

        exampleTextView = findViewById(R.id.exampleTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        progressBar = findViewById(R.id.progressBar);
        answerDisplay = findViewById(R.id.answerDisplay);
        answerButton = findViewById(R.id.btnSubmit);

        hearts = Arrays.asList(
                findViewById(R.id.heart1),
                findViewById(R.id.heart2),
                findViewById(R.id.heart3)
        );

        findViewById(R.id.btnClear).setOnClickListener(v -> clearAnswer());

        gameViewModel = new ViewModelProvider(this, new GameViewModelFactory(this)).get(GameViewModel.class);

        gameViewModel.getCurrentExample().observe(this, exampleTextView::setText);
        gameViewModel.getScore().observe(this, score -> scoreTextView.setText("Score: " + score));
        gameViewModel.getLives().observe(this, this::updateHearts);
        gameViewModel.isGameOver().observe(this, isGameOver -> {
            if (isGameOver) finish(); // Завершаем игру
        });
        displayHighestScore(gameViewModel.getDifficulty());
        setupKeyboard();
        timerHelper = new TimerHelper(60, new TimerHelper.TimerListener() {
            @Override
            public void onTick(int secondsLeft) {
                progressBar.setProgress(secondsLeft);
            }

            @Override
            public void onFinish() {
                gameViewModel.isGameOver().setValue(true);
            }
        });

        // Наблюдаем за gameOver
        gameViewModel.isGameOver().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isGameOver) {
                if (isGameOver != null && isGameOver) {
                    // Игра завершена, записываем результаты
                    showToast( "Game over", Toast.LENGTH_SHORT);
                    saveGameResult(3 - gameViewModel.getLives().getValue(),
                            gameViewModel.getDifficulty(),
                            gameViewModel.getScore().getValue()
                    );
                }
            }
        });
        answerButton.setOnClickListener(v -> {
            // Логика проверки ответа
            float answer = Float.parseFloat(answerDisplay.getText().toString());
            clearAnswer();
            boolean isCorrect = gameViewModel.submitAnswer(answer);
            if (isCorrect){
                showToast("Correct 👍", Toast.LENGTH_SHORT);
            } else {
                showToast("Incorrect 👎", Toast.LENGTH_SHORT);
            }
        });

        timerHelper.start();
    }
    private void showToast(String text, int length){
        Toast.makeText(this, text, length).show();
    }

    // Функция для уменьшения жизней
    private void updateHearts(int lives) {
        for (int i = 0; i < hearts.size(); i++) {
            if (i < lives) {
                hearts.get(i).setImageResource(R.drawable.ic_heart_full); // Полное сердце
            } else {
                hearts.get(i).setImageResource(R.drawable.ic_heart_empty); // Пустое сердце
            }
        }
    }

    private void setupKeyboard() {
        int[] buttonIds = {
                R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4,
                R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9,
                R.id.btn_minus, R.id.btn_dot
        };

        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(this::onKeyboardButtonClick);
        }
    }

    private void onKeyboardButtonClick(View v) {
        Button button = (Button) v;
        String text = button.getText().toString();
        if ("-".equals(text)) {
            // Если нажата кнопка "-", добавляем её только в начало
            if (currentInput.length() == 0 || currentInput.charAt(0) != '-') {
                currentInput.insert(0, "-");
            }
        } else if (".".equals(text)) {
            // Если нажата ".", добавляем её только один раз
            if (!currentInput.toString().contains(".")) {
                currentInput.append(".");
            }
        } else {
            // Для цифр просто добавляем текст
            currentInput.append(text);
        }

        updateAnswerDisplay();
    }

    private void clearAnswer() {
        currentInput.setLength(0); // Очистка текущего ввода
        updateAnswerDisplay(); // Обновление текстового поля
    }

    private void updateAnswerDisplay() {
        answerDisplay.setText(currentInput.length() == 0 ? "0" : currentInput.toString());
    }

    private void displayHighestScore(String difficulty) {
        repository.getHighestScore(difficulty, highestScore -> {
            TextView recordTextView = findViewById(R.id.recordTextView);
            recordTextView.setText("Highest Score: " + highestScore);
        });
    }

    private void saveGameResult(int errors, String difficulty, int score) {
        repository.saveGameRecord(errors, difficulty, score);
    }
}

