package ru.yandex.kingartaved.data.constant;

public enum NoteTypeEnum {

    TEXT_NOTE("Текстовая заметка"),
    CHECK_LIST("Чек-лист");

    private final String noteType;

    NoteTypeEnum(String noteType) {
        this.noteType = noteType;
    }

    public String getNoteType() {
        return noteType;
    }
}
