package ru.yandex.kingartaved.validation.db_line_validator.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.config.ContentValidatorRegistry;
import ru.yandex.kingartaved.exception.ContentValidationException;
import ru.yandex.kingartaved.exception.MetadataValidationException;
import ru.yandex.kingartaved.validation.db_line_validator.content_validator.ContentValidator;
import ru.yandex.kingartaved.validation.db_line_validator.metadata_validator.MetadataValidator;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomFormatDbLineValidatorTest {

    private static final String DB_FIELD_DELIMITER = "\\|";

    @Spy
    private ContentValidatorRegistry mockContentValidatorRegistry;

    @Mock
    private ContentValidator mockContentValidator;

    @Mock
    private MetadataValidator mockMetadataValidator;

    @InjectMocks
    private CustomFormatDbLineValidator customFormatDbLineValidator;

//    @BeforeEach
//    public void setUp() throws MetadataValidationException {
//        doNothing().when(mockMetadataValidator).validateMetadata(any());
//    }

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
        doNothing().when(mockContentValidator).validateContent("Sample text");

        String[] parts = new String[10];
        Arrays.fill(parts, "value");
        parts[8] = "TEXT_NOTE";
        parts[9] = "Sample text";

        // when
        customFormatDbLineValidator.validateNoteContent(parts);

        // then
        verify(mockContentValidatorRegistry).getValidator(NoteTypeEnum.TEXT_NOTE);
        verify(mockContentValidator).validateContent("Sample text");
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
        Executable actual = () -> customFormatDbLineValidator.validateNoteContent(parts);

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
    void isValidDbLineDbLineStructure_extraSpacesPresent_failed(String lineFromCsv, String description) {
        //given
        String[] parts = lineFromCsv.split(DB_FIELD_DELIMITER);

        //when
        Executable actual = () -> customFormatDbLineValidator.validateDbLineStructure(parts);

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
    @DisplayName("Проверка структурной целостности корректных строк из БД")
    void validateDbLineStructure_correctLine_success(String lineFromCsv, String noteType) {
        //given
        String[] parts = lineFromCsv.split(DB_FIELD_DELIMITER);

        //when
        Executable actual = () -> customFormatDbLineValidator.validateDbLineStructure(parts);

        //then
        assertDoesNotThrow(actual, "Для " + noteType + " строка должна быть валидна");
    }



    //todo: разделить метод на два, сначала тестим метаданные, потом контент. csv нужно тоже два, видимо.

    @ParameterizedTest(name = "{0}")
    @CsvFileSource(
            resources = "/db_line_validator/validate_validAndInvalidContent_cases.csv",
            numLinesToSkip = 1)
    @DisplayName("Комплексная проверка валидации строки")
    void isValidDbLine_validAndInvalidContent_cases(String description, String lineFromCsv, boolean expectedValid) throws MetadataValidationException, ContentValidationException { //todo переделать (примерно как ниже в комментах) этот метод после того как контент валидатор переделаю, моки не настроены

        // given
        // lineFromCsv - валидная или невалидная строка из CSV

        if (!expectedValid) {
            doThrow(new MetadataValidationException("Невалидная инфа в метадате", null))
                    .when(mockMetadataValidator)
                            .validateMetadata(any());
            doThrow(new ContentValidationException("Невалидный контент", null))
                    .when(mockContentValidator)
                    .validateContent(any());
        }

        //when
        boolean actual = customFormatDbLineValidator.isValidDbLine(lineFromCsv);

        //then
        assertEquals(expectedValid, actual);
    }
}

//@BeforeEach
//void setUp() {
//    // Настраиваем моки:
//    when(mockContentValidatorRegistry.getValidator(any(NoteTypeEnum.class)))
//            .thenReturn(mockContentValidator);
//
//    when(mockContentValidator.isValidContent(anyString()))
//            .thenReturn(true); // По умолчанию считаем контент валидным
//
//    // Важно: мокируем успешную проверку метаданных по умолчанию
//    when(mockMetadataValidator.validateMetadata(any(String[].class)))
//            .thenReturn(true);
//}
//
//@ParameterizedTest(name = "{0}")
//@CsvFileSource(
//        resources = "/db_line_validator/validate_validAndInvalidContent_cases.csv",
//        numLinesToSkip = 1
//)
//@DisplayName("Комплексная проверка валидации строки")
//void validate_validAndInvalidContent_cases(
//        String description,
//        String lineFromCsv,
//        boolean expectedValid
//) {
//    // given: Настраиваем моки для конкретных случаев
//    if (!expectedValid) {
//        // Для невалидных строк заставляем validateMetadata бросать исключение
//        when(mockMetadataValidator.validateMetadata(any(String[].class)))
//                .thenThrow(new IllegalArgumentException("Invalid metadata"));
//    }
//
//    // when
//    boolean actual = customFormatDbLineValidator.validate(lineFromCsv);
//
//    // then
//    assertEquals(expectedValid, actual, description);
//}
//}