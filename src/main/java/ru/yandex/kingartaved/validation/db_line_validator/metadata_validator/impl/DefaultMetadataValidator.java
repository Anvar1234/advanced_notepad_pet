package ru.yandex.kingartaved.validation.db_line_validator.metadata_validator.impl;

import ru.yandex.kingartaved.data.constant.NotePriorityEnum;
import ru.yandex.kingartaved.data.constant.NoteStatusEnum;
import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.exception.MetadataValidationException;
import ru.yandex.kingartaved.exception.constant.ErrorMessage;
import ru.yandex.kingartaved.util.LoggerUtil;
import ru.yandex.kingartaved.validation.db_line_validator.metadata_validator.MetadataValidator;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ru.yandex.kingartaved.config.FieldIndex.*;
import static ru.yandex.kingartaved.validation.ValidationUtil.*;

public class DefaultMetadataValidator implements MetadataValidator {
    private static final Logger LOGGER = LoggerUtil.getLogger(DefaultMetadataValidator.class);

    @Override
    public void validateMetadata(String[] parts) throws MetadataValidationException {
        try {
            validateUuid(parts[ID.getIndex()], ID.getIndex(), ID.getFieldName());
            validateFieldNotEmpty(parts[TITLE.getIndex()], TITLE.getIndex(), TITLE.getFieldName());
            validateDate(parts[CREATED_AT.getIndex()], CREATED_AT.getIndex(), CREATED_AT.getFieldName());
            validateDate(parts[UPDATED_AT.getIndex()], UPDATED_AT.getIndex(), UPDATED_AT.getFieldName());
            if (isFieldNotEqualsStringNull(parts[REMIND_AT.getIndex()])) {
                validateDate(parts[REMIND_AT.getIndex()], REMIND_AT.getIndex(), REMIND_AT.getFieldName());
            }
            validateBoolean(parts[PINNED.getIndex()], PINNED.getIndex(), PINNED.getFieldName());
            validateEnum(parts[PRIORITY.getIndex()], PRIORITY.getIndex(), PRIORITY.getFieldName(), NotePriorityEnum.class);
            validateEnum(parts[STATUS.getIndex()], STATUS.getIndex(), STATUS.getFieldName(), NoteStatusEnum.class);
            validateEnum(parts[TYPE.getIndex()], TYPE.getIndex(), TYPE.getFieldName(), NoteTypeEnum.class);
        } catch (IllegalArgumentException e) {
            String errorMessage = ErrorMessage.METADATA_VALIDATION_ERROR.getMessage() + ": " + Arrays.toString(parts) + "\nПричина: " + e.getMessage();
            LOGGER.log(Level.SEVERE, errorMessage, e);
            throw new MetadataValidationException(ErrorMessage.METADATA_VALIDATION_ERROR.getMessage(), e);
        }
    }
}
