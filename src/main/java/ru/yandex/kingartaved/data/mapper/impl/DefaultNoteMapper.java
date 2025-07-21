package ru.yandex.kingartaved.data.mapper.impl;

import ru.yandex.kingartaved.data.mapper.ContentMapperRegistry;
import ru.yandex.kingartaved.data.mapper.NoteMapper;
import ru.yandex.kingartaved.data.mapper.metadata_mapper.MetadataMapper;
import ru.yandex.kingartaved.data.model.Content;
import ru.yandex.kingartaved.data.model.Metadata;
import ru.yandex.kingartaved.data.model.Note;
import ru.yandex.kingartaved.dto.ContentDto;
import ru.yandex.kingartaved.dto.MetadataDto;
import ru.yandex.kingartaved.dto.NoteDto;

public class DefaultNoteMapper implements NoteMapper {

    private final MetadataMapper metadataMapper;
    private static final ContentMapperRegistry REGISTRY = new ContentMapperRegistry();

    public DefaultNoteMapper(MetadataMapper metadataMapper) {
        this.metadataMapper = metadataMapper;
    }

    public NoteDto mapEntityToDto(Note note) {
        Metadata metadata = note.getMetadata();
        Content content = note.getContent();

        MetadataDto metadataDto = metadataMapper.mapEntityToDto(metadata);
        ContentDto contentDto = REGISTRY.getMapper(metadata.getType()).mapEntityToDto(content);

        return new NoteDto(metadataDto, contentDto);
    }

    public Note mapDtoToEntity(NoteDto noteDto) {
        MetadataDto metadataDto = noteDto.metadataDto();
        ContentDto contentDto = noteDto.contentDto();

        Metadata metadata = metadataMapper.mapDtoToEntity(metadataDto);
        Content content = REGISTRY.getMapper(metadataDto.getType()).mapDtoToEntity(contentDto);

        return new Note(metadata, content);
    }
}
