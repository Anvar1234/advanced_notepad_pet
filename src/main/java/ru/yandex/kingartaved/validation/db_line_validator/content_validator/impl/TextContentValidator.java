package ru.yandex.kingartaved.validation.db_line_validator.content_validator.impl;

import ru.yandex.kingartaved.config.AppConfig;
import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.exception.ContentValidationException;
import ru.yandex.kingartaved.exception.constant.ErrorMessage;
import ru.yandex.kingartaved.util.LoggerUtil;
import ru.yandex.kingartaved.validation.db_line_validator.content_validator.ContentValidator;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TextContentValidator implements ContentValidator {
    private static final Logger LOGGER = LoggerUtil.getLogger(TextContentValidator.class);
    private static final int MAX_TEXT_LENGTH = AppConfig.MAX_TEXT_LENGTH;

    @Override
    public NoteTypeEnum getSupportedType() {
        return NoteTypeEnum.TEXT_NOTE;
    }

    @Override
    public void validateContent(String contentPart) throws ContentValidationException {
        try {
            //Все базовые проверки уже выполнены в CustomFormatDbLineValidator
            if(contentPart.length() < 1) {
                throw new IllegalArgumentException("Текст слишком короткий. Минимальная длина: " + MAX_TEXT_LENGTH + " символов");
            }
            if (contentPart.length() > MAX_TEXT_LENGTH) {
                throw new IllegalArgumentException("Текст слишком длинный. Максимальная длина: " + MAX_TEXT_LENGTH + " символов");
            }
        } catch (IllegalArgumentException e) {
            String errorMessage = ErrorMessage.CONTENT_VALIDATION_ERROR.getMessage() +
                    " для заметки типа " + getSupportedType() +
                    ": '" + contentPart + "'\nПричина: " + e.getMessage();
            LOGGER.log(Level.SEVERE, errorMessage, e);
            throw new ContentValidationException(errorMessage, e); // Передаем cause
        }
    }
}