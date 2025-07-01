package ru.yandex.kingartaved.data.serializer.metadata_serializer;

import ru.yandex.kingartaved.data.model.Note;

public interface MetadataSerializer {
    String serializeMetadata(Note note);
    Note deserializeMetadata(String str);
}
