package ru.yandex.kingartaved.view.content_view.impl;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.model.ChecklistItem;
import ru.yandex.kingartaved.data.model.Content;
import ru.yandex.kingartaved.data.model.impl.ChecklistContent;
import ru.yandex.kingartaved.dto.ChecklistItemDto;
import ru.yandex.kingartaved.dto.ContentDto;
import ru.yandex.kingartaved.dto.impl.ChecklistContentDto;
import ru.yandex.kingartaved.view.content_view.ContentView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChecklistContentView implements ContentView {
    @Override
    public ContentDto createContentDto(Scanner scanner) {
        System.out.println("Введите задачи чек-листа (пустой ввод - выход): ");
        List<ChecklistItemDto> tasks = new ArrayList<>();

        while (scanner.hasNextLine()) {

            String line = scanner.nextLine();

            if (line.isBlank()) {
                return new ChecklistContentDto(tasks);
            } else {
                tasks.add(new ChecklistItemDto(line, false));
            }
        }
        return new ChecklistContentDto(List.copyOf(tasks));
    }

    @Override
    public NoteTypeEnum getSupportedType() {
        return NoteTypeEnum.CHECKLIST;
    }
}
