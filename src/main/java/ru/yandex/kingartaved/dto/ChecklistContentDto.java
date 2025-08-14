package ru.yandex.kingartaved.dto;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;

import java.util.List;
import java.util.Objects;

public final class ChecklistContentDto extends ContentDto {
    private final List<ChecklistItemDto> tasks;

    public ChecklistContentDto(List<ChecklistItemDto> tasks) {
        this.tasks = tasks;
    }

    @Override
    public NoteTypeEnum getSupportedType() {
        return NoteTypeEnum.CHECKLIST;
    }

    public List<ChecklistItemDto> tasks() {
        return List.copyOf(tasks);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ChecklistContentDto) obj;
        return Objects.equals(this.tasks, that.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tasks);
    }

    @Override
    public String toString() {
        return "ChecklistContentDto[" +
                "tasks=" + tasks + ']';
    }

}