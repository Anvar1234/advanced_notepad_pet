package ru.yandex.kingartaved.validation.db_line_validator.impl;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.validation.db_line_validator.ContentValidator;

public class TextContentValidator implements ContentValidator {

    @Override
    public NoteTypeEnum getSupportedType() {
        return NoteTypeEnum.TEXT_NOTE;
    }

    @Override
    public boolean isValidContent(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
