package ru.yandex.kingartaved.validation;

import ru.yandex.kingartaved.exception.constant.ErrorMessage;

public final class FieldValidationUtil {

    private FieldValidationUtil() {
        throw new UnsupportedOperationException(ErrorMessage.UTILITY_CLASS.getMessage());
    }

    /**
     * Проверяет, что указанное поле содержит валидный UUID.
     */
    public static void validateUuidField(String value, int index, String fieldName) {
        try {
            DataValidationUtil.validateUuid(value);
        } catch (IllegalArgumentException e) {
            String errorMessage = String.format(
                    "Поле: '%s' с индексом: '%s' содержит недопустимый UUID: '%s'", fieldName, index, value);
            throw new IllegalArgumentException(errorMessage, e);
        }
    }

    /**
     * Проверяет, что указанное поле содержит валидную дату.
     */
    public static void validateDateField(String value, int index, String label) {
        try {
            DataValidationUtil.validateDate(value);
        } catch (IllegalArgumentException e) {
            String errorMessage = String.format(
                    "Поле: '%s' с индексом: '%s' содержит недопустимую дату: '%s'", label, index, value);
            throw new IllegalArgumentException(errorMessage, e);
        }
    }

    /**
     * Проверяет, что указанное поле содержит булево значение.
     */
    public static void validateBooleanField(String value, int index, String label) {
        try {
            DataValidationUtil.validateBoolean(value);
        } catch (IllegalArgumentException e) {
            String errorMessage = String.format(
                    "Поле: '%s' с индексом: '%s' должно быть true/false, но пришло: '%s'", label, index, value);
            throw new IllegalArgumentException(errorMessage, e);
        }
    }

    /**
     * Проверяет, что указанное поле содержит допустимое значение перечисления.
     */
    public static <E extends Enum<E>> void validateEnumField(String value, int index, String label, Class<E> enumClass) {
        try {
            DataValidationUtil.validateEnum(value, enumClass);
        } catch (IllegalArgumentException e) {
            String errorMessage = String.format(
                    "Поле: '%s' с индексом: '%s' содержит " +
                            "недопустимое enum-значение: '%s' для перечисления: '%s'",
                    label, index, value, enumClass.getSimpleName());
            throw new IllegalArgumentException(errorMessage, e);
        }
    }

//    public static void validateFieldNotNull(String value, int index, String label) {
//        try {
//            DataValidationUtil.validateNotNull(value);
//        } catch (IllegalArgumentException e) {
//            String errorMessage = String.format("Поле: '%s' с индексом: '%s' не должно быть null", label, index);
//            throw new IllegalArgumentException(errorMessage);
//        }
//    }

    public static void validateFieldNotBlank(String value, int index, String label) {
        try {
            DataValidationUtil.validateNotBlank(value);
        } catch (IllegalArgumentException e) {
            String errorMessage = String.format("Поле: '%s' с индексом: '%s' не должно быть пустым", label, index);
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static void validateFieldTrimmed(String value, int index, String label) {
        try {
            DataValidationUtil.validateTrimmed(value);
        } catch (IllegalArgumentException e) {
            String errorMessage = String.format("Поле: '%s' с индексом: '%s' содержит лишние пробелы: '%s'", label, index, value);
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static void validateFieldNotEqualsStringNull(String value, int index, String label) {
        try {
            DataValidationUtil.validateNotEqualsStringNull(value);
        } catch (IllegalArgumentException e) {
            String errorMessage = String.format("Поле: '%s' с индексом: '%s' не должно быть эквивалентно строке 'null'", label, index);
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static boolean isNotStringNull(String line) {
        return !"null".equalsIgnoreCase(line);
    }

    /**
     * Проверяет, что количество полей соответствует ожидаемому.
     */
   public static void validateFieldsCount(String[] parts, int expectedCount) {
        if (parts.length != expectedCount) {
            throw new IllegalArgumentException("Неверное количество полей после split: ожидалось: " + expectedCount + ", пришло: " + parts.length);
        }
    }

}
