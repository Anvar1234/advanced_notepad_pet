package ru.yandex.kingartaved.validation.db_line_validator.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.kingartaved.data.constant.FieldIndex;
import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.exception.DbLineValidationException;
import ru.yandex.kingartaved.validation.db_line_validator.content_validator.ContentValidatorRegistry;
import ru.yandex.kingartaved.exception.ContentValidationException;
import ru.yandex.kingartaved.validation.db_line_validator.content_validator.ContentValidator;
import ru.yandex.kingartaved.validation.db_line_validator.metadata_validator.MetadataValidator;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DefaultDbLineValidatorTest {

    private static final String DB_FIELD_DELIMITER = "\\|";

    @Spy
    private ContentValidatorRegistry mockContentValidatorRegistry;

    @Mock
    private ContentValidator mockContentValidator;

    @Mock
    private MetadataValidator mockMetadataValidator;

    @InjectMocks
    private DefaultDbLineValidator defaultDbLineValidator;

    /**
     * Проверяет, что при обработке контента заметок типа TEXT_NOTE
     * происходит запрос валидатора именно для этого типа.
     * <p>
     * Использует моки для изоляции от реальных реализаций валидаторов.
     */
    @Test
    @DisplayName("Проверка факта запроса валидатора для TEXT_NOTE при обработке контента")
    void validateContent_callsTextNoteContentValidator_success() throws ContentValidationException {
        // given
        when(mockContentValidatorRegistry.getValidator(NoteTypeEnum.TEXT_NOTE))
                .thenReturn(mockContentValidator);
        doNothing().when(mockContentValidator).validateContent(any(String[].class));

        String[] parts = new String[10];
        Arrays.fill(parts, "value");
        parts[8] = "TEXT_NOTE";
        parts[9] = "Sample text";

        // when
        for (int i = 0; i < 2; i++) {
            defaultDbLineValidator.validateNoteContent(parts);
        }

        // then
        verify(mockContentValidatorRegistry, times(2)).getValidator(NoteTypeEnum.TEXT_NOTE);
        verify(mockContentValidator, times(2)).validateContent(eq(parts));
    }

    @ParameterizedTest
    @MethodSource("provideContentTestCases")
    @DisplayName("Проверка валидации содержимого поля content (успешные и неуспешные сценарии)")
    void validateContent_validAndInvalidNoteContent_cases(String noteType, String content, boolean isValid) {
        //given
        String[] parts = new String[10];
        Arrays.fill(parts, "value");
        parts[8] = noteType;
        parts[9] = content;

        //when
        Executable actual = () -> defaultDbLineValidator.validateNoteContent(parts);

        //then
        if (isValid) {
            assertDoesNotThrow(actual);
        } else {
            assertThrows(ContentValidationException.class, actual);
        }
    }

    private static Stream<Arguments> provideContentTestCases() {
        return Stream.of(
                Arguments.of("TEXT_NOTE", "Обычный текст", true),
                Arguments.of("TEXT_NOTE", "", false),
                Arguments.of("CHECKLIST", "Пункт1:true;Пункт2:false", true),
                Arguments.of("CHECKLIST", "invalid-format", false)
        );
    }

    @ParameterizedTest(name = "{1}")
    @CsvFileSource(
            resources = "/db_line_validator/validateDbLineStructure_extraSpacesPresent_failed.csv",
            numLinesToSkip = 1)
    @DisplayName("Проверка валидации некорректных строк из БД с лишними пробелами")
    void validateLineStructure_extraSpacesPresent_failed(String lineFromCsv, String description) {
        //given
        String[] parts = lineFromCsv.split(DB_FIELD_DELIMITER);

        //when
        Executable actual = () -> defaultDbLineValidator.validateLineStructure(parts, FieldIndex.REMIND_AT.getIndex());

        //then
        assertThrows(IllegalArgumentException.class, actual, description + " , строка должна быть невалидна");
    }

    @ParameterizedTest(name = "Валидация строки из БД для заметки типа: {1}")
    @CsvSource({
            // Корректная строка из БД для заметок типа TEXT_NOTE
            "f47ac10b-58cc-4372-a567-0e02b2c3d479|Заметка 1|2023-10-01T12:34:56|2023-10-01T15:34:56|null|true|HIGH|ACTIVE|TEXT_NOTE|Содержимое,TEXT_NOTE",
            // Корректная строка из БД для заметок типа CHECKLIST
            "f47ac10b-58cc-4372-a567-0e02b2c3d479|Заметка 2|2023-10-01T12:34:56|2023-10-01T15:34:56|null|true|BASE|ACTIVE|CHECKLIST|1задача:true;задача2:false;3:false,CHECKLIST"
    })
    @DisplayName("Проверка структурной целостности корректных строк из БД")
    void validateLineStructure_success(String lineFromCsv, String noteType) {
        //given
        String[] parts = lineFromCsv.split(DB_FIELD_DELIMITER);

        //when
        Executable actual = () -> defaultDbLineValidator.validateLineStructure(parts, FieldIndex.REMIND_AT.getIndex());

        //then
        assertDoesNotThrow(actual, "Для " + noteType + " строка должна быть валидна");
    }

    @ParameterizedTest(name = "{0}")
    @CsvFileSource(
            resources = "/db_line_validator/isValidDbLine_validAndInvalidDbLines_cases.csv",
            numLinesToSkip = 1)
    @DisplayName("Проверка структурной целостности строк")
    void validateAndInvalidDbLine_cases(String description, String lineFromCsv, boolean expectedValid) {

        // given
        // lineFromCsv - валидная или невалидная строка из CSV

        //when
        Executable actual = () -> defaultDbLineValidator.validateDbLine(lineFromCsv);

        //then
        if (expectedValid) {
            Assertions.assertDoesNotThrow(actual);
        } else {
            Assertions.assertThrows(DbLineValidationException.class, actual);
        }
    }
}

