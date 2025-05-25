package ru.yandex.kingartaved.validation.db_line_validator.content_validator.impl;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.model.ChecklistItem;
import ru.yandex.kingartaved.exception.ContentValidationException;
import ru.yandex.kingartaved.exception.constant.ErrorMessage;
import ru.yandex.kingartaved.util.LoggerUtil;
import ru.yandex.kingartaved.validation.db_line_validator.content_validator.ContentValidator;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChecklistContentValidator implements ContentValidator {
    private static final Logger LOGGER = LoggerUtil.getLogger(ChecklistContentValidator.class);
    private static final int IS_DONE_INDEX = 1;

    @Override
    public NoteTypeEnum getSupportedType() {
        return NoteTypeEnum.CHECKLIST;
    }

    @Override
    public void validateContent(String contentPart) throws ContentValidationException {
        try {
            if (contentPart == null || contentPart.trim().isEmpty()) {
                throw new IllegalArgumentException("Содержимое заметки типа " + getSupportedType() + " не может быть null или пустым");
            }

            if ("null".equalsIgnoreCase(contentPart)) {
                throw new IllegalArgumentException("Поле контента заметки типа " + getSupportedType() + " содержит строку 'null'");
            }

            List<ChecklistItem> items = parseChecklistItems(contentPart);
            if (items.isEmpty()) {
                throw new IllegalArgumentException("Список задач не содержит элементов");
            }
        } catch (IllegalArgumentException e) {
            String errorMessage = ErrorMessage.CONTENT_VALIDATION_ERROR.getMessage() + ": '" + contentPart + "'\nПричина: " + e.getMessage();
            LOGGER.log(Level.SEVERE, errorMessage, e);
            throw new ContentValidationException(errorMessage, e);
        }
    }

    private List<ChecklistItem> parseChecklistItems(String content) {
        return Arrays.stream(content.split(";"))
                .map(this::parseChecklistItem)
                .toList();
    }

    private ChecklistItem parseChecklistItem(String itemStr) {
        String[] parts = itemStr.split(":");

        if (parts.length != 2) {
            throw new IllegalArgumentException("Неверный формат элемента чек-листа: " + itemStr);
        }

        String text = parts[0].trim();
        String isDoneStr = parts[1].trim();

        validateBoolean(isDoneStr, "isDone");

        boolean isDone = Boolean.parseBoolean(isDoneStr);
        return new ChecklistItem(text, isDone);
    }

    private void validateBoolean(String part, String label) {
        if (!"true".equalsIgnoreCase(part) && !"false".equalsIgnoreCase(part)) {
            throw new IllegalArgumentException("Поле " + label + " должно быть true/false, но пришло: " + part);
        }
    }
}