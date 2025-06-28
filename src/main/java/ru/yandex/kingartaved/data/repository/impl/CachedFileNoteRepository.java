package ru.yandex.kingartaved.data.repository.impl;

import ru.yandex.kingartaved.data.model.Note;
import ru.yandex.kingartaved.data.repository.NoteRepository;
import ru.yandex.kingartaved.data.repository.db_connector.DbConnector;
import ru.yandex.kingartaved.util.LoggerUtil;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

public class CachedFileNoteRepository implements NoteRepository {
    private static final Logger LOGGER = LoggerUtil.getLogger(CachedFileNoteRepository.class);

    private final List<Note> notes = findAll();

    public CachedFileNoteRepository(DbConnector dbConnector) {
        dbConnector.initializeStorage();
    }

    @Override
    public Optional<Note> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Note> findAll() {
        return List.of();
//       return FileUtil.readAll(PATH_TO_DB_FILE); //todo: сначала нужно провалидировать строки, потом десериализовать и превратить в Note.

    }

    @Override
    public boolean save(Note note) {
        return false;
    }

    @Override
    public void delete(UUID id) {

    }

    @Override
    public void update(List<Note> data) {

    }
}
