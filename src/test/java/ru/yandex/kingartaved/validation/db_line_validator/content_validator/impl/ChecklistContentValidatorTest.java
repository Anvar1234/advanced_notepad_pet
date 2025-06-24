package ru.yandex.kingartaved.validation.db_line_validator.content_validator.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class ChecklistContentValidatorTest {

    private static ChecklistContentValidator contentValidator;

    @BeforeAll
    static void beforeAll() {
        contentValidator = new ChecklistContentValidator();
    }

    @ParameterizedTest(name = "Для заметки типа: {0}")
    @MethodSource("provideValidContentPartLine")
    @DisplayName("Проверка парсинга валидной строки контента")
    void parseItemStrings_validLine_success(String noteType, String input, List<String> expected) {
        // given
        // input - валидная строка из метода

        //when
        List<String> actual = contentValidator.parseItemStrings(input);

        //then
        Assertions.assertEquals(expected, actual, "Для " + noteType + " строка должна быть валидна");
    }

    private static Stream<Arguments> provideValidContentPartLine() {
        return Stream.of(
                Arguments.of("CHECKLIST", "ex1:true;ex2:false", List.of("ex1:true", "ex2:false")),
                Arguments.of("CHECKLIST", "ex1:true", List.of("ex1:true")),
                Arguments.of("CHECKLIST", "", List.of(""))
        );
    }

    @ParameterizedTest(name = "Для заметки типа: {0}")
    @MethodSource("provideInvalidContentPartLine")
    @DisplayName("Проверка парсинга невалидной строки контента")
    void parseItemStrings_invalidLine_success(String noteType, String input, List<String> expected) {
        // given
        // input - валидная строка из метода

        //when
        List<String> actual = contentValidator.parseItemStrings(input);

        //then
        Assertions.assertNotEquals(expected, actual, "Для " + noteType + " строка должна быть невалидна");
    }

    private static Stream<Arguments> provideInvalidContentPartLine() {
        return Stream.of(
                Arguments.of("CHECKLIST", "ex1:true;ex2:false;", List.of("ex1:true", "ex2:false")),
                Arguments.of("CHECKLIST", "ex1:true;", List.of("ex1:true")),
                Arguments.of("CHECKLIST", ";", List.of(""))
        );
    }

    @Test
    void validateItemStructureAndContent_validLine_success() {
        //given
        String itemStr = "text:true";

        //when
        Executable action = () -> contentValidator.validateItemStructureAndContent(itemStr);

        //then
        Assertions.assertDoesNotThrow(action,"Строка '"+itemStr+"' должна быть валидна");
    }

    @ParameterizedTest()
    @CsvSource({
            "' '",
            "':'",
            "' :'",
            "'text1:'",
            "'text2: '",
            "':true'",
            "' :true'",
            "'text3:not-boolean'",
            "'text3333333333333333333333333333333333333333333333333333333333333333333333:true'",
            "'text4:true '",
            "'text5: true'",
            "'text6 :true'",
            "' text7:true'"
    })
    @DisplayName("Проверка структуры элемента чек-листа")
    void validateItemStructureAndContent_invalidLine_failed(String itemStr) {
        // given — приходит как параметр

        //when
        Executable action = () -> contentValidator.validateItemStructureAndContent(itemStr);

        //then
        Assertions.assertThrows(IllegalArgumentException.class, action,"Строка '"+itemStr+"' должна быть невалидна");
    }
}
