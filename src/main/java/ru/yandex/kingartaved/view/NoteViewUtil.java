package ru.yandex.kingartaved.view;

import ru.yandex.kingartaved.exception.constant.ErrorMessage;

import java.util.Optional;
import java.util.Scanner;

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

        //        System.out.print("\nВвод: ");
//
//        while (true) {
//            try {
//                return Integer.parseInt(scanner.nextLine());
//            } catch (NumberFormatException e) {
//                System.err.println(errorMessage);
//            }
//        }
    }


}
