package ru.yandex.kingartaved.data.model;

public class ChecklistItem {
    private final String text;
    private final boolean isDone;

    public ChecklistItem(String text, boolean isDone) {
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