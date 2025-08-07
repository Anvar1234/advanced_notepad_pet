package ru.yandex.kingartaved.view.content_view.impl;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.dto.ContentDto;
import ru.yandex.kingartaved.dto.TextContentDto;
import ru.yandex.kingartaved.view.content_view.ContentView;

import java.util.Scanner;

public class TextContentView implements ContentView<TextContentDto> {


    @Override
    public TextContentDto createContentDto(Scanner scanner) {
        System.out.println("Введите текст заметки (пустой ввод - выход): ");
        String text = scanner.nextLine();

        return new TextContentDto(text);
    }

    @Override
    public void updateContent(Scanner scanner, ContentDto contentDto) {
        System.out.println("Меню редактирования текстовой заметки:");
        System.out.println("1.Изменить текст");
        System.out.println("2.Назад к заметке");

        TextContentDto textContentDto = (TextContentDto) contentDto;
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> {
                String text = textContentDto.text();

            }
            default -> throw new IllegalArgumentException();
        }

    }

    @Override
    public NoteTypeEnum getSupportedType() {
        return NoteTypeEnum.TEXT_NOTE;
    }

    @Override
    public void renderContent(TextContentDto textContentDto, int tableWidth, String delimiterSymbol) {

        String textColumnHeaderAndBodyDelimiter = delimiterSymbol.repeat(tableWidth);

        String contentText = textContentDto.text();
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

        System.out.println(textColumnHeaderAndBodyDelimiter);
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
}
