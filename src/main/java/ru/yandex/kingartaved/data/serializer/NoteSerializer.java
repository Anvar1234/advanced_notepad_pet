package ru.yandex.kingartaved.data.serializer;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.model.Note;

public interface NoteSerializer { //<T extends AbstractNote> {
    NoteTypeEnum getSupportedType();
    String serialize(Note note);
    Note deserialize(String str);
}
