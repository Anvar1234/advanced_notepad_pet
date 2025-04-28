package ru.yandex.kingartaved.data.model;

public class ChecklistItem {
    private String text;
    private boolean isDone;

    public ChecklistItem(String text) {
        this.text = text;
        this.isDone = false;
    }

    public String getText() {
        return text;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}