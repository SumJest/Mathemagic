package ru.romaaaka.mathemagic.view;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Button;

import ru.romaaaka.mathemagic.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        exampleTextView = findViewById(R.id.exampleTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        livesTextView = findViewById(R.id.livesTextView);
        progressBar = findViewById(R.id.progressBar);
        answerDisplay = findViewById(R.id.answerDisplay);
        answerButton = findViewById(R.id.btnSubmit);
        findViewById(R.id.btnClear).setOnClickListener(v -> clearAnswer());

        gameViewModel = new ViewModelProvider(this, new GameViewModelFactory(this)).get(GameViewModel.class);

        gameViewModel.getCurrentExample().observe(this, exampleTextView::setText);
        gameViewModel.getScore().observe(this, score -> scoreTextView.setText("Score: " + score));
        gameViewModel.getLives().observe(this, lives -> livesTextView.setText("Lives: " + lives));
        gameViewModel.isGameOver().observe(this, isGameOver -> {
            if (isGameOver) finish(); // Завершаем игру
        });
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

        answerButton.setOnClickListener(v -> {
            // Логика проверки ответа
            float answer = Float.parseFloat(answerDisplay.getText().toString());
            clearAnswer();
            gameViewModel.submitAnswer(answer);
        });

        timerHelper.start();
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
}

