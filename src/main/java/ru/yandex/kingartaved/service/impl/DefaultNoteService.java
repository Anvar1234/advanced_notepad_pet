package ru.yandex.kingartaved.service.impl;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.model.Content;
import ru.yandex.kingartaved.data.model.Metadata;
import ru.yandex.kingartaved.data.model.Note;
import ru.yandex.kingartaved.data.repository.NoteRepository;
import ru.yandex.kingartaved.dto.ContentDto;
import ru.yandex.kingartaved.dto.MetadataDto;
import ru.yandex.kingartaved.dto.NoteDto;
import ru.yandex.kingartaved.service.NoteService;
import ru.yandex.kingartaved.service.content_service.ContentService;
import ru.yandex.kingartaved.service.content_service.ContentServiceRegistry;
import ru.yandex.kingartaved.service.metadata_service.MetadataService;

public class DefaultNoteService implements NoteService {

    private final NoteRepository repository;
    private final MetadataService metadataService;
    private final ContentServiceRegistry contentServiceRegistry;


    public DefaultNoteService(
            NoteRepository repository,
            MetadataService metadataService,
            ContentServiceRegistry contentServiceRegistry
    ) {
        this.repository = repository;
        this.metadataService = metadataService;
        this.contentServiceRegistry = contentServiceRegistry;
    }

    @Override
    public Note createNote(NoteDto validNoteDto) {
        //todo: маппинг здесь (ДТО из контроллера дб валидным).
        MetadataDto validMetadataDto = validNoteDto.metadataDto();
        ContentDto validContentDto = validNoteDto.contentDto();
        NoteTypeEnum type = validMetadataDto.getType();

        Metadata metadata = metadataService.createMetadata(validMetadataDto);
        Content content = contentServiceRegistry.getContentService(type).createContent(validContentDto);

        Note note = new Note(metadata, content);

        repository.save(note);

        return note;
    }
}
