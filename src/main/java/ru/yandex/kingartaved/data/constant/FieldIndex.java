package ru.yandex.kingartaved.data.constant;

public enum FieldIndex {
    ID(0, "id"),
    TITLE(1, "title"),
    CREATED_AT(2, "createdAt"),
    UPDATED_AT(3, "updatedAt"),
    REMIND_AT(4, "remindAt"),
    PINNED(5, "pinned"),
    PRIORITY(6, "priority"),
    STATUS(7, "status"),
    TYPE(8, "type"),
    CONTENT(9, "content");

    private final int index;
    private final String fieldName;

    FieldIndex(int index, String fieldName) {
        this.index = index;
        this.fieldName = fieldName;
    }

    public int getIndex() {
        return index;
    }

    public String getFieldName() {
        return fieldName;
    }
}