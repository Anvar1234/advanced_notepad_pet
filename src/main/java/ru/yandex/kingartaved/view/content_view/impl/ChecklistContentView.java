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
import java.util.function.Consumer;

public class ChecklistContentView implements ContentView<ChecklistContentDto> {
    @Override
    public ChecklistContentDto createContentDto(Scanner scanner) {
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

    @Override
    public void getBodyView(ChecklistContentDto contentDto) { //todo: добавить обработку очень длинного слова.

        List<ChecklistItemDto> tasks = contentDto.tasks();

        String border = "-----------------------------------";

//        System.out.println("=== Чек-лист ===");
        System.out.println("ID  | Статус | Задача");
        System.out.println("----|--------|" + border);

        for (int i = 0; i < tasks.size(); i++) {
            ChecklistItemDto item = tasks.get(i);
            String id = String.format("%02d", i + 1);
            String status = item.isDone() ? " ✓ " : " ✗ ";
            System.out.printf("%s  |  %s   |%s%n",
                    id,
                    status,
                    printTaskText(border, item.text()));

            System.out.println("----|--------|" + border);
        }
    }

    protected String printTaskText(String border, String text) {

        String[] words = text.split(" ");
        int maxLength = border.length();
        StringBuilder result = new StringBuilder();
        String lineBreakAndTemplate = "\n    |        |";

        StringBuilder currentLine = new StringBuilder();
        for (String word : words) {
            if (currentLine.length() + word.length() + 1 > maxLength) {
                if (!result.isEmpty()) {
                    result.append(lineBreakAndTemplate); // Перенос для продолжения текста
                }
                result.append(currentLine);
                currentLine.setLength(0);
            }

            if (!currentLine.isEmpty()) {
                currentLine.append(" ");
            }
            currentLine.append(word);
        }

        // Добавляем последнюю строку
        if (!currentLine.isEmpty()) {
            if (!result.isEmpty()) {
                result.append(lineBreakAndTemplate);
            }
            result.append(currentLine);
        }

        return result.toString();
    }


    public static void main(String[] args) {
        List<ChecklistItemDto> items = new ArrayList<>();
        items.add(new ChecklistItemDto("Это новый текст заметки чек-листа пробный для посмотреть такой длинный текст вроде бы должен корректно отобразиться", false));
        items.add(new ChecklistItemDto("Короткая заметка", true));
        ChecklistContentView contentView = new ChecklistContentView();
        contentView.getBodyView(new ChecklistContentDto(items));

    }
}
