package ru.yandex.kingartaved.validation.db_line_validator.content_validator.impl;

import ru.yandex.kingartaved.config.AppConfig;
import ru.yandex.kingartaved.data.constant.FieldIndex;
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
    private static final int EXPECTED_TASK_FIELDS_COUNT = 2;
    private static final int MIN_TEXT_LENGTH = AppConfig.MIN_TEXT_LENGTH;
    private static final int MAX_TEXT_LENGTH = AppConfig.MAX_TEXT_LENGTH;

    @Override
    public NoteTypeEnum getSupportedType() {
        return NoteTypeEnum.CHECKLIST;
    }

    @Override
    public void validateContent(String[] parts) throws ContentValidationException { //заходит не пустая, не null, не равная строке 'null' часть.
        try {
            List<String> taskStrings = parseTaskStrings(parts[FieldIndex.CONTENT.getIndex()]);

            for (String taskString : taskStrings) {
                validateTaskStructureAndContent(taskString);
            }

        } catch (IllegalArgumentException e) {
            logAndThrowContentValidationException(parts[FieldIndex.CONTENT.getIndex()], e);
        }
    }

    protected List<String> parseTaskStrings(String contentPart) {
        return Arrays.stream(contentPart.split(";")).toList(); // TODO: Без "-1", т.к. не берем последний пустой элемент (который после последнего ";" в строке вида CHECKLIST|1задача:true;задача2:false;)
    }

    protected void validateTaskStructureAndContent(String taskStr) {
        String[] taskParts = taskStr.split(":");
        FieldValidationUtil.validateFieldsCount(taskParts, EXPECTED_TASK_FIELDS_COUNT);
        validateLineTrimmedAndNotBlank(taskParts);

        String text = taskParts[0];
        String isDoneStr = taskParts[1];

        DataValidationUtil.validateText(text, MIN_TEXT_LENGTH, MAX_TEXT_LENGTH);
        DataValidationUtil.validateBoolean(isDoneStr);
    }

    protected void validateLineTrimmedAndNotBlank(String[] taskParts) {
        for (String part : taskParts) {
            DataValidationUtil.validateNotBlank(part);
            DataValidationUtil.validateTrimmed(part);
        }
    }
}