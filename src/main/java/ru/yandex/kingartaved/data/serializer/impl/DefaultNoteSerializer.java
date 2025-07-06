package ru.yandex.kingartaved.data.serializer.impl;

import ru.yandex.kingartaved.data.model.Content;
import ru.yandex.kingartaved.data.model.Metadata;
import ru.yandex.kingartaved.data.model.Note;
import ru.yandex.kingartaved.data.serializer.NoteSerializer;
import ru.yandex.kingartaved.data.serializer.content_serializer.ContentSerializer;
import ru.yandex.kingartaved.data.serializer.content_serializer.ContentSerializerRegistry;
import ru.yandex.kingartaved.data.serializer.metadata_serializer.MetadataSerializer;
import ru.yandex.kingartaved.util.DbLineSplitter;
import ru.yandex.kingartaved.validation.db_line_validator.DbLineValidator;

public class DefaultNoteSerializer implements NoteSerializer {
    private final ContentSerializerRegistry contentSerializerRegistry;
    private final MetadataSerializer metadataSerializer;
    private final DbLineValidator dbLineValidator;

    public DefaultNoteSerializer(ContentSerializerRegistry contentSerializerRegistry, MetadataSerializer metadataSerializer, DbLineValidator dbLineValidator) {
        this.contentSerializerRegistry = contentSerializerRegistry;
        this.metadataSerializer = metadataSerializer;
        this.dbLineValidator = dbLineValidator;
    }

    @Override
    public String serialize(Note note) { //TODO: добавить валидацию перед сериализацией, типа rawDataValidator. Или нет, если должны приходить точно корректные данные.
        Metadata metadata = note.getMetadata();
        Content content = note.getContent();
        ContentSerializer contentSerializer = contentSerializerRegistry.getSerializer(metadata.getType());

        String metadataPart = metadataSerializer.serializeMetadata(metadata);
        String contentPart = contentSerializer.serializeContent(content);

        return generateDbLine(metadataPart, contentPart);
    }

    private String generateDbLine(String metadataPart, String contentPart) {
        return String.join("|", metadataPart, contentPart);
    }

    @Override
    public Note deserialize(String lineFromDb) {
        dbLineValidator.validateDbLine(lineFromDb);
        String[] parts = DbLineSplitter.splitDbLine(lineFromDb);
        Metadata metadata = metadataSerializer.deserializeMetadata(parts);
        ContentSerializer contentSerializer = contentSerializerRegistry.getSerializer(metadata.getType());
        Content content = contentSerializer.deserializeContent(parts);
        return new Note(metadata, content);
    }
}