package ru.yandex.kingartaved.util.db_creator;

import ru.yandex.kingartaved.data.model.Note;

import java.nio.file.Path;
import java.util.List;

/**
 * При старте приложения загружать данные из файла в List<Note>.
 * Все операции (добавление, удаление, изменение) выполнять в памяти.
 *
 * Периодически сохранять изменения в файл:

 * При закрытии приложения.
 * После каждой критической операции (если важна актуальность).
 * По таймеру (например, раз в 5 минут).
 */

public interface DbCreator {

    void createDatabase();
    void deleteDatabase();
}
