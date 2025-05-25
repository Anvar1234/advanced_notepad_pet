package ru.yandex.kingartaved.validation.db_line_validator.metadata_validator.impl;

import ru.yandex.kingartaved.data.constant.NotePriorityEnum;
import ru.yandex.kingartaved.data.constant.NoteStatusEnum;
import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.exception.MetadataValidationException;
import ru.yandex.kingartaved.exception.constant.ErrorMessage;
import ru.yandex.kingartaved.util.LoggerUtil;
import ru.yandex.kingartaved.validation.db_line_validator.metadata_validator.MetadataValidator;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ru.yandex.kingartaved.config.FieldIndex.*;

public class DefaultMetadataValidator implements MetadataValidator {
    private static final Logger LOGGER = LoggerUtil.getLogger(DefaultMetadataValidator.class);

    @Override
    public void validateMetadata(String[] parts) throws MetadataValidationException {
        try {
            validateNoNullPartsValues(parts, REMIND_AT.getIndex());

            validateUuid(parts, ID.getIndex(), ID.getFieldName());
            validateNotEmpty(parts, TITLE.getIndex(), TITLE.getFieldName());
            validateDate(parts, CREATED_AT.getIndex(), CREATED_AT.getFieldName());
            validateDate(parts, UPDATED_AT.getIndex(), UPDATED_AT.getFieldName());
            if (!"null".equalsIgnoreCase(parts[REMIND_AT.getIndex()])) {
                validateDate(parts, REMIND_AT.getIndex(), REMIND_AT.getFieldName());
            }
            validateBoolean(parts, PINNED.getIndex(), PINNED.getFieldName());
            validateEnum(parts, PRIORITY.getIndex(), PRIORITY.getFieldName(), NotePriorityEnum.class);
            validateEnum(parts, STATUS.getIndex(), STATUS.getFieldName(), NoteStatusEnum.class);
            validateEnum(parts, TYPE.getIndex(), TYPE.getFieldName(), NoteTypeEnum.class);
        } catch (IllegalArgumentException e) {
            String errorMessage = ErrorMessage.METADATA_VALIDATION_ERROR.getMessage() + ": " + Arrays.toString(parts) + "\nПричина: " + e.getMessage();
            LOGGER.log(Level.SEVERE, errorMessage, e);
            throw new MetadataValidationException(ErrorMessage.METADATA_VALIDATION_ERROR.getMessage(), e);
        }
    }

    /**
     * Проверяет, что указанное поле содержит валидный UUID.
     */
    private void validateUuid(String[] parts, int index, String label) {
        try {
            UUID.fromString(parts[index]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Поле '" + label + "' (index " + index + ") содержит недопустимый UUID: " + parts[index], e);
        }
    }

    /**
     * Проверяет, что указанное поле содержит валидную дату.
     */
    private void validateDate(String[] parts, int index, String label) {
        try {
            LocalDateTime.parse(parts[index]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Поле '" + label + "' (index " + index + ") содержит недопустимую дату: '" + parts[index] + "'", e);
        }
    }

    /**
     * Проверяет, что поле не пустое.
     */
    private void validateNotEmpty(String[] parts, int index, String label) {
        if (parts[index].isEmpty()) {
            throw new IllegalArgumentException("Поле '" + label + "' (index " + index + ") не может быть пустым");
        }
    }

    /**
     * Проверяет, что указанное поле содержит булево значение.
     */
    void validateBoolean(String[] parts, int index, String label) {
        if (!"true".equalsIgnoreCase(parts[index]) && !"false".equalsIgnoreCase(parts[index])) {
            throw new IllegalArgumentException("Поле '" + label + "' (index " + index + ") должно быть true/false, но пришло: " + parts[index]);
        }
    }

    /**
     * Проверяет, что указанное поле содержит допустимое значение перечисления.
     */
    <E extends Enum<E>> void validateEnum(String[] parts, int index, String label, Class<E> enumClass) {
        try {
            Enum.valueOf(enumClass, parts[index]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Поле '" + label + "' (index " + index + ") содержит недопустимое enum-значение (" + parts[index] + ") для перечисления " + enumClass.getSimpleName(), e);
        }
    }

    /**
     * Проверяет, что ни один из элементов массива (кроме исключённых индексов) не равен строке "null" (без учёта регистра).
     *
     * @param parts           Массив строк для проверки (не может быть null).
     * @param excludedIndexes Индексы элементов, которые следует игнорировать (например, 2, 4).
     * @return true если все проверяемые элементы не содержат "null".
     * @throws NullPointerException если массив равен null.
     * @see #contains(int[], int)
     */
    void validateNoNullPartsValues(String[] parts, int... excludedIndexes) {
        for (int i = 0; i < parts.length; i++) {
            if (contains(excludedIndexes, i)) continue; // TODO: Пропускаем исключённые индексы
            if ("null".equalsIgnoreCase(parts[i])) {
                throw new IllegalArgumentException("Поле с индексом " + i + " содержит null");
            }
        }
    }

    /**
     * Проверяет наличие значения в массиве исключаемых из проверки целых чисел.
     *
     * @param excludedIndexes Массив целых чисел для проверки (например, [4] или [2, 4]).
     * @param inputIndex      Искомое значение (например, индекс поля i).
     * @return true, если значение найдено в массиве.
     */
    private boolean contains(int[] excludedIndexes, int inputIndex) {
        for (int item : excludedIndexes) {
            if (item == inputIndex) {
                return true;
            }
        }
        return false;
    }

}
