package ru.yandex.kingartaved.data.repository.impl;

import ru.yandex.kingartaved.config.AppConfig;
import ru.yandex.kingartaved.data.model.Note;
import ru.yandex.kingartaved.data.repository.NoteRepository;
import ru.yandex.kingartaved.data.repository.db_connetcor.DbConnector;
import ru.yandex.kingartaved.util.FileUtil;
import ru.yandex.kingartaved.util.LoggerUtil;
import ru.yandex.kingartaved.validation.db_line_validator.DbLineValidator;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

public class CachedFileNoteRepository implements NoteRepository {
    private static final Logger LOGGER = LoggerUtil.getLogger(CachedFileNoteRepository.class);

    private static final Path PATH_TO_DB_FILE = Path.of(AppConfig.PATH_TO_DB_FILE);
    private final List<Note> notes = findAll();

    public CachedFileNoteRepository(DbConnector dbConnector) {
        dbConnector.connectToDb();
    }

    @Override
    public Optional<Note> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Note> findAll() {
       return FileUtil.readAll(PATH_TO_DB_FILE); //todo: сначала нужно провалидировать строки, потом десериализовать и превратить в Note.

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
