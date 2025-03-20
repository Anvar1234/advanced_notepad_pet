package ru.yandex.kingartaved.data.model;

public class CheckListItem {
    private String text;
    private boolean isCompleted;

    public CheckListItem(String text) {
        this.text = text;
        this.isCompleted = false;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}