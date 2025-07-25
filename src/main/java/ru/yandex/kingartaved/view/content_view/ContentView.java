package ru.yandex.kingartaved.view.content_view;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.dto.ContentDto;

import java.util.Scanner;

public interface ContentView<T extends ContentDto> {

    T createContentDto(Scanner scanner);
    NoteTypeEnum getSupportedType();

    void getBodyView(T contentDto);
}
