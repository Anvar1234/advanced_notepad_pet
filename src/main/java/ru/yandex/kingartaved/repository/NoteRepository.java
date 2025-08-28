package ru.yandex.kingartaved.repository;

import ru.yandex.kingartaved.data.model.Note;

import java.util.List;
import java.util.UUID;

public interface NoteRepository{
//    Optional<Note> findById(UUID id);
    List<Note> findAll();
    boolean saveToCache(Note note); //TODO: мне кажется, сохранять в БД нужно только при выборе пользователем решения о выходе из приложения.
    boolean delete(UUID id);
    void saveToDB();
}
