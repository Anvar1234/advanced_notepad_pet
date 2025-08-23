package ru.yandex.kingartaved.view.content_view;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.dto.ContentDto;
import ru.yandex.kingartaved.dto.response.ChecklistContentUpdateResponse;
import ru.yandex.kingartaved.dto.response.ContentUpdateResponse;

import java.util.Optional;
import java.util.Scanner;

public interface ContentView<T extends ContentDto> {

    Optional<T> createContentDto(Scanner scanner);

    ContentUpdateResponse updateContent(Scanner scanner, T contentDto);
    NoteTypeEnum getSupportedType();

    void renderContent(T contentDto);
    String getContentPreview(T contentDto, int remainingWidth);
}
