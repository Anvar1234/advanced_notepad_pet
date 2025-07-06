package ru.yandex.kingartaved.data.model.impl;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.model.ChecklistItem;
import ru.yandex.kingartaved.data.model.Content;

import java.util.List;

public final class ChecklistContent implements Content {
    private final List<ChecklistItem> tasks;

    public ChecklistContent(List<ChecklistItem> tasks) {
        this.tasks = List.copyOf(tasks); // Защита от мутации
    }

    public List<ChecklistItem> tasks() {
        return tasks;
    }

    @Override
    public NoteTypeEnum getSupportedType() {
        return NoteTypeEnum.CHECKLIST;
    }
}
