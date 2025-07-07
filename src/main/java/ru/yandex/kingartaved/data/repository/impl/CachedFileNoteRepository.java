package ru.yandex.kingartaved.data.repository.impl;

import ru.yandex.kingartaved.data.model.Note;
import ru.yandex.kingartaved.data.repository.NoteRepository;
import ru.yandex.kingartaved.data.repository.db_connector.DbConnector;
import ru.yandex.kingartaved.data.serializer.NoteSerializer;
import ru.yandex.kingartaved.util.FileUtil;
import ru.yandex.kingartaved.util.LoggerUtil;
import ru.yandex.kingartaved.validation.db_line_validator.DbLineValidator;

import java.nio.file.Path;
import java.util.*;
import java.util.logging.Logger;

public class CachedFileNoteRepository implements NoteRepository {
    private static final Logger LOGGER = LoggerUtil.getLogger(CachedFileNoteRepository.class);

    private final DbConnector dbConnector;

    private final DbLineValidator dbLineValidator;

    private final NoteSerializer noteSerializer;

    private final Path pathToDbFile;

    private volatile Map<UUID, Note> notes = new LinkedHashMap<>();

    public CachedFileNoteRepository(DbConnector dbConnector, DbLineValidator dbLineValidator, NoteSerializer noteSerializer) {
        this.dbConnector = dbConnector;
        this.dbLineValidator = dbLineValidator;
        this.noteSerializer = noteSerializer;
        this.pathToDbFile = dbConnector.getPath();
    }

    protected void init() {
        dbConnector.initializeStorage();
        List<String> dbLines = FileUtil.readAll(pathToDbFile);
        dbLines.forEach(dbLineValidator::validateDbLine);
        dbLines.stream().map(noteSerializer::deserialize)
                .forEach(note -> notes.put(note.getMetadata().getId(), note));
    }

    @Override
    public Optional<Note> findById(UUID id) {
        return Optional.ofNullable(notes.get(id));
    }

    @Override
    public List<Note> findAll() {
        return new ArrayList<>(notes.values());
    }

    @Override
    public boolean saveToDb() {
        List<String> strings = notes.values()
                .stream()
                .map(noteSerializer::serialize)
                .toList();
        FileUtil.saveAll(pathToDbFile, strings);
        return true;
    }

    @Override
    public boolean delete(UUID id) {
        if(!notes.containsKey(id)) return false;
        notes.remove(id);
        return true;
    }

    @Override
    public boolean update(Note note) {
        UUID actualId = note.getMetadata().getId();
        notes.put(actualId, note);
        return true;
    }
}
