package ru.yandex.kingartaved.dto.impl;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.dto.ChecklistItemDto;
import ru.yandex.kingartaved.dto.ContentDto;

import java.util.List;

public record ChecklistContentDto(List<ChecklistItemDto> tasks) implements ContentDto {

    @Override
    public NoteTypeEnum getSupportedType() {
        return NoteTypeEnum.CHECKLIST;
    }
}