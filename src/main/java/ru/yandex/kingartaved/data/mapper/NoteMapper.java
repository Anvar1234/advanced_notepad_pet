package ru.yandex.kingartaved.data.mapper;

import ru.yandex.kingartaved.data.model.Content;
import ru.yandex.kingartaved.data.model.Metadata;
import ru.yandex.kingartaved.data.model.Note;
import ru.yandex.kingartaved.dto.ContentDto;
import ru.yandex.kingartaved.dto.MetadataDto;
import ru.yandex.kingartaved.dto.NoteDto;

public class NoteMapper {

    private static final ContentMapperRegistry REGISTRY = new ContentMapperRegistry();

    public static NoteDto mapEntityToDto(Note note) {
        Metadata metadata = note.getMetadata();
        Content content = note.getContent();

        MetadataDto metadataDto = MetadataMapper.mapEntityToDto(metadata);
        ContentDto contentDto = REGISTRY.getMapper(metadata.getType()).mapEntityToDto(content);

        return new NoteDto(metadataDto, contentDto);
    }

    public static Note mapDtoToEntity(NoteDto noteDto) {
        MetadataDto metadataDto = noteDto.metadataDto();
        ContentDto contentDto = noteDto.contentDto();

        Metadata metadata = MetadataMapper.mapDtoToEntity(metadataDto);
        Content content = REGISTRY.getMapper(metadataDto.getType()).mapDtoToEntity(contentDto);

        return new Note(metadata, content);
    }
}
