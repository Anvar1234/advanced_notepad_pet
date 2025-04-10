package ru.yandex.kingartaved.util.validator;

import ru.yandex.kingartaved.data.constant.NotePriorityEnum;
import ru.yandex.kingartaved.data.constant.NoteStatusEnum;
import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.exception.constant.ErrorMessage;
import ru.yandex.kingartaved.util.LoggerUtil;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommonPartsValidator {
    private static final Logger logger = LoggerUtil.log(CommonPartsValidator.class.getName());
    public static boolean validateCommonParts(String[] parts) {
        if (parts.length != 11) return false;

        try {
            UUID.fromString(parts[0]);
            if (parts[1].trim().isEmpty()) {
                throw new IllegalArgumentException("Title is empty");
            }
            LocalDateTime.parse(parts[2]);
            LocalDateTime.parse(parts[3]);
            if (!parts[4].equals("null")) {
                LocalDateTime.parse(parts[4]);
            }
            if (!parts[5].equalsIgnoreCase("true") && !parts[5].equalsIgnoreCase("false")) {
                throw new IllegalArgumentException("Invalid boolean is value: " + parts[5]);
            }
            NotePriorityEnum.valueOf(parts[6]);
            NoteStatusEnum.valueOf(parts[8]);
            NoteTypeEnum.valueOf(parts[9]);
            return true;
        } catch (IllegalArgumentException e){
            String errorMessage = ErrorMessage.INVALID_LINE.getMessage();
            logger.log(Level.SEVERE, errorMessage, e);
            return false;
        }


    }
}
