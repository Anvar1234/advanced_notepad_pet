package ru.yandex.kingartaved.data.constant;

public enum NoteTypeEnum {

    TEXT_NOTE("Текстовая заметка"),
    CHECKLIST("Чек-лист");

    private final String noteType;

    NoteTypeEnum(String noteType) {
        this.noteType = noteType;
    }

    public String getNoteType() {
        return noteType;
    }
}
