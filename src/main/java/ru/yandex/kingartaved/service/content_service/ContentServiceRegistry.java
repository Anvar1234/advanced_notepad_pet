package ru.yandex.kingartaved.service.content_service;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.dto.ContentDto;
import ru.yandex.kingartaved.util.LoggerUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContentServiceRegistry {

    private static final Logger LOGGER = LoggerUtil.getLogger(ContentServiceRegistry.class);
    private static final Map<NoteTypeEnum, ContentService<?>> CONTENT_SERVICE_REGISTRY = new HashMap<>();
    private static boolean isInitialized = false;

    private static synchronized void initialize() {
        if (!isInitialized) {
            ServiceLoader.load(ContentService.class)
                    .forEach(contentService -> {
                        NoteTypeEnum type = contentService.getSupportedType();
                        CONTENT_SERVICE_REGISTRY.put(type, contentService);
                        LOGGER.log(
                                Level.INFO,
                                "Зарегистрирован сервис: {0} для контента заметок типа: {1}",
                                new Object[]{contentService, type}
                        );
                    });
            isInitialized = true;
        }
    }

    public <T extends ContentDto> ContentService<T> getContentService(NoteTypeEnum type){
        initialize();

        ContentService<?> contentService = CONTENT_SERVICE_REGISTRY.get(type);

        if(contentService == null){
            LOGGER.log(
                    Level.WARNING,
                    "Не найден сервис для контента заметок типа: {0}. Доступные типы: {1}",
                    new Object[]{type, CONTENT_SERVICE_REGISTRY.keySet()}
            );
            throw new IllegalArgumentException("Нет сервиса для контента заметок типа: " + type);
        }
        return (ContentService<T>) contentService;
    }
}
