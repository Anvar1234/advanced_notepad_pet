package ru.yandex.kingartaved.service.impl;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.model.Content;
import ru.yandex.kingartaved.data.model.Metadata;
import ru.yandex.kingartaved.data.model.Note;
import ru.yandex.kingartaved.data.repository.NoteRepository;
import ru.yandex.kingartaved.dto.NoteDto;
import ru.yandex.kingartaved.dto.request.CreateNewNoteDto;
import ru.yandex.kingartaved.service.NoteService;
import ru.yandex.kingartaved.service.content_service.ContentService;
import ru.yandex.kingartaved.service.metadata_service.MetadataService;
import ru.yandex.kingartaved.service.metadata_service.impl.DefaultMetadataService;

public class DefaultNoteService implements NoteService {

    private final NoteRepository repository;
    private final MetadataService metadataService;

    public DefaultNoteService(NoteRepository repository, MetadataService metadataService) {
        this.repository = repository;
        this.metadataService = metadataService;
    }

    @Override
    public Note createNote(NoteDto noteDto) {




        return note;
    }
}
