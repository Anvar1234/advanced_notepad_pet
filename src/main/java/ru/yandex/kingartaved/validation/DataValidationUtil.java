package ru.yandex.kingartaved.validation;

import ru.yandex.kingartaved.exception.constant.ErrorMessage;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public final class DataValidationUtil {

    private DataValidationUtil() {
        throw new UnsupportedOperationException(ErrorMessage.UTILITY_CLASS.getMessage());
    }

    /**
     * Проверяет, что указанное поле содержит валидный UUID.
     */
    public static void validateUuid(String value) {
        try {
            UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Недопустимый UUID", e);
        }
    }

    /**
     * Проверяет, что указанное поле содержит валидную дату.
     */
    public static void validateDate(String value) {
        try {
            LocalDateTime.parse(value);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Недопустимый формат даты", e);
        }
    }

    /**
     * Проверяет, что указанное поле содержит булево значение.
     */
    public static void validateBoolean(String value) {
        if (!"true".equalsIgnoreCase(value) && !"false".equalsIgnoreCase(value)) {
            throw new IllegalArgumentException("Недопустимое boolean-значение");
        }
    }

    /**
     * Проверяет, что указанное поле содержит допустимое значение перечисления.
     */
    public static <E extends Enum<E>> void validateEnum(String value, Class<E> enumClass) {
        try {
            Enum.valueOf(enumClass, value);
        } catch (Exception e) {
            throw new IllegalArgumentException("Недопустимое enum-значение", e);
        }
    }

    public static void validateNotNull(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Входная строка == null");
        }
    }

    public static void validateNotBlank(String value) {
        if (value.isBlank()) {
            throw new IllegalArgumentException("Входная строка пуста");
        }
    }

    public static void validateTrimmed(String value) {
        if (!value.equals(value.trim())) {
            throw new IllegalArgumentException("Входная строка содержит лишние пробелы");
        }
    }

    public static void validateNotEqualsStringNull(String value) {
        if ("null".equalsIgnoreCase(value)) {
            throw new IllegalArgumentException("Входная строка эквивалентна строке 'null' ");
        }
    }

    public static void validateText(String text, int minTextLength, int maxTextLength) {
        if (text.length() < minTextLength) {
            throw new IllegalArgumentException("Текст слишком короткий. Минимальная длина: " + minTextLength + " символов");
        }
        if (text.length() > maxTextLength) {
            throw new IllegalArgumentException("Текст слишком длинный. Максимальная длина: " + maxTextLength + " символов");
        }
    }

    // Валидация пути
    public static void validatePath(String path) {
        try {
            validateNotNull(path);
            validateNotBlank(path);
        } catch (IllegalArgumentException e) {
            String errorMessage = String.format("Путь: '%s' не валиден", path);
            throw new IllegalArgumentException(errorMessage, e);
        }
    }

    // Создание Path
    public static Path getPath(String path) {
        try {
            return Path.of(path);
        } catch (InvalidPathException e) {
            throw new IllegalArgumentException("Неверный путь: " + path, e);
        }
    }

    /**
     * Проверяет наличие значения в массиве исключаемых из проверки целых чисел.
     *
     * @param excludedIndexes Массив целых чисел для проверки (например, [4] или [2, 4]).
     * @param inputIndex      Искомое значение (например, индекс поля i).
     * @return true, если значение найдено в массиве.
     */
    public static boolean contains(int inputIndex, int[] excludedIndexes) {
        for (int item : excludedIndexes) {
            if (item == inputIndex) {
                return true;
            }
        }
        return false;
    }
}
