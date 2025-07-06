package ru.yandex.kingartaved.validation.db_line_validator.content_validator.impl;

import ru.yandex.kingartaved.config.AppConfig;
import ru.yandex.kingartaved.config.FieldIndex;
import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.exception.ContentValidationException;
import ru.yandex.kingartaved.util.LoggerUtil;
import ru.yandex.kingartaved.validation.DataValidationUtil;
import ru.yandex.kingartaved.validation.db_line_validator.content_validator.ContentValidator;

import java.util.logging.Logger;

/**
 * Валидатор семантики текстового контента.
 */

public class TextContentValidator implements ContentValidator {
    private static final Logger LOGGER = LoggerUtil.getLogger(TextContentValidator.class);
    private static final int MIN_TEXT_LENGTH = AppConfig.MIN_TEXT_LENGTH;
    private static final int MAX_TEXT_LENGTH = AppConfig.MAX_TEXT_LENGTH;

    @Override
    public NoteTypeEnum getSupportedType() {
        return NoteTypeEnum.TEXT_NOTE;
    }

    @Override
    public void validateContent(String[] parts) throws ContentValidationException {
        try {
            //Все базовые проверки уже выполнены в @link{DefaultDbLineValidator}
            DataValidationUtil.validateText(parts[FieldIndex.CONTENT.getIndex()], MIN_TEXT_LENGTH, MAX_TEXT_LENGTH);
        } catch (IllegalArgumentException e) {
            logAndThrowContentValidationException(parts[FieldIndex.CONTENT.getIndex()], e);
        }
    }
}