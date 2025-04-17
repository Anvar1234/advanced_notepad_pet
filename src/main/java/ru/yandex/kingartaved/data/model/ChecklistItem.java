package ru.yandex.kingartaved.data.model;

public class ChecklistItem {
    private String text;
    private boolean isCompleted;

    public ChecklistItem(String text) {
        this.text = text;
        this.isCompleted = false;
    }

    public String getText() {
        return text;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}