package ru.yandex.kingartaved.data.serializer.content_serializer.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.kingartaved.config.FieldIndex;
import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.model.Content;
import ru.yandex.kingartaved.data.model.impl.TextContent;
import ru.yandex.kingartaved.data.serializer.content_serializer.ContentSerializer;

public class TextContentSerializer implements ContentSerializer {
    private static final Logger log = LoggerFactory.getLogger(TextContentSerializer.class); //TODO: удалить или использовать.

    @Override
    public NoteTypeEnum getSupportedType() {
        return NoteTypeEnum.TEXT_NOTE;
    }

    @Override
    public String serializeContent(Content content) {
        if (content.getSupportedType() != getSupportedType()) {
            throw new IllegalArgumentException("Неподдерживаемый тип контента: " + content.getClass());
        }
        return ((TextContent) content).text();
    }

    @Override
    public TextContent deserializeContent(String[] parts) {
        return new TextContent(parts[FieldIndex.CONTENT.getIndex()]);
    }
}
