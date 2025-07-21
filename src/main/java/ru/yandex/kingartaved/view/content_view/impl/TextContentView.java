package ru.yandex.kingartaved.view.content_view.impl;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.dto.ContentDto;
import ru.yandex.kingartaved.dto.impl.TextContentDto;
import ru.yandex.kingartaved.view.content_view.ContentView;

import java.util.Scanner;

public class TextContentView implements ContentView {

    @Override
    public ContentDto createContentDto(Scanner scanner) {
        System.out.println("Введите текст заметки (пустой ввод - выход): ");
        String text = scanner.nextLine();

        return new TextContentDto(text);
    }

    @Override
    public NoteTypeEnum getSupportedType() {
        return NoteTypeEnum.TEXT_NOTE;
    }
}
