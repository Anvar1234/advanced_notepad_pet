package ru.yandex.kingartaved.util;

import ru.yandex.kingartaved.config.AppConfig;
import ru.yandex.kingartaved.exception.DbLineValidationException;
import ru.yandex.kingartaved.exception.constant.ErrorMessage;
import ru.yandex.kingartaved.validation.DataValidationUtil;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class DbLineSplitter {

    private static final String DB_FIELD_DELIMITER = AppConfig.DB_FIELD_DELIMITER;

    private static final Logger LOGGER = LoggerUtil.getLogger(DbLineSplitter.class);

    /**
     * Делит строку из БД на массив строк по {@value DB_FIELD_DELIMITER}).
     * @param lineFromDb Строка из БД в формате "id|title|createdAt|...|content".
     * @throws IllegalArgumentException если строка null, пуста или произошла ошибка во время
     * выполнения метода {@link String#split(String regex, int limit)}.
     */
    public static String[] splitDbLine(String lineFromDb) {
        try {
            DataValidationUtil.validateNotNull(lineFromDb);
            DataValidationUtil.validateNotBlank(lineFromDb);
            return lineFromDb.split(DB_FIELD_DELIMITER, -1);
        }catch (IllegalArgumentException e){
            String errorMessage = ErrorMessage.DB_LINE_SPLIT_ERROR.getMessage();
            String logMessage = errorMessage + ": " + "'\nПричина: " + e.getMessage();
            LOGGER.log(Level.SEVERE, logMessage, e);
            throw new IllegalArgumentException(errorMessage, e);
        }
    }
}
