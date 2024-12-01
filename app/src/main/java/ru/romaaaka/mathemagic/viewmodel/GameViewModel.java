package ru.romaaaka.mathemagic.viewmodel;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.romaaaka.mathemagic.R;
import ru.romaaaka.mathemagic.model.ExampleGenerator;
import ru.romaaaka.mathemagic.model.GameManager;


public class GameViewModel extends ViewModel {
    private final GameManager gameManager;
    private final ExampleGenerator exampleGenerator;

    private final MutableLiveData<String> currentExample = new MutableLiveData<>();
    private final MutableLiveData<Integer> score = new MutableLiveData<>();
    private final MutableLiveData<Integer> lives = new MutableLiveData<>();
    private final MutableLiveData<Boolean> gameOver = new MutableLiveData<>();

    private int difficultyLevel; // Уровень сложности
    private String operationType; // Тип арифметической операции

    public GameViewModel(Context context) {
        this.gameManager = new GameManager(3, 60); // 3 жизни, 60 секунд
        this.loadSettings(context);
        this.exampleGenerator = new ExampleGenerator(operationType, difficultyLevel);
        startNewGame();
    }

    // Метод для загрузки настроек из SharedPreferences
    private void loadSettings(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE);

        // Загрузка уровня сложности
        int difficultyId = sharedPreferences.getInt("difficulty", R.id.difficultyEasy);
        if (difficultyId == R.id.difficultyEasy) {
            difficultyLevel = 1;
        } else if (difficultyId == R.id.difficultyMedium) {
            difficultyLevel = 2;
        } else if (difficultyId == R.id.difficultyHard) {
            difficultyLevel = 3;
        } else {
            difficultyLevel = 1; // По умолчанию Easy
        }

        // Загрузка типа операции
        int operationId = sharedPreferences.getInt("operation", R.id.operationAdd);
        if (operationId == R.id.operationAdd) {
            operationType = "+";
        } else if (operationId == R.id.operationSubtract) {
            operationType = "-";
        } else if (operationId == R.id.operationMultiply) {
            operationType = "*";
        } else if (operationId == R.id.operationDivide) {
            operationType = "/";
        } else {
            operationType = "+"; // По умолчанию Addition
        }
    }

    public void startNewGame() {
        gameManager.startGame();
        score.setValue(gameManager.getScore());
        lives.setValue(gameManager.getLives());
        gameOver.setValue(false);
        generateNextExample();
    }

    public void generateNextExample() {
        String example = exampleGenerator.generateExample();
        currentExample.setValue(example);
    }

    public void submitAnswer(float userAnswer) {
        float correctAnswer = exampleGenerator.getCorrectAnswer(currentExample.getValue());
        if (gameManager.checkAnswer(userAnswer, correctAnswer)) {
            score.setValue(gameManager.getScore());
        } else {
            lives.setValue(gameManager.getLives());
        }

        if (gameManager.isGameOver()) {
            gameOver.setValue(true);
        } else {
            generateNextExample();
        }
    }

    public MutableLiveData<String> getCurrentExample() {
        return currentExample;
    }

    public MutableLiveData<Integer> getScore() {
        return score;
    }

    public MutableLiveData<Integer> getLives() {
        return lives;
    }

    public MutableLiveData<Boolean> isGameOver() {
        return gameOver;
    }
}

