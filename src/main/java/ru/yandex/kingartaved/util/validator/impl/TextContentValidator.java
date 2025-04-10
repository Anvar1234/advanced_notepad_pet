package ru.yandex.kingartaved.util.validator.impl;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.util.validator.ContentValidator;

public class TextContentValidator implements ContentValidator {

    @Override
    public NoteTypeEnum getSupportedNoteType() {
        return NoteTypeEnum.TEXT_NOTE;
    }

    @Override
    public boolean isValidContent(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
