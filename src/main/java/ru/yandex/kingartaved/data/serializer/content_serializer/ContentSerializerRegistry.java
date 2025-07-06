package ru.yandex.kingartaved.data.serializer.content_serializer;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.util.LoggerUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContentSerializerRegistry {
    private static final Logger LOGGER = LoggerUtil.getLogger(ContentSerializerRegistry.class);
    private static final Map<NoteTypeEnum, ContentSerializer> CONTENT_SERIALIZER_REGISTRY = new HashMap<>();
    private static boolean isInitialized = false;

    private static synchronized void initialize(){
        if(!isInitialized){
            ServiceLoader.load(ContentSerializer.class)
                    .forEach(contentSerializer -> {
                        LOGGER.log(Level.INFO, "Зарегистрирован сериалайзер для заметок типа: " + contentSerializer.getSupportedType());
                        CONTENT_SERIALIZER_REGISTRY.put(contentSerializer.getSupportedType(), contentSerializer);
                    });
        }
        isInitialized = true;
    }

    public ContentSerializer getSerializer(NoteTypeEnum type) {
        initialize();

        ContentSerializer contentSerializer = CONTENT_SERIALIZER_REGISTRY.get(type);
        if (contentSerializer == null) {
            LOGGER.log(
                    Level.WARNING,
                    "Не удалось найти сериалайзер для заметок типа: {0}. Доступные типы: {1}",
                    new Object[]{type, CONTENT_SERIALIZER_REGISTRY.keySet()}
            );
            throw new IllegalArgumentException("Нет сериалайзера для заметок типа: " + type);
        }
        return contentSerializer;
    }
}
