package ru.yandex.kingartaved.data.model.impl;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.model.ChecklistItem;
import ru.yandex.kingartaved.data.model.Content;

import java.util.Collections;
import java.util.List;

public class ChecklistContent implements Content {
    private final List<ChecklistItem> items;

    public ChecklistContent(List<ChecklistItem> items) {
        this.items = List.copyOf(items); // Защита от мутации
    }

    public List<ChecklistItem> items() {
        return items;
    }

    @Override
    public NoteTypeEnum getSupportedType() {
        return NoteTypeEnum.CHECKLIST;
    }
}
