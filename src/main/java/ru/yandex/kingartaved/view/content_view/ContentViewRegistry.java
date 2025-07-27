package ru.yandex.kingartaved.view.content_view;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.dto.ContentDto;
import ru.yandex.kingartaved.util.LoggerUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContentViewRegistry {

    private static final Logger LOGGER = LoggerUtil.getLogger(ContentViewRegistry.class);
    private static final Map<NoteTypeEnum, ContentView<?>> CONTENT_VIEW_REGISTRY = new HashMap<>();
    private static boolean isInitialized = false;

    private static synchronized void initialize() {
        if (!isInitialized) {
            ServiceLoader.load(ContentView.class)
                    .forEach(contentView -> {
                        LOGGER.log(Level.INFO, "Зарегистрирован экран для заметок типа: " + contentView.getSupportedType());
                        CONTENT_VIEW_REGISTRY.put(contentView.getSupportedType(), contentView);
                    });
            isInitialized = true;
        }
    }

    public <T extends ContentDto> ContentView<T> getContentView(NoteTypeEnum type) {
        initialize();

        ContentView<?> contentView = CONTENT_VIEW_REGISTRY.get(type);
        if (contentView == null) {
            LOGGER.log(
                    Level.WARNING,
                    "Не найден экран для заметок типа: {0}. Доступные типы: {1}",
                    new Object[]{type, CONTENT_VIEW_REGISTRY.keySet()}
            );

            throw new IllegalArgumentException("Нет экрана для заметок типа: " + type);
        }
        return (ContentView<T>) contentView;
    }
}
