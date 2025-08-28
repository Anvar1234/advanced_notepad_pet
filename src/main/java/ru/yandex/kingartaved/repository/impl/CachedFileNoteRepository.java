package ru.yandex.kingartaved.repository.impl;

import ru.yandex.kingartaved.data.model.Metadata;
import ru.yandex.kingartaved.data.model.Note;
import ru.yandex.kingartaved.repository.NoteRepository;
import ru.yandex.kingartaved.repository.db_connector.DbConnector;
import ru.yandex.kingartaved.data.serializer.NoteSerializer;
import ru.yandex.kingartaved.util.FileUtil;
import ru.yandex.kingartaved.util.LoggerUtil;
import ru.yandex.kingartaved.validation.db_line_validator.DbLineValidator;

import java.nio.file.Path;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CachedFileNoteRepository implements NoteRepository {
    private static final Logger LOGGER = LoggerUtil.getLogger(CachedFileNoteRepository.class);

    private final DbConnector dbConnector;

    private final DbLineValidator dbLineValidator;

    private final NoteSerializer noteSerializer;

    private final Path pathToDbFile;

    private final Map<UUID, Note> notes = new LinkedHashMap<>();

    public CachedFileNoteRepository(DbConnector dbConnector, DbLineValidator dbLineValidator, NoteSerializer noteSerializer) {
        this.dbConnector = dbConnector;
        this.dbLineValidator = dbLineValidator;
        this.noteSerializer = noteSerializer;
        this.pathToDbFile = dbConnector.getPath();
        init();
    }

    protected void init() {
        dbConnector.initializeStorage();
        List<String> dbLines = FileUtil.readAll(pathToDbFile);
        dbLines.forEach(dbLineValidator::validateDbLine);
        dbLines.stream().map(noteSerializer::deserialize)
                .forEach(note -> notes.put(note.getMetadata().getId(), note));
        LOGGER.log(Level.INFO, "Репозиторий инициализирован");
    }

//    @Override
//    public Optional<Note> findById(UUID id) {
//        return Optional.ofNullable(notes.get(id));
//    }

    @Override
    public List<Note> findAll() {
        return new ArrayList<>(notes.values());
    }

    @Override
    public boolean saveToCache(Note note) {
        Objects.requireNonNull(note, "Note is required");
        Metadata metadata = note.getMetadata();
        Objects.requireNonNull(metadata, "Note metadata is required");
        UUID id = metadata.getId();
        Objects.requireNonNull(id, "Note ID is required");

        notes.put(id, note);
        return true;
    }

    @Override
    public boolean delete(UUID id) {
        if (!notes.containsKey(id)) return false;
        notes.remove(id);
        return true;
    }

    @Override
    public void saveToDB() {
        List<String> strings = notes.values()
                .stream()
                .map(noteSerializer::serialize)
                .toList();
        FileUtil.saveAll(pathToDbFile, strings);
    }

//    @Override
//    public boolean update(UUID id, Note note) {
//        Optional<Note> optionalNote = findById(id);
//        if (optionalNote.isEmpty()) {
//            return false;
//        }
//        saveToCache(note);
//        return true;
//    }
}
