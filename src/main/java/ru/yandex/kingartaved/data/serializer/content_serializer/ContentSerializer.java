package ru.yandex.kingartaved.data.serializer.content_serializer;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.model.Content;

public interface ContentSerializer {
    NoteTypeEnum getSupportedType();
    String serialize(Content note);
    Content deserialize(String str);
}
