package ru.yandex.kingartaved.data.serializer.metadata_serializer.impl;

import ru.yandex.kingartaved.data.model.Note;
import ru.yandex.kingartaved.data.serializer.metadata_serializer.MetadataSerializer;

public class DefaultMetadataSerializer implements MetadataSerializer {

    @Override
    public String serializeMetadata(Note note) {
        return "";
    }

    @Override
    public Note deserializeMetadata(String str) {


        return null;
    }
}
