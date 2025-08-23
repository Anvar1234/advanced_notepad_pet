package ru.yandex.kingartaved.dto;

import java.util.Objects;

public final class ChecklistTaskDto {
    private final String text;
    private final boolean isDone;

    public ChecklistTaskDto(String text, boolean isDone) {
        this.text = text;
        this.isDone = isDone;
    }

    public String text() {
        return text;
    }

    public boolean isDone() {
        return isDone;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ChecklistTaskDto) obj;
        return Objects.equals(this.text, that.text) &&
                this.isDone == that.isDone;
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, isDone);
    }

    @Override
    public String toString() {
        return "ChecklistTaskDto[" +
                "text=" + text + ", " +
                "isDone=" + isDone + ']';
    }

}