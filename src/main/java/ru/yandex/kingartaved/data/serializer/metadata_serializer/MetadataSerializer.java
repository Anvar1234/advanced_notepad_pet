package ru.yandex.kingartaved.data.serializer.metadata_serializer;

import ru.yandex.kingartaved.data.model.Metadata;
import ru.yandex.kingartaved.data.model.Note;

public interface MetadataSerializer {
    String serializeMetadata(Metadata metadata);
    Metadata deserializeMetadata(String[] metadataParts);
}
