package ru.yandex.kingartaved.validation.db_line_validator.content_validator.impl;

import ru.yandex.kingartaved.config.AppConfig;
import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.exception.ContentValidationException;
import ru.yandex.kingartaved.validation.DataValidationUtil;
import ru.yandex.kingartaved.validation.FieldValidationUtil;
import ru.yandex.kingartaved.validation.db_line_validator.content_validator.ContentValidator;

import java.util.Arrays;
import java.util.List;

/**
 * Валидатор семантики контента чек-листа.
 */

public class ChecklistContentValidator implements ContentValidator {
    private static final int EXPECTED_ITEM_FIELDS_COUNT = 2;
    private static final int MIN_TEXT_LENGTH = AppConfig.MIN_TEXT_LENGTH;
    private static final int MAX_TEXT_LENGTH = AppConfig.MAX_TEXT_LENGTH;

    @Override
    public NoteTypeEnum getSupportedType() {
        return NoteTypeEnum.CHECKLIST;
    }

    @Override
    public void validateContent(String contentPart) throws ContentValidationException { //заходит не пустая, не null, не равная строке 'null' часть.
        try {
            List<String> itemStrings = parseItemStrings(contentPart);

            for (String itemString : itemStrings) {
                validateItemStructureAndContent(itemString);
            }

        } catch (IllegalArgumentException e) {
            logAndThrowContentValidationException(contentPart, e);
        }
    }

    protected List<String> parseItemStrings(String contentPart) {
        return Arrays.stream(contentPart.split(";", -1)).toList();
    }

    protected void validateItemStructureAndContent(String itemStr) {
        String[] itemParts = itemStr.split(":");
        FieldValidationUtil.validateFieldsCount(itemParts, EXPECTED_ITEM_FIELDS_COUNT);
        validateLineTrimmedAndNotBlank(itemParts);

        String text = itemParts[0];
        String isDoneStr = itemParts[1];

        DataValidationUtil.validateText(text, MIN_TEXT_LENGTH, MAX_TEXT_LENGTH);
        DataValidationUtil.validateBoolean(isDoneStr);
    }

    protected void validateLineTrimmedAndNotBlank(String[] itemParts) {
        for (String part : itemParts) {
            DataValidationUtil.validateNotBlank(part);
            DataValidationUtil.validateTrimmed(part);
        }
    }
}