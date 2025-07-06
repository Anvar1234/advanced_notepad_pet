package ru.yandex.kingartaved.validation.db_line_validator.content_validator;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.exception.ContentValidationException;
import ru.yandex.kingartaved.exception.constant.ErrorMessage;
import ru.yandex.kingartaved.util.LoggerUtil;

import java.util.logging.Level;
import java.util.logging.Logger;

public interface ContentValidator {
    NoteTypeEnum getSupportedType();

    void validateContent(String[] parts) throws ContentValidationException;

    default Logger getLogger() {
        return LoggerUtil.getLogger(this.getClass());
    }

    default void logAndThrowContentValidationException(
            String contentPart,
            IllegalArgumentException cause
    ) throws ContentValidationException {
        String errorMessage = ErrorMessage.CONTENT_VALIDATION_ERROR.getMessage();
        Logger logger = getLogger();
        String logMessage = String.format(
                "%s для заметки типа %s: '%s'\nПричина: %s",
                errorMessage,
                getSupportedType(),
                contentPart,
                cause.getMessage()
        );

        logger.log(Level.SEVERE, logMessage, cause);
        throw new ContentValidationException(errorMessage, cause);
    }
}
