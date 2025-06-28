package ru.yandex.kingartaved.data.serializer.content_serializer;

import ru.yandex.kingartaved.data.model.Content;

public interface ContentSerializer {
    String serialize(Content note);
    Content deserialize(String str);
}
