package ru.yandex.kingartaved.validation.db_line_validator.metadata_validator.impl;

import ru.yandex.kingartaved.config.AppConfig;
import ru.yandex.kingartaved.data.constant.NotePriorityEnum;
import ru.yandex.kingartaved.data.constant.NoteStatusEnum;
import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.exception.MetadataValidationException;
import ru.yandex.kingartaved.exception.constant.ErrorMessage;
import ru.yandex.kingartaved.util.LoggerUtil;
import ru.yandex.kingartaved.validation.FieldValidationUtil;
import ru.yandex.kingartaved.validation.db_line_validator.metadata_validator.MetadataValidator;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ru.yandex.kingartaved.data.constant.FieldIndex.*;
import static ru.yandex.kingartaved.validation.FieldValidationUtil.*;

public final class DefaultMetadataValidator implements MetadataValidator {
    private static final Logger LOGGER = LoggerUtil.getLogger(DefaultMetadataValidator.class);
    private static final int MAX_TITLE_LENGTH = AppConfig.MAX_TITLE_LENGTH;

    @Override
    public void validateMetadata(String[] parts) throws MetadataValidationException {
        try {
            validateUuidField(parts[ID.getIndex()], ID.getIndex(), ID.getFieldName());
            validateTitle(parts[TITLE.getIndex()]);
            validateDateField(parts[CREATED_AT.getIndex()], CREATED_AT.getIndex(), CREATED_AT.getFieldName());
            validateDateField(parts[UPDATED_AT.getIndex()], UPDATED_AT.getIndex(), UPDATED_AT.getFieldName());
            if (FieldValidationUtil.isNotStringNull(parts[REMIND_AT.getIndex()])) {
                validateDateField(parts[REMIND_AT.getIndex()], REMIND_AT.getIndex(), REMIND_AT.getFieldName());
            }
            validateBooleanField(parts[PINNED.getIndex()], PINNED.getIndex(), PINNED.getFieldName());
            validateEnumField(parts[PRIORITY.getIndex()], PRIORITY.getIndex(), PRIORITY.getFieldName(), NotePriorityEnum.class);
            validateEnumField(parts[STATUS.getIndex()], STATUS.getIndex(), STATUS.getFieldName(), NoteStatusEnum.class);
            validateEnumField(parts[TYPE.getIndex()], TYPE.getIndex(), TYPE.getFieldName(), NoteTypeEnum.class);
        } catch (IllegalArgumentException e) {
            String errorMessage = ErrorMessage.METADATA_VALIDATION_ERROR.getMessage();
            String logMessage = errorMessage + " для массива полей: " +
                    Arrays.toString(parts) +
                    "\nПричина: " + e.getMessage();
            LOGGER.log(Level.SEVERE, logMessage, e);
            throw new MetadataValidationException(errorMessage, e);
        }
    }

    private void validateTitle(String titlePart) {
        if (titlePart.length() > MAX_TITLE_LENGTH) {
            throw new IllegalArgumentException("Заголовок слишком длинный. " +
                    "Максимальная длина: " + MAX_TITLE_LENGTH + " символов");
        }
    }
}
