package ru.yandex.kingartaved.validation.db_line_validator.impl;

import ru.yandex.kingartaved.config.AppConfig;
import ru.yandex.kingartaved.config.FieldIndex;
import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.exception.ContentValidationException;
import ru.yandex.kingartaved.exception.MetadataValidationException;
import ru.yandex.kingartaved.exception.constant.ErrorMessage;
import ru.yandex.kingartaved.util.LoggerUtil;
import ru.yandex.kingartaved.config.ContentValidatorRegistry;
import ru.yandex.kingartaved.validation.db_line_validator.DbLineValidator;
import ru.yandex.kingartaved.validation.db_line_validator.content_validator.ContentValidator;
import ru.yandex.kingartaved.validation.db_line_validator.metadata_validator.MetadataValidator;

import java.util.logging.Level;
import java.util.logging.Logger;

import static ru.yandex.kingartaved.config.FieldIndex.*;
import static ru.yandex.kingartaved.validation.ValidationUtil.*;

/**
 * Валидатор строк из базы данных, проверяющий их соответствие формату заметок.
 * Осуществляет разбор строки по разделителю '|', проверку количества полей и их валидность.
 */

public class CustomFormatDbLineValidator implements DbLineValidator {
    private static final Logger LOGGER = LoggerUtil.getLogger(CustomFormatDbLineValidator.class);
    private static final int EXPECTED_FIELDS_COUNT = 10;
    private static final String DB_FIELD_DELIMITER = AppConfig.DB_FIELD_DELIMITER;
    private final MetadataValidator metadataValidator;
    private final ContentValidatorRegistry contentValidatorRegistry;

    public CustomFormatDbLineValidator(ContentValidatorRegistry contentValidatorRegistry, MetadataValidator metadataValidator) {
        this.contentValidatorRegistry = contentValidatorRegistry;
        this.metadataValidator = metadataValidator;
    }


    /**
     * Проверяет строку из БД на соответствие формату заметки.
     * <p>
     * Формат строки (10 полей через '|'):
     * 0: UUID, 1: заголовок, 2: дата создания, 3: дата изменения,
     * 4: дата напоминания (или "null"), 5: закреплена (true/false),
     * 6: приоритет, 7: статус, 8: тип, 9: содержимое.
     * <p>
     * Процесс валидации:<p>
     * 1. Проверка строки на null/пустоту <p>
     * 2. Разделение по символу '|'<p>
     * 3. Проверка количества полей<p>
     * 4. Удаление пробелов вокруг значений<p>
     * 5. Проверка значений полей<p>
     *
     * @param lineFromDb Строка из БД в формате "id|title|createdAt|...|content".
     * @return true если строка валидна, false в противном случае (ошибки логируются).
     */
    @Override
    public boolean isValidDbLine(String lineFromDb) {

        String[] parts;

        try {
            validateLineNotEmpty(lineFromDb);

            parts = lineFromDb.split(DB_FIELD_DELIMITER, -1);

            validateLineStructure(parts, REMIND_AT.getIndex());
            metadataValidator.validateMetadata(parts);
            validateNoteContent(parts);

            return true;
        } catch (IllegalArgumentException | MetadataValidationException | ContentValidationException e) {
            String errorMessage = ErrorMessage.DB_LINE_VALIDATION_ERROR.getMessage() + ": " + lineFromDb + "'\nПричина: " + e.getMessage();
            LOGGER.log(Level.SEVERE, errorMessage, e);
            return false;
        }
    }

    /**
     * Проверяет, что строка из БД не является null или пустой.
     */
    private void validateLineNotEmpty(String lineFromDb) {
        if (lineFromDb == null || lineFromDb.isBlank()) {
            throw new IllegalArgumentException("Строка из БД не должна быть null или пустой");
        }
    }

    /**
     * Проверяет структурную целостность строки из БД:
     */
    void validateLineStructure(String[] parts, int... nullableIndexes) {// TODO: По окончании работ убрать nullableIndexes, если не понадобится.
        validatePartsCount(parts);
        for (int i = 0; i < parts.length; i++) {
            validateFieldNotEmpty(parts[i], i, FieldIndex.values()[i].getFieldName());
            validateFieldAfterTrim(parts[i], i, FieldIndex.values()[i].getFieldName());
            if (contains(i, nullableIndexes)) continue;
            validateFieldNotEqualsStringNull(parts[i], i, FieldIndex.values()[i].getFieldName());
        }
    }

    /**
     * Проверяет, что количество полей соответствует ожидаемому.
     */
    void validatePartsCount(String[] parts) {
        if (parts.length != EXPECTED_FIELDS_COUNT) {
            throw new IllegalArgumentException("Неверное количество полей после split: ожидалось: " + EXPECTED_FIELDS_COUNT + ", пришло: " + parts.length);
        }
    }

    /**
     * Проверяет валидность содержимого заметки в зависимости от её типа.
     */
    void validateNoteContent(String[] parts) throws ContentValidationException {
        NoteTypeEnum noteTypeEnum = Enum.valueOf(NoteTypeEnum.class, parts[TYPE.getIndex()]);
        ContentValidator contentValidator = contentValidatorRegistry.getValidator(noteTypeEnum);
        contentValidator.validateContent(parts[CONTENT.getIndex()]);
    }
}