package ru.yandex.kingartaved.data.repository.db_connector.impl;

import ru.yandex.kingartaved.config.AppConfig;
import ru.yandex.kingartaved.data.repository.db_connector.DbConnector;
import ru.yandex.kingartaved.exception.FileConnectionException;
import ru.yandex.kingartaved.exception.FileOperationException;
import ru.yandex.kingartaved.exception.ValidationException;
import ru.yandex.kingartaved.exception.constant.ErrorMessage;
import ru.yandex.kingartaved.util.FileUtil;
import ru.yandex.kingartaved.util.LoggerUtil;
import ru.yandex.kingartaved.validation.DataValidationUtil;

import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public enum FileDbConnector implements DbConnector {

    INSTANCE;

    private static final Logger LOGGER = LoggerUtil.getLogger(FileDbConnector.class);
    private static final String STRING_PATH = AppConfig.PATH_TO_DB_FILE;

    @Override
    public void initializeFileStorage() {
        try {
            Path pathToDb = DataValidationUtil.validateAndGetPath(STRING_PATH);
            FileUtil.createFile(pathToDb);
        } catch (IllegalArgumentException | ValidationException | FileOperationException e) {
            String errorMessage = ErrorMessage.FILE_CONNECTION_ERROR.getMessage();
            String logMessage = errorMessage + ": " + STRING_PATH + "\nПричина: " + e.getMessage();
            LOGGER.log(Level.SEVERE, logMessage, e);
            throw new FileConnectionException(errorMessage, e);
        }
    }
}