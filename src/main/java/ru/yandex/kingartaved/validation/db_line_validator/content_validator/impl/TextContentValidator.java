package ru.yandex.kingartaved.validation.db_line_validator.content_validator.impl;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.exception.ContentValidationException;
import ru.yandex.kingartaved.exception.constant.ErrorMessage;
import ru.yandex.kingartaved.util.LoggerUtil;
import ru.yandex.kingartaved.validation.db_line_validator.content_validator.ContentValidator;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TextContentValidator implements ContentValidator {
    private static final Logger LOGGER = LoggerUtil.getLogger(TextContentValidator.class);

    @Override
    public NoteTypeEnum getSupportedType() {
        return NoteTypeEnum.TEXT_NOTE;
    }

    @Override
    public void validateContent(String contentPart) throws ContentValidationException {
        try {
            if (contentPart == null || contentPart.trim().isEmpty()) {
                throw new IllegalArgumentException("Поле контента заметки типа " + getSupportedType() + " не может быть null или пустым");
            }
            if ("null".equalsIgnoreCase(contentPart)) {
                throw new IllegalArgumentException("Поле контента заметки типа " + getSupportedType() + " содержит строку 'null'");
            }
        } catch (IllegalArgumentException e) {
            String errorMessage = ErrorMessage.CONTENT_VALIDATION_ERROR.getMessage() + ": '" + contentPart + "'\nПричина: " + e.getMessage();
            LOGGER.log(Level.SEVERE, errorMessage, e);
            throw new ContentValidationException(errorMessage, e);
        }
    }
}
