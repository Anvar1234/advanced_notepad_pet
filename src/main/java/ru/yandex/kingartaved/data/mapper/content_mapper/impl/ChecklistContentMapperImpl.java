package ru.yandex.kingartaved.data.mapper.content_mapper.impl;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.mapper.content_mapper.ContentMapper;
import ru.yandex.kingartaved.data.model.ChecklistTask;
import ru.yandex.kingartaved.data.model.ChecklistContent;
import ru.yandex.kingartaved.dto.ChecklistTaskDto;
import ru.yandex.kingartaved.dto.ChecklistContentDto;

import java.util.stream.Collectors;

public class ChecklistContentMapperImpl implements ContentMapper<ChecklistContent, ChecklistContentDto> {

    //TODO: неужели необходимо делать маппер и для айтемов внутри чеклиста?
    @Override
    public ChecklistContentDto mapEntityToDto(ChecklistContent content) {
        return new ChecklistContentDto(content.tasks().stream()
                .map(item -> new ChecklistTaskDto(item.getText(), item.isDone()))
                .collect(Collectors.toList()));
    }

    @Override
    public ChecklistContent mapDtoToEntity(ChecklistContentDto contentDto) {
        return new ChecklistContent(contentDto.tasks().stream()
                .map(itemDto -> new ChecklistTask(itemDto.text(), itemDto.isDone()))
                .collect(Collectors.toList()));

    }

    @Override
    public NoteTypeEnum getSupportedType() {
        return NoteTypeEnum.CHECKLIST;
    }
}
