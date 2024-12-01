package ru.romaaaka.mathemagic.model;

public class ExampleGenerator {
    private String operationType; // "+", "-", "*", "/"
    private int difficulty; // Уровень сложности: 1, 2, 3

    public ExampleGenerator(String operationType, int difficulty) {
        this.operationType = operationType;
        this.difficulty = difficulty;
    }

    public String generateExample() {
        int range = difficulty * 10; // Определяем диапазон чисел
        int num1 = (int) (Math.random() * range);
        int num2 = (int) (Math.random() * range);

        if (operationType.equals("/") && num2 == 0) num2 = 1; // Избегаем деления на 0

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
