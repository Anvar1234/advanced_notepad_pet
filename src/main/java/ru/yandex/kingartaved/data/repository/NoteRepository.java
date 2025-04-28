package ru.yandex.kingartaved.data.repository;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий работает с сущностями из БД (БД - это список или мапа, заполняется маппированными сущностями из строк файла txt на данном этапе).
 */
public interface NoteRepository<T extends AbstractNote> {
    Optional<T> findById(UUID id);
    List<T> findAll();
    void save(T note);
    void delete(UUID id);
    void update(Path filePath, List<T> data);
}
