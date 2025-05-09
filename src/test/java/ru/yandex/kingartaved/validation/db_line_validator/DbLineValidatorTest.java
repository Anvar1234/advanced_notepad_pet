package ru.yandex.kingartaved.validation.db_line_validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class DbLineValidatorTest {

    private static final int EXPECTED_FIELDS_COUNT = 10;
    private static final String DB_FIELD_DELIMITER = "\\|";
    private static final ContentValidatorRegistry CONTENT_VALIDATOR_REGISTRY = new ContentValidatorRegistry();

    private static DbLineValidator validator;

    @BeforeAll
    static void init() {
        validator = new DbLineValidator();
    }

    @ParameterizedTest(name = "{1}")
    @CsvFileSource(
            resources = "/validate_db_line_structure_test_cases_should_fail.csv",
            numLinesToSkip = 1)
    @DisplayName("Проверка валидации некорректных строк из БД с лишними пробелами")
    void validateDbLineStructure_shouldFail_whenExtraSpacesPresent(String lineFromCsv, String description) {
        //given
        String[] parts = lineFromCsv.split(DB_FIELD_DELIMITER);

        //when
        Executable actual = () -> validator.validateDbLineStructure(parts);

        //then
        assertThrows(IllegalArgumentException.class, actual, description + " , строка должна быть невалидна");
    }


    @ParameterizedTest(name = "Валидация строки из БД для заметки типа: {1}")
    @CsvSource({
            // Корректная строка из БД для заметок типа TEXT_NOTE
            "f47ac10b-58cc-4372-a567-0e02b2c3d479|Заметка 1|2023-10-01T12:34:56|2023-10-01T15:34:56|null|true|HIGH|ACTIVE|TEXT_NOTE|Содержимое,TEXT_NOTE",
            // Корректная строка из БД для заметок типа CHECKLIST
            "f47ac10b-58cc-4372-a567-0e02b2c3d479|Заметка 2|2023-10-01T12:34:56|2023-10-01T15:34:56|null|true|BASE|ACTIVE|CHECKLIST|1dsdsf:true;2dsfsf:false; 3d:false,CHECKLIST"
    })
    @DisplayName("Проверка валидации корректных строк БД")
    void validateDbLineStructure_shouldSucceed_whenLineIsCorrect(String lineFromCsv, String noteType) {
        //given
        String[] parts = lineFromCsv.split(DB_FIELD_DELIMITER);

        //when
        Executable actual = () -> validator.validateDbLineStructure(parts);

        //then
        assertDoesNotThrow(actual, "Для " + noteType + " строка должна быть валидна");
    }

    @ParameterizedTest
    @MethodSource("provideContentTestCases")
    @DisplayName("Проверка валидации содержимого поля content")
    void requireContent_shouldValidate(String noteType, String content, boolean isValid) {
        //given
        String[] parts = new String[10];
        Arrays.fill(parts, "value");
        parts[8] = noteType;
        parts[9] = content;

        //when
        Executable actual = () -> validator.requireContent(parts, 8, 9, "type", "content");

        //then
        if (isValid) {
            assertDoesNotThrow(actual);
        } else {
            assertThrows(IllegalArgumentException.class, actual);
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

    @ParameterizedTest(name = "{0}")
    @CsvFileSource(
            resources = "/validate_test_cases_should_validate.csv",
            numLinesToSkip = 1)
    @DisplayName("Комплексная проверка валидации строки")
    void validate_shouldValidate(String description, String lineFromCsv, boolean expectedValid) {

        // given
        // (дано: lineFromCsv - валидная или невалидная строка из CSV)

        //when
        boolean actual = validator.validate(lineFromCsv);

        //then
        assertEquals(expectedValid, actual);
    }
}

