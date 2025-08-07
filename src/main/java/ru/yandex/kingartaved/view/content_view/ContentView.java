package ru.yandex.kingartaved.view.content_view;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.dto.ContentDto;

import java.util.Scanner;

public interface ContentView<T extends ContentDto> {

    T createContentDto(Scanner scanner);

    void updateContent(Scanner scanner, ContentDto contentDto);
    NoteTypeEnum getSupportedType();

    void renderContent(T contentDto, int tableWidth, String delimiterSymbol);
    String getContentPreview(T contentDto, int remainingWidth);
}
