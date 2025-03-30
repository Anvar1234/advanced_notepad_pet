package ru.yandex.kingartaved.data.serializer;

import ru.yandex.kingartaved.data.model.AbstractNote;

public interface NoteSerializer<T extends AbstractNote> {
    String serialize(T note);
    T deserialize(String str);
}
