package ru.yandex.kingartaved.data.repository;

import ru.yandex.kingartaved.data.model.AbstractNote;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий работает с сущностями из БД (БД - это список или мапа, заполняется маппированными сущностями из строк файла txt на данном этапе).
 */
public interface NoteRepository {
    Optional<AbstractNote> findById(UUID id); // TODO: репозиторий пусть работает со строками, сервис же преобразует в сущность и обратно и возвращает в репозиторий.
    List<AbstractNote> findAll();
    AbstractNote save(AbstractNote note);
    void delete(UUID id);
    void update(Path filePath, List<AbstractNote> data);
}
