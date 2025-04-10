package ru.yandex.kingartaved.util.validator;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;

public interface ContentValidator {
    NoteTypeEnum getSupportedNoteType();
    boolean isValidContent(String value);
}
