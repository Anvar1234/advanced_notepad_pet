package ru.yandex.kingartaved.data.mapper;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.model.Content;
import ru.yandex.kingartaved.dto.ContentDto;
import ru.yandex.kingartaved.util.LoggerUtil;
import ru.yandex.kingartaved.validation.db_line_validator.DbLineValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Реестр мапперов контента заметок. Позволяет получить маппер для конкретного типа контента.
 * Автоматически загружает все реализации ContentMapper через ServiceLoader.
 */
public class ContentMapperRegistry {
    private static final Logger LOGGER = LoggerUtil.getLogger(ContentMapperRegistry.class);
    private final Map<NoteTypeEnum, ContentMapper<?, ?>> mapperRegistry = new HashMap<>();

    public ContentMapperRegistry() {
        ServiceLoader.load(ContentMapper.class)
                .forEach(contentMapper -> {
                    LOGGER.log(Level.INFO, "Зарегистрирован маппер для заметок типа: " + contentMapper.getSupportedType());
                    mapperRegistry.put(contentMapper.getSupportedType(), contentMapper);
                });
    }

    /**
     * Возвращает маппер контента для указанного типа заметки.
     *
     * @param type тип заметки из перечисления {@link NoteTypeEnum}, для которого требуется маппер
     * @param <C> тип сущности контента (должен наследовать от {@link Content})
     * @param <D> тип DTO контента (должен наследовать от {@link ContentDto})
     * @return соответствующий маппер контента
     * @throws IllegalArgumentException если маппер для указанного типа не найден
     * @throws ClassCastException если generic-параметры {@code <C, D>} не соответствуют фактическим типам маппера
     *
     * @implNote
     * 1. Перед выбросом исключения логируется предупреждение с доступными типами мапперов
     * 2. Каст к {@code ContentMapper<C, D>} выполняется после проверки наличия маппера
     */
    public <C extends Content, D extends ContentDto> ContentMapper<C, D> getMapper(NoteTypeEnum type) {
        ContentMapper<?, ?> mapper = mapperRegistry.get(type);
        if (mapper == null) {
            LOGGER.log(
                    Level.WARNING,
                    "Не удалось найти маппер для типа заметки: {0}. Доступные типы: {1}",
                    new Object[]{type, mapperRegistry.keySet()}
            );
            throw new IllegalArgumentException("Нет маппера для заметок типа: " + type);
        }
        return (ContentMapper<C, D>) mapper;
    }
}
