package ru.yandex.kingartaved.validation;

import ru.yandex.kingartaved.exception.constant.ErrorMessage;
import ru.yandex.kingartaved.util.LoggerUtil;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.logging.Logger;

public final class ValidationUtil {
    private static final Logger LOGGER = LoggerUtil.getLogger(ValidationUtil.class);

    private ValidationUtil() {
        throw new UnsupportedOperationException(ErrorMessage.UTILITY_CLASS.getMessage());
    }

    /**
     * Проверяет, что указанное поле содержит валидный UUID.
     */
    public static void validateUuid(String part, int index, String label) {
        try {
            UUID.fromString(part);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format(
                    "Поле: '%s' с индексом: '%s' содержит недопустимый UUID: '%s'", label, index, part), e);
        }
    }

    /**
     * Проверяет, что указанное поле содержит валидную дату.
     */
    public static void validateDate(String part, int index, String label) {
        try {
            LocalDateTime.parse(part);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format(
                    "Поле: '%s' с индексом: '%s' содержит недопустимую дату: '%s'", label, index, part), e);
        }
    }

    /**
     * Проверяет, что указанное поле содержит булево значение.
     */
    public static void validateBoolean(String part, int index, String label) {
        if (!"true".equalsIgnoreCase(part) && !"false".equalsIgnoreCase(part)) {
            throw new IllegalArgumentException(String.format(
                    "Поле: '%s' с индексом: '%s' должно быть true/false, но пришло: '%s'", label, index, part));
        }
    }

    /**
     * Проверяет, что указанное поле содержит допустимое значение перечисления.
     */
    public static <E extends Enum<E>> void validateEnum(String enumPart, int index, String label, Class<E> enumClass) {
        try {
            Enum.valueOf(enumClass, enumPart);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format(
                    "Поле: '%s' с индексом: '%s' содержит " +
                            "недопустимое enum-значение: %s для перечисления: %s",
                    label, index, enumPart, enumClass.getSimpleName()), e);
        }
    }

    public static void validateFieldNotNull(String line, int index, String label) {
        if (line == null) {
            throw new IllegalArgumentException(String.format("Поле: '%s' с индексом: '%s' не должно быть null", label, index));
        }
    }

    public static void validateFieldNotEmpty(String line, int index, String label) {
        if (line.isBlank()) {
            throw new IllegalArgumentException(String.format("Поле: '%s' с индексом: '%s' не должно быть пустым", label, index));
        }
    }

    public static void validateFieldAfterTrim(String line, int index, String label) {
        if (!line.equals(line.trim())) {
            throw new IllegalArgumentException(String.format("Поле: '%s' с индексом: '%s' содержит лишние пробелы: '%s'", label, index, line));
        }
    }

    public static void validateFieldNotEqualsStringNull(String line, int index, String label) {
        if ("null".equalsIgnoreCase(line)) {
            throw new IllegalArgumentException(String.format("Поле: '%s' с индексом: '%s' не должно быть эквивалентно строке 'null'", label, index));
        }
    }

    public static boolean isFieldNotEqualsStringNull(String line) {
        return !"null".equalsIgnoreCase(line);
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
