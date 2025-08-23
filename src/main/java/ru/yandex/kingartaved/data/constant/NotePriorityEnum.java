package ru.yandex.kingartaved.data.constant;

public enum NotePriorityEnum {

    HIGH("Высокий"),
    BASE("Базовый"),
    LOW("Низкий");

    private final String priority;

    NotePriorityEnum(String priority) {
        this.priority = priority;
    }
}
