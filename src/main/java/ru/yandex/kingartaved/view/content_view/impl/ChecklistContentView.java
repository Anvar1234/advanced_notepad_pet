package ru.yandex.kingartaved.view.content_view.impl;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.dto.ChecklistItemDto;
import ru.yandex.kingartaved.dto.impl.ChecklistContentDto;
import ru.yandex.kingartaved.view.content_view.ContentView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChecklistContentView implements ContentView<ChecklistContentDto> {

    //Эти литералы вынести в поля класса (или в конфиги).
    private static final int TABLE_WIDTH = 50;
    private static final String ID_COLUMN_HEADER = "ID  ";
    private static final String STATUS_COLUMN_HEADER = " Статус ";
    private static final String TASK_COLUMN_HEADER = " Задача";

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
    public void renderContent(ChecklistContentDto contentDto) { //todo: добавить обработку очень длинного слова.

        //todo: вынести в параметры метода renderContent.
        int taskColumnHeaderAndBodyDelimiterWidth = TABLE_WIDTH
                - ID_COLUMN_HEADER.length()
                - 1
                - STATUS_COLUMN_HEADER.length()
                - 1;

        StringBuilder buffer = new StringBuilder();

        buffer.append("-".repeat(ID_COLUMN_HEADER.length()));
        String idColumnHeaderAndBodyDelimiter = buffer.toString();
        buffer.setLength(0);

        buffer.append("-".repeat(STATUS_COLUMN_HEADER.length()));
        String statusColumnHeaderAndBodyDelimiter = buffer.toString();
        buffer.setLength(0);

        buffer.append("-".repeat(taskColumnHeaderAndBodyDelimiterWidth));
        String taskTextColumnHeaderAndBodyDelimiter = buffer.toString();
        buffer.setLength(0);

        //нужно нарисовать
        /**
         * ID  | Статус | Задача
         * ----|--------|-----------------------------------
         * 01  |   ✗    |Это новый текст заметки чек-листа
         *     |        |пробный для посмотреть такой
         *     |        |длинный текст вроде бы должен
         *     |        |корректно отобразиться
         * ----|--------|-----------------------------------
         * 02  |   ✓    |Короткая заметка
         * ----|--------|-----------------------------------
         */

        //рисуем шапку
        /**
         * ID  | Статус | Задача
         * ----|--------|-----------------------------------
         */
        System.out.println(ID_COLUMN_HEADER + "|" + STATUS_COLUMN_HEADER + "|" + TASK_COLUMN_HEADER);
        System.out.println(idColumnHeaderAndBodyDelimiter + "|" + statusColumnHeaderAndBodyDelimiter + "|" + taskTextColumnHeaderAndBodyDelimiter);

        List<ChecklistItemDto> tasks = List.copyOf(contentDto.tasks());
        int checklistItemDtoIndex;
        //        for (int i = 0; i < tasks.size(); i++) {
//            ChecklistItemDto item = tasks.get(i);
//            String id = String.format("%02d", i + 1);
//            String status = item.isDone() ? " ✓ " : " ✗ ";
//            System.out.printf("%s  |  %s   |%s%n",
//                    id,
//                    status,
//                    printTaskText(border, item.text()));
//
//            System.out.println("----|--------|" + border);
//        }
        //делегируем отрисовать все остальное
        for (int i = 0; i < tasks.size(); i++) {
            ChecklistItemDto itemDto = tasks.get(i);
            checklistItemDtoIndex = i + 1;
            renderChecklistItem(checklistItemDtoIndex, itemDto, idColumnHeaderAndBodyDelimiter, statusColumnHeaderAndBodyDelimiter, taskTextColumnHeaderAndBodyDelimiter);
        }

    }

//    protected String printTaskText(String border, String text) {
//
//        String[] words = text.split(" ");
//        int maxLength = border.length();
//        StringBuilder result = new StringBuilder();
//        String lineBreakAndTemplate = "\n    |        |";
//
//        StringBuilder currentLine = new StringBuilder();
//        for (String word : words) {
//            if (currentLine.length() + word.length() + 1 > maxLength) {
//                if (!result.isEmpty()) {
//                    result.append(lineBreakAndTemplate); // Перенос для продолжения текста
//                }
//                result.append(currentLine);
//                currentLine.setLength(0);
//            }
//
//            if (!currentLine.isEmpty()) {
//                currentLine.append(" ");
//            }
//            currentLine.append(word);
//        }
//
//        // Добавляем последнюю строку
//        if (!currentLine.isEmpty()) {
//            if (!result.isEmpty()) {
//                result.append(lineBreakAndTemplate);
//            }
//            result.append(currentLine);
//        }
//
//        return result.toString();
//    }

    /**
     * Рисуем:
     * 01  |   ✗    |Это новый текст заметки чек-листа
     *     |        |пробный для посмотреть такой
     *     |        |длинный текст вроде бы должен
     *     |        |корректно отобразиться
     * ----|--------|-----------------------------------
     * 02  |   ✓    |Короткая заметка
     * ----|--------|-----------------------------------
     */
    protected void renderChecklistItem( //TODO: ЗДЕСЬ!!!
                                        int checklistItemDtoIndex,
                                        ChecklistItemDto item,
                                        String idColumnHeaderAndBodyDelimiter,
                                        String statusColumnHeaderAndBodyDelimiter,
                                        String textColumnHeaderAndBodyDelimiter
    ) {

        StringBuilder buffer = new StringBuilder();

        //создаем пустые значения колонок id, status
        buffer.append(" ".repeat(ID_COLUMN_HEADER.length()));
        String idColumnSpaces = buffer.toString();
        buffer.setLength(0);

        buffer.append(" ".repeat(STATUS_COLUMN_HEADER.length()));
        String statusColumnSpaces = buffer.toString();
        buffer.setLength(0);

        /**
         * //рисуем первую строку с id, status, первой строкой текста
         * 01  |   ✗    |Это новый текст заметки чек-листа
         */

        List<String> sentences = getSentencesFromTaskText(textColumnHeaderAndBodyDelimiter.length(), item.text());
        for (int i = 0; i < sentences.size(); i++) {
            if (i == 0) {
                String id = String.format("%02d", checklistItemDtoIndex);
                String status = item.isDone() ? " ✓ " : " ✗ ";
                System.out.printf("%s  |  %s   |%s%n", id, status, sentences.get(i));
            } else {

                /**
                 * //рисуем последующие пустые строки с id, status, первой строкой текста
                 *      |        |пробный для посмотреть такой
                 */
                System.out.printf("%s|%s|%s%n", idColumnSpaces, statusColumnSpaces, sentences.get(i));
            }
        }
        /**
         * //рисуем последнюю строку
         */
        System.out.println(idColumnHeaderAndBodyDelimiter + "|" + statusColumnHeaderAndBodyDelimiter + "|" + textColumnHeaderAndBodyDelimiter);
    }


    protected List<String> getSentencesFromTaskText(int borderLength, String text) {
        String[] words = text.split(" ");
        ArrayList<String> result = new ArrayList<>();

        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            // если формируемое предложение (текущее предложение + следующее слово + пробел)
            // больше допустимой ширины строки (textBorderLength)
            if (currentLine.length() + word.length() + 1 > borderLength) {
                result.add(currentLine.toString());
                currentLine.setLength(0);
            }
            //если в буфере уже есть какое-то слово/слова
            if (!currentLine.isEmpty()) {
                currentLine.append(" ");
            }
            //если буфер пустой (только начинаем заполнение)
            currentLine.append(word);
        }

        // Добавляем последнюю строку
        if (!currentLine.isEmpty()) {
            result.add(currentLine.toString());
        }

        return result;
    }

    public static void main(String[] args) {
        List<ChecklistItemDto> items = new ArrayList<>();
        items.add(new ChecklistItemDto("Это новый текст заметки чек-листа пробный для посмотреть такой длинный текст вроде бы должен корректно отобразиться", false));
        items.add(new ChecklistItemDto("Короткая заметка", true));
        ChecklistContentView contentView = new ChecklistContentView();
        contentView.renderContent(new ChecklistContentDto(items));

    }
}
