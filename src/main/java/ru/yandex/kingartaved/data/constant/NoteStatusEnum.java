package ru.yandex.kingartaved.data.constant;

public enum NoteStatusEnum {

    ACTIVE("Активная"),
    COMPLETED("Завершенная"),
    POSTPONED("Отложенная"),
    ARCHIVED("Архивная");

    private final String description;

    NoteStatusEnum(String description) {
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
