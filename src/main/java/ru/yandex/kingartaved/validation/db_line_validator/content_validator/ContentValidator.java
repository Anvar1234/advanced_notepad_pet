package ru.yandex.kingartaved.validation.db_line_validator.content_validator;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.exception.ContentValidationException;

public interface ContentValidator {
    NoteTypeEnum getSupportedType();
    void validateContent(String value) throws ContentValidationException;
}
