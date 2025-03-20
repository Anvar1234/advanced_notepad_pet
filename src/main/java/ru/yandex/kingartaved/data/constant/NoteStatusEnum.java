package ru.yandex.kingartaved.data.constant;

public enum NoteStatusEnum {

    ACTIVE("Активная"),
    COMPLETED("Завершенная"),
    POSTPONED("Отложенная"),
    ARCHIVED("Архивная");

    private final String clientType;

    NoteStatusEnum(String clientType) {
        this.clientType = clientType;
    }
}
