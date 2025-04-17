package ru.yandex.kingartaved.data.model.impl;

import ru.yandex.kingartaved.data.model.ChecklistItem;

import java.util.Collections;
import java.util.List;

public class ChecklistContent {
    private final List<ChecklistItem> items;

    public ChecklistContent(List<ChecklistItem> items) {
        this.items = Collections.unmodifiableList(items);
    }

    public List<ChecklistItem> getItems() {
        return items;
    }
}
