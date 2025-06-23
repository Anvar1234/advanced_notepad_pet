package ru.yandex.kingartaved.validation.db_line_validator.content_validator.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class ChecklistContentValidatorTest {

    private final ChecklistContentValidator contentValidator = new ChecklistContentValidator();

    @ParameterizedTest(name = "Для заметки типа: {0}")
    @MethodSource("provideTestData")
    @DisplayName("Проверка парсинга строки контента")
    void parseItemStrings_assertEquals(String noteType, String input, List<String> expected) {
        List<String> actual = contentValidator.parseItemStrings(input);
        Assertions.assertEquals(expected, actual, "Для " + noteType + " строка должна быть валидна");
    }

    private static Stream<Arguments> provideTestData() {
        return Stream.of(
                Arguments.of("CHECKLIST", "ex1:true;ex2:false", List.of("ex1:true", "ex2:false")),
                Arguments.of("CHECKLIST", "ex1:true", List.of("ex1:true")),
                Arguments.of("CHECKLIST", "", List.of(""))
        );
    }

}
