package ru.yandex.kingartaved.util.db_creator.impl;

import ru.yandex.kingartaved.data.model.Note;
import ru.yandex.kingartaved.util.db_creator.DbCreator;

import java.nio.file.Path;
import java.util.List;

/**
 * TODO: возможно сделать синглтон, чтобы только одна БД текстНоте и одна Чеклист была создана.
 */
public final class InMemoryDbCreator { // implements DbCreator {

    private final Path pathToDb;
    private List<Note> dataBase;

    public InMemoryDbCreator(Path pathToDb) {
        this.pathToDb = pathToDb;
    }

//    public List<Note> createDatabase() {
//        FileUtil.createFile(pathToDb);
//        List<String> uploaded = FileUtil.readAll(pathToDb);
//
//
//    }
}
