package ru.yandex.kingartaved.util;

import ru.yandex.kingartaved.config.AppConfig;
import ru.yandex.kingartaved.exception.constant.ErrorMessage;
import ru.yandex.kingartaved.validation.DataValidationUtil;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class LineSplitter {

    private static final String DB_FIELD_DELIMITER = AppConfig.DB_FIELD_DELIMITER;

    private static final Logger LOGGER = LoggerUtil.getLogger(LineSplitter.class);

    public static String[] splitDbLine(String value) {
        try {
            DataValidationUtil.validateNotNull(value);
            DataValidationUtil.validateNotBlank(value);
            return value.split(DB_FIELD_DELIMITER, -1);
        }catch (IllegalArgumentException e){
            String errorMessage = ErrorMessage.DB_LINE_SPLIT_ERROR.getMessage();
            String logMessage = errorMessage + ": " + "'\nПричина: " + e.getMessage();
            LOGGER.log(Level.SEVERE, logMessage, e);
            throw new IllegalArgumentException(errorMessage, e);
        }
    }
}
