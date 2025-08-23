package ru.yandex.kingartaved.dto.response;

public enum ContentUpdateResult {
    CONTENT_UPDATED("Содержимое обновлено"),
    NOTE_SHOULD_BE_DELETED("Заметка должна быть удалена");
//    OPERATION_CANCELLED("Операция отменена");

    private final String description;

    ContentUpdateResult(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
