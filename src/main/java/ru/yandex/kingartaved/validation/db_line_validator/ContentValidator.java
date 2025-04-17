package ru.yandex.kingartaved.validation.db_line_validator;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;

public interface ContentValidator {
    NoteTypeEnum getSupportedNoteType();
    boolean isValidContent(String value);
}
