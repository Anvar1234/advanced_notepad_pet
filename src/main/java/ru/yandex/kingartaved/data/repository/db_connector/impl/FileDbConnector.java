package ru.yandex.kingartaved.data.repository.db_connector.impl;

import ru.yandex.kingartaved.config.AppConfig;
import ru.yandex.kingartaved.data.repository.db_connector.DbConnector;
import ru.yandex.kingartaved.exception.FileStorageException;
import ru.yandex.kingartaved.exception.constant.ErrorMessage;
import ru.yandex.kingartaved.util.FileUtil;
import ru.yandex.kingartaved.util.LoggerUtil;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public enum FileDbConnector implements DbConnector {

    INSTANCE;

    private static final Logger LOGGER = LoggerUtil.getLogger(FileDbConnector.class);
    private static final String STRING_PATH = AppConfig.PATH_TO_DB_FILE;
    private Path pathToDb;

    @Override
    public void initializeFileStorage() {
        ensurePath();
        FileUtil.createFile(pathToDb);
    }

    private void ensurePath() {
        try {
            this.pathToDb = Path.of(STRING_PATH);
        } catch (InvalidPathException e) {
            String errorMessage = ErrorMessage.PATH_ERROR.getMessage() + ": " + STRING_PATH + "\nПричина: " + e.getMessage();
            LOGGER.log(Level.SEVERE, errorMessage, e);
            throw new FileStorageException(ErrorMessage.PATH_ERROR.getMessage(), e);
        }
    }
}