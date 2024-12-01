package ru.romaaaka.mathemagic.model;

public class GameManager {
    private int score;
    private int lives;
    private int timeLeft;
    private boolean gameOver;

    public GameManager(int initialLives, int initialTime) {
        this.score = 0;
        this.lives = initialLives;
        this.timeLeft = initialTime;
        this.gameOver = false;
    }

    public void startGame() {
        this.score = 0;
        this.lives = 3;
        this.gameOver = false;
    }

    public boolean checkAnswer(float userAnswer, float correctAnswer) {
        if (userAnswer == correctAnswer) {
            score++;
            return true;
        } else {
            lives--;
            if (lives <= 0) {
                gameOver = true;
            }
            return false;
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void decrementTime() {
        if (timeLeft > 0) {
            timeLeft--;
        } else {
            gameOver = true;
        }
    }
}

