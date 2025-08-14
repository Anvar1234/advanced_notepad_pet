package ru.yandex.kingartaved.service.command_executor.constant;

public enum MetadataServiceCommandEnum {
    SET_TITLE("Изменить заголовок"),
    SET_REMIND("Установить напоминание"),
    SET_PINNED("Закрепить"),
    SET_STATUS("Изменить статус")
    ; //TODO: добавить поле description в Note, чтобы отображать краткое содержание поля contentDto

    /*
    create, add, read, readAll, update, delete.
И есть специфичные для работы с полем «List<CheckListItem> contentDto» у CheckList: addItem, removeItem, updateItem.

     */
    private final String description;

    MetadataServiceCommandEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
/*
NoteRepository {
    // CRUD-операции
    void save(Note note);          // Добавляет новую запись в файл
    void update(Note note);        // Обновляет существующую запись
    void delete(String noteId);    // Удаляет запись по ID
    Note findById(String noteId);  // Находит запись по ID
    List<Note> findAll();          // Возвращает все записи

 */