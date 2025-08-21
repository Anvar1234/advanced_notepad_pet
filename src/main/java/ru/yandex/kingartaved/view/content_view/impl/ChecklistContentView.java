package ru.yandex.kingartaved.view.content_view.impl;

import ru.yandex.kingartaved.config.AppConfig;
import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.model.ChecklistTaskSelection;
import ru.yandex.kingartaved.dto.ChecklistTaskDto;
import ru.yandex.kingartaved.dto.ChecklistContentDto;
import ru.yandex.kingartaved.dto.response.ChecklistTaskRemovalResponse;
import ru.yandex.kingartaved.dto.response.ChecklistContentUpdateResponse;
import ru.yandex.kingartaved.dto.response.ContentUpdateResult;
import ru.yandex.kingartaved.view.content_view.ContentView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ChecklistContentView implements ContentView<ChecklistContentDto> {

    private static final String ID_COLUMN_NAME = "ID  ";
    private static final String STATUS_COLUMN_NAME = " Статус ";
    private static final String TASK_COLUMN_NAME = " Задача";
    private static final int TABLE_WIDTH = AppConfig.TABLE_WIDTH;
    private static final String DELIMITER_SYMBOL = AppConfig.DELIMITER_SYMBOL;


    @Override
    public Optional<ChecklistContentDto> createContentDto(Scanner scanner) {
        List<ChecklistTaskDto> tasks = new ArrayList<>();

        System.out.println("Режим создания чек-листа. Пустой ввод - выход из режима.");
        while (true) {
            Optional<List<ChecklistTaskDto>> tasksAfterAdd = addTask(scanner, tasks);
            if (tasksAfterAdd.isEmpty()) break;
            tasks = tasksAfterAdd.get();
            System.out.println("Введите следующую задачу или пустую строку для выхода.");
        }

        return Optional.of(new ChecklistContentDto(tasks));
    }

    @Override
    public ChecklistContentUpdateResponse updateContent(Scanner scanner, ChecklistContentDto checklistContentDto) {

        List<ChecklistTaskDto> currentTasks = new ArrayList<>(checklistContentDto.tasks());

        while (true) {
            System.out.println("Меню редактирования задач чек-листа:");
            System.out.println("1.Добавить задачу");
            System.out.println("2.Изменить задачу чек-листа");
            System.out.println("3.Удалить задачу");
            System.out.println("4.Назад в меню чек-листа");
            System.out.print("Ввод: ");

            int choice;

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Ошибка: введите число от 1 до 3!");
                continue;
            }

            if (choice == 1) {
                System.out.println("Режим добавления задач. Пустой ввод - выход из режима.");
                while (true) {
                    Optional<List<ChecklistTaskDto>> tasksAfterAdd = addTask(scanner, currentTasks);
                    if (tasksAfterAdd.isEmpty()) break;
                    currentTasks = tasksAfterAdd.get();
                    System.out.println("Введите следующую задачу или пустую строку для выхода.");
                }
            }
            if (choice == 2) {
                currentTasks = updateTask(scanner, currentTasks);
            }
            if (choice == 3) {
                System.out.println("Режим удаления задач. Пустой ввод - выход из режима.");
                while (true) {
                    ChecklistTaskRemovalResponse response = removeTask(scanner, currentTasks);
                    ChecklistTaskRemovalResponse.TaskRemovalResult result = response.getResult();

                    switch (result) {
                        case TASK_REMOVED -> {
                            System.out.println(result.getDescription());
                            currentTasks = response.getUpdatedTasks();
                        }
                        case ALL_TASKS_REMOVED -> {
                            System.out.println(result.getDescription());
                            return new ChecklistContentUpdateResponse(
                                    ContentUpdateResult.NOTE_SHOULD_BE_DELETED,
                                    new ChecklistContentDto(List.of())
                            );
                        }
                        case OPERATION_CANCELLED, EMPTY_CHECKLIST -> System.out.println(result.getDescription());
                    }
                    System.out.println("Введите № задачи для удаления или пустую строку для выхода.");
                }
            }
            if (choice == 4) {
                return new ChecklistContentUpdateResponse(
                        ContentUpdateResult.CONTENT_UPDATED,
                        checklistContentDto
                );
            }
            System.err.println("Ошибка: введите число от 1 до 4!");
        }
    }

    private Optional<List<ChecklistTaskDto>> addTask(Scanner scanner, List<ChecklistTaskDto> inputTasks) {
        List<ChecklistTaskDto> tasks = new ArrayList<>(inputTasks);

        System.out.println("Текст задачи: ");
        String text = scanner.nextLine().trim();

        if (text.isBlank()) {
            System.out.println("Ввод отменен");
            return Optional.empty();
        }

        ChecklistTaskDto task = new ChecklistTaskDto(text, false);
        tasks.add(task);
        System.out.println("Задача добавлена.");

        return Optional.of(tasks);
    }

    protected List<ChecklistTaskDto> updateTask(Scanner scanner, List<ChecklistTaskDto> inputTasks) {

        List<ChecklistTaskDto> tasks = new ArrayList<>(inputTasks);
        Optional<ChecklistTaskSelection> selectedTask = getTaskWithIndex(scanner, tasks);

        if (selectedTask.isEmpty()) {
            return tasks;
        }

        int selectedItemDtoIndex = selectedTask.get().index();
        ChecklistTaskDto selectedItemDto = selectedTask.get().itemDto();

        System.out.println();

        while (true) {
            renderChecklistItem(selectedItemDtoIndex, selectedItemDto);
            System.out.println("Меню редактирования задачи чек-листа");
            System.out.println("1.Изменить текст задачи");
            System.out.println("2.Отметить задачу выполненной");
            System.out.println("3.Назад");
            System.out.print("Ввод: ");
            int choice;

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Ошибка: введите число от 1 до 3!");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    System.out.println("Введите новый текст задачи (пустой ввод - отмена): ");
                    String updatedText = scanner.nextLine();
                    if (updatedText.isBlank()) {
                        System.out.println("Ввод отменен.");
                        continue;
                    }
                    ChecklistTaskDto updatedItemDto = new ChecklistTaskDto(updatedText, selectedItemDto.isDone());
                    tasks.set(selectedItemDtoIndex, updatedItemDto);
                    selectedItemDto = updatedItemDto;
                    System.out.println("Текст задачи обновлен");
                }
                case 2 -> {
                    Optional<ChecklistTaskDto> dtoOptional = markTaskCompleted(selectedItemDto);
                    if (dtoOptional.isEmpty()) {
                        System.out.println("Задача уже выполнена, вернитесь к выбору другой задачи");
                    } else {
                        selectedItemDto = dtoOptional.get();
                        tasks.set(selectedItemDtoIndex, selectedItemDto);
                    }
                }
                case 3 -> {
                    return tasks;
                }
                default -> System.err.println("Ошибка: введите число от 1 до 3!");
            }
        }

    }

    protected ChecklistTaskRemovalResponse removeTask(Scanner scanner, List<ChecklistTaskDto> inputTasks) {  //todo: throws IllegalArgumentException

        if (inputTasks.isEmpty()) { //входит пустой чек-лист
            return new ChecklistTaskRemovalResponse(
                    ChecklistTaskRemovalResponse.TaskRemovalResult.EMPTY_CHECKLIST,
                    inputTasks
            );
        }

        Optional<ChecklistTaskSelection> selectedTask = getTaskWithIndex(scanner, inputTasks);
        if (selectedTask.isEmpty()) { // пользователь отменил выбор
            return new ChecklistTaskRemovalResponse(
                    ChecklistTaskRemovalResponse.TaskRemovalResult.OPERATION_CANCELLED,
                    inputTasks
            );
        }

        int index = selectedTask.get().index();
        List<ChecklistTaskDto> tasks = new ArrayList<>(inputTasks);

        if (tasks.size() == 1) {// Если задача одна - запрашиваем подтверждение
            System.out.println("""
                    В чек-листе всего одна задача.
                    Её удаление приведёт к удалению всей заметки.
                    Продолжить? (да/нет)""");
            System.out.print("Ввод: ");
            String input = scanner.nextLine().trim();

            if ("да".equalsIgnoreCase(input)) {
                return new ChecklistTaskRemovalResponse(
                        ChecklistTaskRemovalResponse.TaskRemovalResult.ALL_TASKS_REMOVED,
                        List.of()
                );
            } else {
                return new ChecklistTaskRemovalResponse(
                        ChecklistTaskRemovalResponse.TaskRemovalResult.OPERATION_CANCELLED,
                        tasks
                );
            }
        }

        // в остальных случаях - удаляем задачу
        tasks.remove(index);
        return new ChecklistTaskRemovalResponse(
                ChecklistTaskRemovalResponse.TaskRemovalResult.TASK_REMOVED,
                tasks
        );
    }

    protected Optional<ChecklistTaskSelection> getTaskWithIndex(Scanner scanner, List<ChecklistTaskDto> inputTasks) {
        List<ChecklistTaskDto> tasks = new ArrayList<>(inputTasks);

        while (true) {
            System.out.printf("Номер задачи (1-%d): \n", tasks.size());
            String input = scanner.nextLine().trim();

            if (input.isBlank()) {
                System.out.println("Выбор отменен.");
                return Optional.empty();
            }

            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= tasks.size()) {
                    int itemIndex = choice - 1;
                    ChecklistTaskDto selectedTask = tasks.get(itemIndex);
                    return Optional.of(new ChecklistTaskSelection(itemIndex, selectedTask));
                }
                System.err.printf("Неверный номер! Доступно: 1-%d%n", tasks.size());
            } catch (NumberFormatException e) {
                System.err.println("Ошибка: введите число!");
            }
        }
    }

    protected Optional<ChecklistTaskDto> markTaskCompleted(ChecklistTaskDto itemDto) {
        return itemDto.isDone() ? Optional.empty() : Optional.of(new ChecklistTaskDto(itemDto.text(), true));
    }

    @Override
    public NoteTypeEnum getSupportedType() {
        return NoteTypeEnum.CHECKLIST;
    }

    @Override
    public void renderContent(ChecklistContentDto contentDto) { //todo: добавить обработку очень длинного слова.

        int taskColumnHeaderAndBodyDelimiterWidth = TABLE_WIDTH
                - ID_COLUMN_NAME.length()
                - 1
                - STATUS_COLUMN_NAME.length()
                - 1;

        String idColumnHeaderAndBodyDelimiter = DELIMITER_SYMBOL.repeat(ID_COLUMN_NAME.length());

        String statusColumnHeaderAndBodyDelimiter = DELIMITER_SYMBOL.repeat(STATUS_COLUMN_NAME.length());

        String taskTextColumnHeaderAndBodyDelimiter = DELIMITER_SYMBOL.repeat(taskColumnHeaderAndBodyDelimiterWidth);

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
        System.out.println(ID_COLUMN_NAME + "|" + STATUS_COLUMN_NAME + "|" + TASK_COLUMN_NAME);
        System.out.println(idColumnHeaderAndBodyDelimiter + "|" + statusColumnHeaderAndBodyDelimiter + "|" + taskTextColumnHeaderAndBodyDelimiter);

        List<ChecklistTaskDto> tasks = List.copyOf(contentDto.tasks());
        int checklistItemDtoIndex;
        //        for (int i = 0; i < tasks.size(); i++) {
//            ChecklistTaskDto item = tasks.get(i);
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
            ChecklistTaskDto itemDto = tasks.get(i);
            checklistItemDtoIndex = i + 1;
            renderChecklistItem(checklistItemDtoIndex, itemDto);
        }

    }

    @Override
    public String getContentPreview(ChecklistContentDto contentDto, int remainingTableWidth) {
        List<ChecklistTaskDto> tasks = List.copyOf(contentDto.tasks()); //todo: хотя бы одна задача должна быть после валидации, иначе заметка не создается.
        int tasksCount = tasks.size();
        String counterPattern = String.format("(1/%d)", tasksCount);

        String taskText = tasks.get(0).text();
        int contentTextLength = taskText.length();
        int acceptableFullTextLength = remainingTableWidth - counterPattern.length() - 1; //text + "(1/11)" + "|"
        int acceptableAbbreviatedTextLength = remainingTableWidth - 3 - counterPattern.length() - 1; //tex + "..." + "(1/11)" + "|"

        // Если текст + "(1/11)" + "|" короче границы — возвращаем как есть
        if (contentTextLength <= acceptableFullTextLength) {
            return String.format(
                    "%s%s%s%s",
                    taskText,
                    " ".repeat(acceptableFullTextLength - contentTextLength),
                    counterPattern,
                    "|"
            );
        }

        // Если текст + "|" длиннее границы и его нужно обрезать, учитывая tex + "..." + "|"
        String trimmedSubText = taskText
                .substring(0, acceptableAbbreviatedTextLength)
                .trim();

        //Если после trim текст стал короче, то добавляем пробелы до достижения длины acceptableAbbreviatedTextLength
        return String.format(
                "%s%s%s%s%s",
                trimmedSubText,
                "...",
                " ".repeat(acceptableAbbreviatedTextLength - trimmedSubText.length()),
                String.format("(1/%d)", tasksCount),
                "|"
        );
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
     * |        |пробный для посмотреть такой
     * |        |длинный текст вроде бы должен
     * |        |корректно отобразиться
     * ----|--------|-----------------------------------
     * 02  |   ✓    |Короткая заметка
     * ----|--------|-----------------------------------
     */
    protected void renderChecklistItem( //TODO: ЗДЕСЬ!!!
                                        int checklistItemDtoIndex,
                                        ChecklistTaskDto item
    ) {
        int taskColumnHeaderAndBodyDelimiterWidth = TABLE_WIDTH
                - ID_COLUMN_NAME.length()
                - 1
                - STATUS_COLUMN_NAME.length()
                - 1;

        String idColumnHeaderAndBodyDelimiter = DELIMITER_SYMBOL.repeat(ID_COLUMN_NAME.length());

        String statusColumnHeaderAndBodyDelimiter = DELIMITER_SYMBOL.repeat(STATUS_COLUMN_NAME.length());

        String taskTextColumnHeaderAndBodyDelimiter = DELIMITER_SYMBOL.repeat(taskColumnHeaderAndBodyDelimiterWidth);


        StringBuilder buffer = new StringBuilder();

        //создаем пустые значения колонок id, status
        buffer.append(" ".repeat(ID_COLUMN_NAME.length()));
        String idColumnSpaces = buffer.toString();
        buffer.setLength(0);

        buffer.append(" ".repeat(STATUS_COLUMN_NAME.length()));
        String statusColumnSpaces = buffer.toString();
        buffer.setLength(0);

        /**
         * //рисуем первую строку с id, status, первой строкой текста
         * 01  |   ✗    |Это новый текст заметки чек-листа
         */

        List<String> sentences = getSentencesFromTaskText(taskTextColumnHeaderAndBodyDelimiter.length(), item.text());
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
        System.out.println(idColumnHeaderAndBodyDelimiter + "|" + statusColumnHeaderAndBodyDelimiter + "|" + taskTextColumnHeaderAndBodyDelimiter);
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

//    public static void main(String[] args) {
//        List<ChecklistTaskDto> items = new ArrayList<>();
//        items.add(new ChecklistTaskDto("Это новый текст заметки чек-листа пробный для посмотреть такой длинный текст вроде бы должен корректно отобразиться", false));
//        items.add(new ChecklistTaskDto("Короткая заметка", true));
//        ChecklistContentView contentView = new ChecklistContentView();
//        contentView.renderContent(new ChecklistContentDto(items));
//
//    }
}
