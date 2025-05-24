package ru.yandex.kingartaved.data.repository;

import ru.yandex.kingartaved.data.model.Note;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий работает с сущностями из БД (БД - это список или мапа, заполняется маппированными сущностями из строк файла txt на данном этапе).
 */
public interface NoteRepository{
    Optional<Note> findById(UUID id);
    List<Note> findAll();
    boolean save(Note note);
    void delete(UUID id);
    void update(List<Note> data);
}
