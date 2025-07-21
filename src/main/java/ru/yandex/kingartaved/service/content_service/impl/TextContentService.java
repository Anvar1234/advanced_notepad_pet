package ru.yandex.kingartaved.service.content_service.impl;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.model.impl.TextContent;
import ru.yandex.kingartaved.dto.impl.TextContentDto;
import ru.yandex.kingartaved.service.content_service.ContentService;

public class TextContentService implements ContentService<TextContentDto> {
    @Override
    public TextContent createContent(TextContentDto validContentDto) {
        return new TextContent(validContentDto.text());
    }

    @Override
    public NoteTypeEnum getSupportedType() {
        return NoteTypeEnum.TEXT_NOTE;
    }
}
