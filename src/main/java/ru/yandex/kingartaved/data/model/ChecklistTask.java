package ru.yandex.kingartaved.data.model;

public class ChecklistTask {
    private final String text;
    private final boolean isDone;

    public ChecklistTask(String text, boolean isDone) {
        this.text = text;
        this.isDone = isDone;
    }

    public String getText() {
        return text;
    }

    public boolean isDone() {
        return isDone;
    }
}