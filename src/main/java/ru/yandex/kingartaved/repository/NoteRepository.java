package ru.yandex.kingartaved.repository;

import ru.yandex.kingartaved.data.model.Note;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий работает с сущностями из БД (БД - это список или мапа, заполняется маппированными сущностями из строк файла txt на данном этапе).
 */
public interface NoteRepository{
    Optional<Note> findById(UUID id);
    List<Note> findAll();
    void saveToCache(Note note); //TODO: мне кажется, сохранять в БД нужно только при выборе пользователем решения о выходе из приложения.
    boolean delete(UUID id);
    boolean update(Note note);
}
