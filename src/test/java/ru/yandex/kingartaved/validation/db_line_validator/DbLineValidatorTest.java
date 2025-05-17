package ru.yandex.kingartaved.validation.db_line_validator;

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
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.kingartaved.data.constant.NoteTypeEnum;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DbLineValidatorTest {

    private static final String DB_FIELD_DELIMITER = "\\|";
    @Mock
    private ContentValidatorRegistry mockContentValidatorRegistry;

    @Mock
    private ContentValidator mockContentValidator;

    @InjectMocks
    private DbLineValidator dbLineValidator;

    /**
     * Проверяет, что при обработке контента заметок типа TEXT_NOTE
     * происходит запрос валидатора именно для этого типа.
     *
     * Использует моки для изоляции от реальных реализаций валидаторов.
     */
    @Test
    @DisplayName("Проверка факта запроса валидатора для TEXT_NOTE при обработке контента")
    void requireContent_callsTextContentValidator_success() { //TODO: изменить название, не вызывается конкретная реализация валидатора, а проверяется факт вызова нужного валидатора.
        // given
        when(mockContentValidatorRegistry.getValidator(NoteTypeEnum.TEXT_NOTE))
                .thenReturn(mockContentValidator);
        when(mockContentValidator.isValidContent("Sample text"))
                .thenReturn(true);

        String[] parts = new String[10];
        Arrays.fill(parts, "value");
        parts[8] = "TEXT_NOTE";
        parts[9] = "Sample text";

        // when
        dbLineValidator.requireContent(parts, 8, 9, "noteType", "content");

        // then
        verify(mockContentValidatorRegistry).getValidator(NoteTypeEnum.TEXT_NOTE);
        verify(mockContentValidator).isValidContent("Sample text");
    }

    @ParameterizedTest(name = "{1}")
    @CsvFileSource(
            resources = "/validate_db_line_structure_test_cases_should_fail.csv",
            numLinesToSkip = 1)
    @DisplayName("Проверка валидации некорректных строк из БД с лишними пробелами")
    void validateDbLineStructure_extraSpacesPresent_failed(String lineFromCsv, String description) {
        //given
        String[] parts = lineFromCsv.split(DB_FIELD_DELIMITER);

        //when
        Executable actual = () -> dbLineValidator.validateDbLineStructure(parts);

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
    void validateDbLineStructure_correctLine_success(String lineFromCsv, String noteType) {
        //given
        String[] parts = lineFromCsv.split(DB_FIELD_DELIMITER);

        //when
        Executable actual = () -> dbLineValidator.validateDbLineStructure(parts);

        //then
        assertDoesNotThrow(actual, "Для " + noteType + " строка должна быть валидна");
    }

    @ParameterizedTest
    @MethodSource("provideContentTestCases")
    @DisplayName("Проверка валидации содержимого поля content (успешные и неуспешные сценарии)")
    void requireContent_validAndInvalidContent_cases(String noteType, String content, boolean isValid) {
        //given
        String[] parts = new String[10];
        Arrays.fill(parts, "value");
        parts[8] = noteType;
        parts[9] = content;

        //when
        Executable actual = () -> dbLineValidator.requireContent(parts, 8, 9, "type", "content");

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
    void validate_validAndInvalidContent_cases(String description, String lineFromCsv, boolean expectedValid) {

        // given
        // (дано: lineFromCsv - валидная или невалидная строка из CSV)

        //when
        boolean actual = dbLineValidator.validate(lineFromCsv);

        //then
        assertEquals(expectedValid, actual);
    }
}

