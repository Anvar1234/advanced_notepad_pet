package ru.yandex.kingartaved.validation.db_line_validator.metadata_validator;

import ru.yandex.kingartaved.exception.MetadataValidationException;

public interface MetadataValidator {
    void validateMetadata(String[] parts) throws MetadataValidationException;
}
