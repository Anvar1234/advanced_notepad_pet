package ru.yandex.kingartaved.service.command_executor.constant;

public enum NoteServiceCommandEnum {
    CREATE("Создать новую заметку"), //перед созданием юзер выбирает тип заметки (список, чеклист), title тоже устанавливается здесь.
    READ("Прочесть заметку"), //
    UPDATE("Изменить заметку"),
    REMOVE("Удалить")
    ; //TODO: добавить поле description в Note, чтобы отображать краткое содержание поля contentDto

    /*
    create, add, read, readAll, update, delete.
И есть специфичные для работы с полем «List<CheckListItem> contentDto» у CheckList: addItem, removeItem, updateItem.

     */
    private final String description;

    NoteServiceCommandEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        CREATE.getClass();
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