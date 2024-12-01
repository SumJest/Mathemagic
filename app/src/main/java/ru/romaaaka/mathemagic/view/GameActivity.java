package ru.romaaaka.mathemagic.view;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.widget.TextView;
import android.widget.Button;

import ru.romaaaka.mathemagic.R;
import ru.romaaaka.mathemagic.utils.TimerHelper;
import ru.romaaaka.mathemagic.viewmodel.GameViewModel;
import ru.romaaaka.mathemagic.viewmodel.GameViewModelFactory;

public class GameActivity extends AppCompatActivity {
    private GameViewModel gameViewModel;
    private TimerHelper timerHelper;

    private TextView exampleTextView, scoreTextView, livesTextView, timerTextView, answerEditText;
    private Button answerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        SharedPreferences sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);

        exampleTextView = findViewById(R.id.exampleTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        livesTextView = findViewById(R.id.livesTextView);
        timerTextView = findViewById(R.id.timerTextView);
        answerEditText = findViewById(R.id.answerEditText);
        answerButton = findViewById(R.id.answerButton);

        int difficulty = sharedPreferences.getInt("difficulty", R.id.difficultyEasy);
        int operation = sharedPreferences.getInt("operation", R.id.operationAdd);

        gameViewModel = new ViewModelProvider(this, new GameViewModelFactory(this)).get(GameViewModel.class);

        gameViewModel.getCurrentExample().observe(this, exampleTextView::setText);
        gameViewModel.getScore().observe(this, score -> scoreTextView.setText("Score: " + score));
        gameViewModel.getLives().observe(this, lives -> livesTextView.setText("Lives: " + lives));
        gameViewModel.isGameOver().observe(this, isGameOver -> {
            if (isGameOver) finish(); // Завершаем игру
        });

        timerHelper = new TimerHelper(60, new TimerHelper.TimerListener() {
            @Override
            public void onTick(int secondsLeft) {
                timerTextView.setText("Time: " + secondsLeft);
            }

            @Override
            public void onFinish() {
                gameViewModel.isGameOver().setValue(true);
            }
        });

        answerButton.setOnClickListener(v -> {
            float userAnswer = Float.parseFloat(answerEditText.getText().toString()); // Для примера
            gameViewModel.submitAnswer(userAnswer);
        });

        timerHelper.start();
    }
}

