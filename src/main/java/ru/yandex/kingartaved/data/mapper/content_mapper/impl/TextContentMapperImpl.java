package ru.yandex.kingartaved.data.mapper.content_mapper.impl;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.mapper.content_mapper.ContentMapper;
import ru.yandex.kingartaved.data.model.impl.TextContent;
import ru.yandex.kingartaved.dto.impl.TextContentDto;

public class TextContentMapperImpl implements ContentMapper<TextContent, TextContentDto>{

    @Override
    public TextContentDto mapEntityToDto(TextContent content) {
        return new TextContentDto(content.text());
    }

    @Override
    public TextContent mapDtoToEntity(TextContentDto contentDto) {
        return new TextContent(contentDto.text());
    }

    @Override
    public NoteTypeEnum getSupportedType() {
        return NoteTypeEnum.TEXT_NOTE;
    }
}