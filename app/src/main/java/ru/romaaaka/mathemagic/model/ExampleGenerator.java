package ru.romaaaka.mathemagic.model;

public class ExampleGenerator {
    private String operationType; // "+", "-", "*", "/"
    private int difficulty; // Уровень сложности: 1, 2, 3

    public ExampleGenerator(String operationType, int difficulty) {
        this.operationType = operationType;
        this.difficulty = difficulty;
    }

    private int generateNumber(int difficulty) {
        int start = (int) Math.pow(10, difficulty - 1); // Определяем диапазон чисел
        int end = (int) Math.pow(10, difficulty);
        return (int) (Math.random() * (end - start)) + start;
    }

    public String generateExample() {
        int num1 = generateNumber(this.difficulty);
        int num2 = generateNumber(this.difficulty);

        if (operationType.equals("/")) {
            int answer = ((int) (Math.random() * 9)) + 1;
            num1 = num2 * answer;
        } else if (operationType.equals("*")) {
            num1 = generateNumber(Math.max(this.difficulty - 1, 1));
            num2 = generateNumber(Math.max(this.difficulty - 1, 1));
        }

        return num1 + " " + operationType + " " + num2 + " = ?";
    }

    public float getCorrectAnswer(String example) {
        String[] parts = example.split(" ");
        int num1 = Integer.parseInt(parts[0]);
        int num2 = Integer.parseInt(parts[2]);

        switch (parts[1]) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                return (float) num1 / num2;
            default:
                throw new IllegalArgumentException("Unknown operation type");
        }
    }
}
