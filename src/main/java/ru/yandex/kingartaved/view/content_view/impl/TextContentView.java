package ru.yandex.kingartaved.view.content_view.impl;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.dto.impl.TextContentDto;
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
    public NoteTypeEnum getSupportedType() {
        return NoteTypeEnum.TEXT_NOTE;
    }

    @Override
    public void renderContent(TextContentDto textContentDto) {
        String border = "-----------------------------------";
        String contentText = textContentDto.text();
        String[] words = contentText.split(" ");
        int maxLength = border.length();

        System.out.println(border);

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

        System.out.println(border);
    }
}
