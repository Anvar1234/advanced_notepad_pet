package ru.yandex.kingartaved.service.impl;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.mapper.NoteMapper;
import ru.yandex.kingartaved.data.model.Content;
import ru.yandex.kingartaved.data.model.Metadata;
import ru.yandex.kingartaved.data.model.Note;
import ru.yandex.kingartaved.data.repository.NoteRepository;
import ru.yandex.kingartaved.dto.NoteDto;
import ru.yandex.kingartaved.dto.request.CreateNewNoteRequestDto;
import ru.yandex.kingartaved.service.NoteService;
import ru.yandex.kingartaved.service.content_service.ContentServiceRegistry;
import ru.yandex.kingartaved.service.metadata_service.MetadataService;

import java.util.List;

public class DefaultNoteService implements NoteService {

    private final NoteMapper noteMapper;
    private final NoteRepository repository;
    private final MetadataService metadataService; //todo: возможно удалить
    private final ContentServiceRegistry contentServiceRegistry; //todo: возможно удалить


    public DefaultNoteService(
            NoteMapper noteMapper,
            NoteRepository repository,
            MetadataService metadataService,
            ContentServiceRegistry contentServiceRegistry
    ) {
        this.noteMapper = noteMapper;
        this.repository = repository;
        this.metadataService = metadataService;
        this.contentServiceRegistry = contentServiceRegistry;
    }

    @Override
    public NoteDto createNote(CreateNewNoteRequestDto createNewNoteRequestDto) { //todo: верно ли, что из контроллера должен приходить валидный ДТО?
        NoteTypeEnum type = createNewNoteRequestDto.createNewMetadataRequestDto().type();

        Metadata metadata = metadataService.createMetadata(createNewNoteRequestDto.createNewMetadataRequestDto());
        Content content = contentServiceRegistry.getContentService(type).createContent(createNewNoteRequestDto.contentDto());
        Note note = new Note(metadata, content);

        repository.saveToCache(note);

        return noteMapper.mapEntityToDto(note);
    }

    @Override
    public List<NoteDto> readAllNotes() {
        return repository.findAll().stream()
                .map(noteMapper::mapEntityToDto)
                .toList();
    }

}
