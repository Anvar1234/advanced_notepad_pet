package ru.yandex.kingartaved.data.repository.db_connector;

import java.nio.file.Path;

public interface DbConnector {
    void initializeStorage();

    Path getPath();

    default void logAndThrowDbConnectionException() {

    }
}
