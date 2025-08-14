package ru.yandex.kingartaved.view.content_view;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.dto.ContentDto;

import java.util.Optional;
import java.util.Scanner;

public interface ContentView<T extends ContentDto> {

    Optional<T> createContentDto(Scanner scanner);

    T updateContent(Scanner scanner, T contentDto);
    NoteTypeEnum getSupportedType();

    void renderContent(T contentDto, int tableWidth, String delimiterSymbol);
    String getContentPreview(T contentDto, int remainingWidth);
}
