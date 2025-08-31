package ru.yandex.kingartaved.service.impl;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.mapper.NoteMapper;
import ru.yandex.kingartaved.data.model.Content;
import ru.yandex.kingartaved.data.model.Metadata;
import ru.yandex.kingartaved.data.model.Note;
import ru.yandex.kingartaved.repository.NoteRepository;
import ru.yandex.kingartaved.dto.NoteDto;
import ru.yandex.kingartaved.dto.request.CreateNewNoteRequestDto;
import ru.yandex.kingartaved.service.NoteService;
import ru.yandex.kingartaved.service.content_service.ContentServiceRegistry;
import ru.yandex.kingartaved.service.metadata_service.MetadataService;
import ru.yandex.kingartaved.service.sorting.SortOrder;
import ru.yandex.kingartaved.service.sorting.UserSortingSettingsRepository;

import java.util.List;
import java.util.UUID;
//todo: в будущем логику перенести из вьюшки
public class DefaultNoteService implements NoteService {

    private final NoteMapper noteMapper;
    private final NoteRepository repository;
    private final MetadataService metadataService;
    private final ContentServiceRegistry contentServiceRegistry;
    private final UserSortingSettingsRepository settingsRepository;

    public DefaultNoteService(
            NoteMapper noteMapper,
            NoteRepository repository,
            MetadataService metadataService,
            ContentServiceRegistry contentServiceRegistry,
            UserSortingSettingsRepository settingsRepository
    ) {
        this.noteMapper = noteMapper;
        this.repository = repository;
        this.metadataService = metadataService;
        this.contentServiceRegistry = contentServiceRegistry;
        this.settingsRepository = settingsRepository;
    }

    @Override
    public boolean updateNote(NoteDto updatedNoteDto) {
        Note updatedNote = noteMapper.mapDtoToEntity(updatedNoteDto);
        return repository.saveToCache(updatedNote);
    }

    @Override
    public NoteDto createNote(CreateNewNoteRequestDto createNewNoteRequestDto) {
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
                .sorted(settingsRepository.getSortOrder().toComparator())
                .map(noteMapper::mapEntityToDto)
                .toList();
    }

    @Override
    public boolean deleteNote(UUID id) {
        return repository.delete(id);
    }

    @Override
    public void close() {
        repository.saveToDB();
    }

    public boolean setSortOrder(SortOrder.SortField field, SortOrder.SortDirection direction) {
        SortOrder order = new SortOrder(field, direction);
        try {
            settingsRepository.saveSortOrder(order);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка при изменении сортировки: " + e.getMessage());
        }
        return false;
    }
}
