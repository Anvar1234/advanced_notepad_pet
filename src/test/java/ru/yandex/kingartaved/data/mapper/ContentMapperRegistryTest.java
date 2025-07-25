package ru.yandex.kingartaved.data.mapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.mapper.content_mapper.ContentMapperRegistry;

import static org.junit.jupiter.api.Assertions.*;


public class ContentMapperRegistryTest {

    private static ContentMapperRegistry registry;

    @BeforeAll
    static void init() {
        registry = new ContentMapperRegistry();
    }

    @ParameterizedTest(name = "Должен быть доступен маппер для типа заметки {0}")
    @EnumSource(value = NoteTypeEnum.class, names = {"TEXT_NOTE", "CHECKLIST"})
    @DisplayName("Проверка наличия маппера для каждого типа заметок из списка")
    void getMapper_shouldReturnMapperForSupportedTypes(NoteTypeEnum type) {
        // given
        // аргумент type

        // when
        var mapper = registry.getMapper(type);

        // then
        assertNotNull(mapper, "Маппер для " + type + " не должен быть null");    }

    @RepeatedTest(5)
    @DisplayName("Проверка получения одного и того же маппера при многократных вызовах")
    void getMapper_shouldReturnSameInstanceOnMultipleCalls() {
        // given
        NoteTypeEnum type = NoteTypeEnum.TEXT_NOTE;

        // when
        var firstCall = registry.getMapper(type);
        var secondCall = registry.getMapper(type);

        // then
        assertSame(firstCall, secondCall, "Ожидался один и тот же экземпляр маппера");
    }

    @Test
    @DisplayName("Проверка выброса исключения при попытке получения маппера для неподдерживаемого типа заметки")
    void getMapper_shouldThrowExceptionForUnsupportedType() {
        // given
        NoteTypeEnum unsupportedType = NoteTypeEnum.TEST_UNSUPPORTED_TYPE;

        // when
        Executable action = () -> registry.getMapper(unsupportedType);

        // then
        assertThrows(IllegalArgumentException.class, action,
                "Для типа " + unsupportedType + " должно быть исключение");
    }
}
