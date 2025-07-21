package ru.yandex.kingartaved.service.content_service.impl;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.model.Content;
import ru.yandex.kingartaved.data.model.impl.TextContent;
import ru.yandex.kingartaved.service.content_service.ContentService;

public class TextContentService implements ContentService<TextContent> {
    @Override
    public Content createContent(Сщтеу type) {
        return new TextContent("");
    }

    @Override
    public NoteTypeEnum getSupportedType() {
        return NoteTypeEnum.TEXT_NOTE;
    }
}
