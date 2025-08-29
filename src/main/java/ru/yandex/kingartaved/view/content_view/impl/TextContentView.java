package ru.yandex.kingartaved.view.content_view.impl;

import ru.yandex.kingartaved.config.AppConfig;
import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.dto.ChecklistContentDto;
import ru.yandex.kingartaved.dto.TextContentDto;
import ru.yandex.kingartaved.dto.response.ContentUpdateResult;
import ru.yandex.kingartaved.dto.response.TextContentUpdateResponse;
import ru.yandex.kingartaved.view.NoteViewUtil;
import ru.yandex.kingartaved.view.content_view.ContentView;

import java.util.Optional;
import java.util.Scanner;

public class TextContentView implements ContentView<TextContentDto> {
    private static final int TABLE_WIDTH = AppConfig.TABLE_WIDTH;
    private static final String DELIMITER_SYMBOL = AppConfig.DELIMITER_SYMBOL;

    @Override
    public Optional<TextContentDto> createContentDto(Scanner scanner) {
        System.out.println("Введите текст заметки (пустой ввод - отмена): ");
        String text = scanner.nextLine();

        if (text.isBlank()) {
            System.out.println("Создание заметки отменено.");
            return Optional.empty();
        }

        return Optional.of(new TextContentDto(text.trim()));
    }

    @Override
    public TextContentUpdateResponse updateContent(Scanner scanner, TextContentDto textContentDto) {

        String currentText = textContentDto.text();

        while (true) {
            System.out.println();
//            renderContent(new TextContentDto(currentText));

            System.out.println("Меню редактирования текстовой заметки:");
            System.out.println("1.Изменить текст заметки");
            System.out.println("2.Назад в меню заметки");

            Optional<Integer> optionalChoice = NoteViewUtil.getNumericChoice(scanner, 2, "Ошибка: введите число от 1 до 2!");
            if (optionalChoice.isEmpty()) continue;
            int choice = optionalChoice.get();

            switch (choice) {
                case 1 -> {
                    System.out.println("Введите новый текст заметки (пустой ввод - отмена): ");
                    String text = scanner.nextLine().trim();

                    if (text.isBlank()) {
                        System.out.println("Ввод отменен.");
                        continue;
                    }
                    currentText = text;
                }
                case 2 -> {
                    return new TextContentUpdateResponse(
                            ContentUpdateResult.CONTENT_UPDATED,
                            new TextContentDto(currentText));
                }
                default -> System.err.println("Ошибка: введите число от 1 до 2!");
            }
        }
    }

    @Override
    public NoteTypeEnum getSupportedType() {
        return NoteTypeEnum.TEXT_NOTE;
    }

    protected void renderContentHeader() {
//        System.out.println("Текст заметки");
        String textColumnHeaderAndBodyDelimiter = DELIMITER_SYMBOL.repeat(TABLE_WIDTH);
        System.out.println(textColumnHeaderAndBodyDelimiter);

    }

    @Override
    public void renderContent(TextContentDto textContentDto) {

        NoteViewUtil.renderGeneralDelimiter();
        String textColumnHeaderAndBodyDelimiter = DELIMITER_SYMBOL.repeat(TABLE_WIDTH);

        String contentText = "Текст заметки: " + textContentDto.text();
        String[] words = contentText.split(" ");
        int maxLength = textColumnHeaderAndBodyDelimiter.length();

        // Если текст короче границы — выводим как есть
        if (contentText.length() <= maxLength) {
            System.out.println(contentText);
        } else {
            StringBuilder line = new StringBuilder();

            for (String word : words) {
                // Если текущая строка + новое слово + пробел превышают maxLength
                if (line.length() + word.length() + 1 > maxLength) {
                    System.out.println(line); // Выводим собранную строку
                    line.setLength(0); // Очищаем буфер
                }

                if (!line.isEmpty()) {
                    line.append(" "); // Добавляем пробел между словами
                }
                line.append(word);
            }

            // Выводим последнюю строку, если она не пустая
            if (!line.isEmpty()) {
                System.out.println(line);
            }
        }

        NoteViewUtil.renderGeneralDelimiter();
    }

    @Override
    public String getContentPreview(TextContentDto textContentDto, int remainingTableWidth) {

        String contentText = textContentDto.text();
        int contentTextLength = contentText.length();
        int acceptableFullTextLength = remainingTableWidth - 1; //text + "|"
        int acceptableAbbreviatedTextLength = remainingTableWidth - 3 - 1; //tex + "..." + "|"

        // Если текст + "|" короче границы — возвращаем как есть
        if (contentTextLength <= acceptableFullTextLength) {
            return contentText + " ".repeat(acceptableFullTextLength - contentTextLength) + "|";
        }

        // Если текст + "|" длиннее границы и его нужно обрезать, учитывая tex + "..." + "|"
        String trimmedSubText = contentText
                .substring(0, acceptableAbbreviatedTextLength)
                .trim();

        //Если после trim текст стал короче, то добавляем пробелы до достижения длины acceptableAbbreviatedTextLength
        return trimmedSubText + "..." + " ".repeat(acceptableAbbreviatedTextLength - trimmedSubText.length()) + "|";


//        if (contentTextLength < acceptableFullTextLength) {
//            return contentText + " ".repeat(spacesCount) + "|";
//        }
//        //if(contentTextLength > acceptableFullTextLength)
//        //"text"
//        String subText = contentText.substring(0, acceptableAbbreviatedTextLength - 1).trim();
//        if(subText.length() == acceptableAbbreviatedTextLength){
//            return subText + "..." + "|";
//        }
//        //"text       "
//        if (subText.length() < acceptableAbbreviatedTextLength){
//            spacesCount = acceptableAbbreviatedTextLength - subText.length();
//            return subText + " ".repeat(spacesCount) + "|";
//        }


//        else {
//            StringBuilder line = new StringBuilder();
//
//            for (String word : words) {
//                spacesCount = acceptableAbbreviatedTextLength - line.length();
//                // Если текущая строка (уже с пробелом) + новое слово + пробел превышают acceptableAbbreviatedTextLength
//                if (line.length() + word.length() + 1 > acceptableAbbreviatedTextLength) {
//                    return line
//                            .append("...")
//                            .append(" ".repeat(spacesCount))
//                            .append("|")
//                            .toString();
//                    line.setLength(0); // Очищаем буфер
//                }
//                if (!line.isEmpty()) {
//                    line.append(" "); // Добавляем пробел между словами
//                }
//                line.append(word);
//            }
//
//            // Возвращаем последнюю строку, если она не пустая
//            if (!line.isEmpty()) {
//                spacesCount = acceptableAbbreviatedTextLength - line.length();
//                return line
//                        .append("...")
//                        .append(" ".repeat(spacesCount))
//                        .append("|")
//                        .toString();
//            }
//        }
    }

//    public static void main(String[] args) {
//        TextContentDto dto = new TextContentDto("Заметка заметку в заметке заметкий заметуся заметолд заметкус заметуська заметище");
//        TextContentView textContentView = new TextContentView();
//        textContentView.renderContent(dto);
//    }
}
