package ru.yandex.kingartaved.util.validator;

import ru.yandex.kingartaved.data.constant.NotePriorityEnum;
import ru.yandex.kingartaved.data.constant.NoteStatusEnum;
import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.util.LoggerUtil;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneralValidator {
    private static final Logger LOGGER = LoggerUtil.log(GeneralValidator.class.getName());
    private static final ContentValidatorRegistry CONTENT_VALIDATOR_REGISTRY = new ContentValidatorRegistry();
    private static final int EXPECTED_FIELDS_COUNT = 10;

    public static boolean validateParts(String[] parts) {
        if (parts == null) {
            LOGGER.log(Level.SEVERE, "Массив parts не может быть null");
            return false;
        }

        if (parts.length != EXPECTED_FIELDS_COUNT) {
            LOGGER.log(Level.SEVERE, "Неверное количество полей, ожидалось: {0}, фактическое количество: {1}",
                    new Object[]{EXPECTED_FIELDS_COUNT, parts.length}); //TODO: изучить подробнее такой вариант с Object[].
            return false;
        }

        if (!validateNoNullValues(parts)) return false;

        try {
            requireUuid(parts, 0, "id");
            requireNotBlank(parts, 1, "title");
            requireDate(parts, 2, "createdAt");
            requireDate(parts, 3, "modifiedAt");
            if (!parts[4].equals("null")) {
                requireDate(parts, 4, "remindAt");
            }
            requireBoolean(parts, 5, "pinned");
            requireEnum(parts, 6, "priority", NotePriorityEnum.class);
            requireEnum(parts, 7, "status", NoteStatusEnum.class);
            requireEnum(parts, 8, "type", NoteTypeEnum.class);
            requireContent(parts, 8, 9, "type", "content");
            return true;
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, "Ошибка при валидации строки: " + String.join("|", parts), e);
            return false;
        }
    }

    private static boolean validateNoNullValues(String[] parts, int... excludedIndexes) {
        for (int i = 0; i < parts.length; i++) {
            if (contains(excludedIndexes, i)) continue; // TODO: Пропускаем исключённые индексы
            if (parts[i] == null) {
                LOGGER.log(Level.SEVERE, "Поле с индексом " + i + " содержит null");
                return false;
            }
        }
        return true;
    }

    /**
     * Проверяет, содержится ли значение в массиве.
     *
     * @param array Массив для проверки (например, [4] или [2, 4]).
     * @param value Искомое значение (например, индекс поля i).
     * @return true, если значение найдено в массиве.
     */
    private static boolean contains(int[] array, int value) {
        for (int item : array) {
            if (item == value) {
                return true;
            }
        }
        return false;
    }

    private static void requireUuid(String[] parts, int index, String label) {
        try {
            UUID.fromString(parts[index]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Поле '" + label + "' (index " + index + ") содержит недопустимый UUID: " + parts[index], e);
        }
    }

    private static void requireDate(String[] parts, int index, String label) {
        try {
            LocalDateTime.parse(parts[index]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Поле '" + label + "' (index " + index + ") содержит недопустимую дату: " + parts[index], e);
        }
    }

    private static void requireNotBlank(String[] parts, int index, String label) {
        if (parts[index] == null || parts[index].trim().isEmpty()) {
            throw new IllegalArgumentException("Поле '" + label + "' (index " + index + ") не может быть пустым");
        }
    }

    private static void requireBoolean(String[] parts, int index, String label) {
        if (!"true".equalsIgnoreCase(parts[index]) && !"false".equalsIgnoreCase(parts[index])) {
            throw new IllegalArgumentException("Поле '" + label + "' (index " + index + ") должно быть true/false, но пришло: " + parts[index]);
        }
    }

    private static <E extends Enum<E>> void requireEnum(String[] parts, int index, String label, Class<E> enumClass) {
        try {
            Enum.valueOf(enumClass, parts[index]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Поле '" + label + "' (index " + index + ") содержит недопустимое enum-значение: " + parts[index], e);
        }
    }

    private static void requireContent(String[] parts, int noteTypeIndex, int contentIndex, String noteTypeLabel, String contentLabel) {
        requireEnum(parts, noteTypeIndex, noteTypeLabel, NoteTypeEnum.class);

        NoteTypeEnum noteTypeEnum = Enum.valueOf(NoteTypeEnum.class, parts[noteTypeIndex]);

        ContentValidator contentValidator = CONTENT_VALIDATOR_REGISTRY
                .getValidator(noteTypeEnum)
                .orElseThrow(() -> new IllegalArgumentException("Нет валидатора для заметок типа: " + noteTypeEnum));

        if (!contentValidator.isValidContent(parts[contentIndex])) {
            throw new IllegalArgumentException("Поле '" + contentLabel + "' (index " + contentIndex + ") содержит невалидную строку: " + parts[contentIndex]);
        }
    }

    public static void main(String[] args) {
        String[] vvod1 = "f47ac10b-58cc-4372-a567-0e02b2c3d479|Заметка 1|2023-10-01T12:34:56|2023-10-01T15:34:56|2023-10-05T12:34:56|true|HIGH|ACTIVE|TEXT_NOTE|Это содержимое текстовой заметки номер 1".split("\\|", -1);
        System.out.println(Arrays.toString(vvod1));
        System.out.println(GeneralValidator.validateParts(vvod1));
        String[] vvod2 = "a1b2c3d4-e5f6-7890-abcd-ef1234567890|Заметка 3|2023-10-10T18:45:22|2023-10-10T20:45:22|2023-10-15T18:45:22|true |HIGH|POSTPONED|TEXT_NOTE|Текстовая заметка с отложенным статусом".split("\\|", -1);
        System.out.println(GeneralValidator.validateParts(vvod2));
    }
}