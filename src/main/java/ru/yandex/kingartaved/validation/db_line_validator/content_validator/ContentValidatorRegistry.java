package ru.yandex.kingartaved.validation.db_line_validator.content_validator;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.util.LoggerUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContentValidatorRegistry {
    private static final Logger LOGGER = LoggerUtil.getLogger(ContentValidatorRegistry.class);
    private static final Map<NoteTypeEnum, ContentValidator> CONTENT_VALIDATOR_REGISTRY = new HashMap<>();
    private static boolean isInitialized = false;

    private static synchronized void initialize() {
        if (!isInitialized) {
            ServiceLoader.load(ContentValidator.class)
                    .forEach(contentValidator -> {
                        LOGGER.log(Level.INFO, "Зарегистрирован валидатор для заметок типа: " + contentValidator.getSupportedType());
                        CONTENT_VALIDATOR_REGISTRY.put(contentValidator.getSupportedType(), contentValidator);
                    });
            isInitialized = true;
        }
    }

    /**
     * Возвращает валидатор контента для указанного типа заметки.
     *
     * @param type тип заметки из перечисления {@link NoteTypeEnum}, для которого требуется валидатор
     * @return соответствующий валидатор контента
     * @throws IllegalArgumentException если валидатор для указанного типа не найден
     *
     * @implNote
     * 1. Перед выбросом исключения логируется предупреждение с доступными типами валидаторов
     */
    public ContentValidator getValidator(NoteTypeEnum type) {
        initialize();

        ContentValidator validator = CONTENT_VALIDATOR_REGISTRY.get(type);
        if (validator == null) {
            LOGGER.log(
                    Level.WARNING,
                    "Не удалось найти валидатор для заметок типа: {0}. Доступные типы: {1}",
                    new Object[]{type, CONTENT_VALIDATOR_REGISTRY.keySet()}
            );
            throw new IllegalArgumentException("Нет валидатора для заметок типа: " + type);
        }
        return validator;
    }
}
