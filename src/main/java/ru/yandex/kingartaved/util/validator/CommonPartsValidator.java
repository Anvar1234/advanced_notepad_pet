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

public class CommonPartsValidator {
    private static final Logger logger = LoggerUtil.log(CommonPartsValidator.class.getName());

    public static boolean validateCommonParts(String[] parts) {
        if (parts.length != 11) {
            logger.severe("Неверное количество полей: ожидалось 11, получено: " + parts.length);
            return false;
        }

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
            requireNotBlank(parts, 7, "tags");
            requireEnum(parts, 8, "status", NoteStatusEnum.class);
            requireEnum(parts, 9, "type", NoteTypeEnum.class);
            return true;
        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, "Ошибка при валидации строки: " + Arrays.toString(parts), e);
            return false;
        }
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
            throw new IllegalArgumentException("Поле '" + label + "' (index " + index + ") должно быть true/false, но было: " + parts[index]);
        }
    }

    private static <E extends Enum<E>> void requireEnum(String[] parts, int index, String label, Class<E> enumClass) {
        try {
            Enum.valueOf(enumClass, parts[index]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Поле '" + label + "' (index " + index + ") содержит недопустимое enum-значение: " + parts[index], e);
        }
    }
}