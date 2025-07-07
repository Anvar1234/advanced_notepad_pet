package ru.yandex.kingartaved.data.serializer.content_serializer;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.model.Content;
import ru.yandex.kingartaved.exception.constant.ErrorMessage;
import ru.yandex.kingartaved.util.LoggerUtil;

import java.util.logging.Level;
import java.util.logging.Logger;

public interface ContentSerializer {
    NoteTypeEnum getSupportedType();
    String serializeContent(Content content);
    Content deserializeContent(String[] contentPart);
}
