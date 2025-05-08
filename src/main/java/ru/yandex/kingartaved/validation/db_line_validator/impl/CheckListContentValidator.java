package ru.yandex.kingartaved.validation.db_line_validator.impl;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.model.ChecklistItem;
import ru.yandex.kingartaved.validation.db_line_validator.ContentValidator;

import java.util.Arrays;
import java.util.List;

public class CheckListContentValidator implements ContentValidator {

    private static final int IS_DONE_INDEX = 1;

    @Override
    public NoteTypeEnum getSupportedType() {
        return NoteTypeEnum.CHECKLIST;
    }

    @Override
    public boolean isValidContent(String contentPart) {
        if (contentPart == null || contentPart.trim().isEmpty()) {
            return false;
        }

        try {
            List<ChecklistItem> items = parseChecklistItems(contentPart);
            return !items.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    private List<ChecklistItem> parseChecklistItems(String content) {
        return Arrays.stream(content.split(";"))
//                .map(String::trim) //TODO: может убрать чистку и фильтр? Нам же должны заходить валидные (консистентные?) строки из БД.
//                .filter(item -> !item.isEmpty())
                .map(this::parseChecklistItem)
                .toList();
    }

    private ChecklistItem parseChecklistItem(String itemStr) {
        // Ожидаем формат: "текст:статус" (например: "Купить молоко:false")
        String[] parts = itemStr.split(":");

        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid checklist item format");
        }

        ensureBoolean(parts[IS_DONE_INDEX], "isDone");

        String text = parts[0].trim();
        boolean isDone = Boolean.parseBoolean(parts[1].trim());
        return new ChecklistItem(text, isDone);
    }

    private void ensureBoolean(String part, String label) {
        if (!"true".equalsIgnoreCase(part) && !"false".equalsIgnoreCase(part)) {
            throw new IllegalArgumentException("Поле " + label + " должно быть true/false, но пришло: " + part);
        }
    }

}
