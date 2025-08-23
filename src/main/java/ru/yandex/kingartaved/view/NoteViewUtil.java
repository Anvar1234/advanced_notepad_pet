package ru.yandex.kingartaved.view;

import ru.yandex.kingartaved.dto.MetadataDto;
import ru.yandex.kingartaved.exception.constant.ErrorMessage;

import java.util.Optional;
import java.util.Scanner;

import static ru.yandex.kingartaved.config.AppConfig.DELIMITER_SYMBOL;
import static ru.yandex.kingartaved.config.AppConfig.TABLE_WIDTH;

public class NoteViewUtil {

    private NoteViewUtil() {
        throw new UnsupportedOperationException(ErrorMessage.UTILITY_CLASS.getMessage());
    }

    public static Optional<Integer> getNumericChoice(Scanner scanner, String errorMessage) {

        while (true) {
            System.out.print("\nВвод (пустой ввод - выход): ");
            String input = scanner.nextLine().trim();

            // Пустая строка - отмена ввода
            if (input.isEmpty()) {
                System.out.println("\nВвод отменен");
                return Optional.empty();
            }

            try {
                return Optional.of(Integer.parseInt(input));
            } catch (NumberFormatException e) {
                System.err.println(errorMessage + "\n");
            }
        }
    }

    public static void renderGeneralDelimiter() {
        String noteHeaderAndBodyDelimiter = DELIMITER_SYMBOL.repeat(TABLE_WIDTH);
        System.out.println(noteHeaderAndBodyDelimiter);
    }

    public static void renderHeaderWithDescription(String description) {

        String listTitleAndAroundSymbols = "=" + description + "=";
        int headerHalfBordersLength = (TABLE_WIDTH - listTitleAndAroundSymbols.length()) / 2;

        String border = "-".repeat(headerHalfBordersLength);

        //---------------=Описание=---------------
        System.out.println(border + listTitleAndAroundSymbols + border);
    }

}
