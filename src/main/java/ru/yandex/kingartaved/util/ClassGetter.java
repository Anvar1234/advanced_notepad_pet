package ru.yandex.kingartaved.util;

import ru.yandex.kingartaved.data.mapper.NoteMapperFactory;
import ru.yandex.kingartaved.exception.constant.ErrorMessage;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ClassGetter {

    private static final Logger logger = LoggerUtil.log(ClassGetter.class.getName());

    private ClassGetter() {
        throw new UnsupportedOperationException(ErrorMessage.UTILITY_CLASS.getMessage());
    }

    /**
     * Находит класс по его имени среди заданного списка классов.
     *
     * @param className Имя класса.
     * @param classes   Список классов для поиска.
     * @return Класс, соответствующий имени.
     * @throws IllegalArgumentException если класс не найден.
     */
    public static Class<?> getClassByNameFromList(String className, List<Class<?>> classes) {
        // Ищем класс в списке
        Class<?> foundClass = classes.stream()
                .filter(c -> c.getSimpleName().equals(className))
                .findFirst()
                .orElseThrow(() -> {
                    // Логируем ошибку, если класс не найден
                    String errorMessage = ErrorMessage.CLASS_NOT_FOUND.getMessage() + ": " + className;
                    logger.warning(errorMessage);
                    return new IllegalArgumentException(errorMessage);
                });
        return foundClass;
    }
}
