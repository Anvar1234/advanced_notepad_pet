package ru.yandex.kingartaved.validation.db_line_validator.impl;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.validation.db_line_validator.ContentValidator;

import java.util.Arrays;
import java.util.List;

public class CheckListContentValidator implements ContentValidator {
    @Override
    public NoteTypeEnum getSupportedType() {
        return NoteTypeEnum.CHECKLIST;
    }


    @Override
    public boolean isValidContent(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }

        List<String> items = Arrays.stream(value.split(";"))
                .map(String::trim)
                .filter(item -> !item.isEmpty())
                .toList();

        return !items.isEmpty();
    }
}
