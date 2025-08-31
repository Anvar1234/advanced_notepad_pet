package ru.yandex.kingartaved.view.impl;

import ru.yandex.kingartaved.config.AppConfig;
import ru.yandex.kingartaved.controller.NoteController;
import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.dto.ContentDto;
import ru.yandex.kingartaved.dto.MetadataDto;
import ru.yandex.kingartaved.dto.NoteDto;
import ru.yandex.kingartaved.dto.request.CreateNewMetadataRequestDto;
import ru.yandex.kingartaved.dto.request.CreateNewNoteRequestDto;
import ru.yandex.kingartaved.dto.request.UpdateMetadataRequestDto;
import ru.yandex.kingartaved.dto.response.ContentUpdateResponse;
import ru.yandex.kingartaved.dto.response.ContentUpdateResult;
import ru.yandex.kingartaved.service.sorting.SortOrder;
import ru.yandex.kingartaved.view.NoteViewUtil;
import ru.yandex.kingartaved.view.content_view.ContentView;
import ru.yandex.kingartaved.view.content_view.ContentViewRegistry;
import ru.yandex.kingartaved.view.metadata_view.MetadataView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class DefaultNoteView {
    private static final int TABLE_WIDTH = AppConfig.TABLE_WIDTH;
    private static final String AROUND_SYMBOL = "=";

    MetadataView metadataView;
    ContentViewRegistry contentViewRegistry;
    NoteController controller;
    Scanner scanner;

    public DefaultNoteView(MetadataView metadataView, ContentViewRegistry contentViewRegistry, Scanner scanner, NoteController controller) {
        this.metadataView = metadataView;
        this.contentViewRegistry = contentViewRegistry;
        this.controller = controller;
        this.scanner = scanner;
    }

    public void startMenu() {

        while (true) {
            List<NoteDto> currentNoteDtos = controller.readAll();

            // Если список заметок пустой, сразу предлагаем создать новую заметку
            if (currentNoteDtos.isEmpty()) {
                System.out.println("Заметок пока нет. Создайте первую заметку!");

                NoteTypeEnum[] typeEnums = NoteTypeEnum.values();
                System.out.println("Выбор типа создаваемой заметки (пустой ввод - отмена):");

                for (int i = 0; i < typeEnums.length; i++) {
                    System.out.printf("%d. %s%n", i + 1, typeEnums[i].name());
                }

                int lastIndex = typeEnums.length;
                String errorMessage = String.format("Ошибка: введите число от 1 до %d!", lastIndex);
                Optional<Integer> optionalChoice = NoteViewUtil.getNumericChoice(scanner, lastIndex, errorMessage);

                if (optionalChoice.isEmpty()) {
                    System.out.println("Создание отменено.");
                    continue;
                }

                int choice = optionalChoice.get();
                NoteTypeEnum type = typeEnums[choice - 1];

                Optional<CreateNewNoteRequestDto> optionalCreateNewNoteRequestDto = createNote(type);
                if (optionalCreateNewNoteRequestDto.isEmpty()) {
                    continue;
                }

                CreateNewNoteRequestDto createNewNoteRequestDto = optionalCreateNewNoteRequestDto.get();
                NoteDto createdNoteDto = controller.createNote(createNewNoteRequestDto);
                renderNote(createdNoteDto);

                // После создания продолжаем цикл, чтобы показать меню с новой заметкой
                continue;
            }

            // Если есть заметки, показываем их и основное меню
            renderAllNotesPreview(currentNoteDtos);
            System.out.println("\nВыбери действие (пустой ввод - выход):");
            System.out.println("1.Создать заметку");
            System.out.println("2.Редактировать заметку");
            System.out.println("3.Отсортировать заметки");
            System.out.println("4.Удалить заметку");
            System.out.println("5.Выйти из приложения");

            Optional<Integer> optionalChoice = NoteViewUtil.getNumericChoice(scanner, 5, "Ошибка: введите число от 1 до 5!");
            if (optionalChoice.isEmpty()) continue;
            int choice = optionalChoice.get();

            switch (choice) {
                case 1 -> { //todo: createNote().
                    System.out.println("Выбор типа создаваемой заметки (пустой ввод - отмена)");
                    NoteTypeEnum[] typeEnums = NoteTypeEnum.values();

                    for (int i = 0; i < typeEnums.length; i++) {
                        System.out.printf("%s.%s\n", typeEnums[i].ordinal() + 1, typeEnums[i].name());
                    }

                    int lastIndex = typeEnums.length;
                    String errorMessage = String.format("Ошибка: введите число от 1 до %s!", lastIndex);
                    optionalChoice = NoteViewUtil.getNumericChoice(scanner, lastIndex, errorMessage);
                    if (optionalChoice.isEmpty()) continue;
                    choice = optionalChoice.get();

                    NoteTypeEnum type = typeEnums[choice - 1];

                    Optional<CreateNewNoteRequestDto> optionalCreateNewNoteRequestDto = createNote(type);
                    if (optionalCreateNewNoteRequestDto.isEmpty()) continue;

                    CreateNewNoteRequestDto createNewNoteRequestDto = optionalCreateNewNoteRequestDto.get();
                    NoteDto createdNoteDto = controller.createNote(createNewNoteRequestDto); //todo: в сервисе сохраняется.
                    renderNote(createdNoteDto);
                }
                case 2 -> {
                    System.out.println("Выберите номер заметки для редактирования");
                    int lastIndex = currentNoteDtos.size();
                    String errorMessage = String.format("Ошибка: введите число от 1 до %s!", lastIndex);
                    optionalChoice = NoteViewUtil.getNumericChoice(scanner, lastIndex, errorMessage);

                    if (optionalChoice.isEmpty()) continue;
                    choice = optionalChoice.get();
//                    renderNote(currentNoteDtos.get(choice - 1));

                    Optional<NoteDto> optionalNoteDto = updateNote(currentNoteDtos.get(choice - 1));
                    if (optionalNoteDto.isEmpty()) continue;

                    NoteDto createdNoteDto = optionalNoteDto.get();
                    if (controller.updateNote(createdNoteDto)) {
                        renderNote(createdNoteDto);
                        System.out.println("Заметка успешно обновлена!");
                        continue;
                    }
                    System.out.println("Заметка не обновлена!");
                }
                case 3 -> {
                    while (true) {
                        Optional<SortOrder> createdSortingOrder = createSortingConfiguration();
                        if (createdSortingOrder.isEmpty()) continue;
                        SortOrder.SortField field = createdSortingOrder.get().field();
                        SortOrder.SortDirection direction = createdSortingOrder.get().direction();

                        if (controller.setSortOrder(field, direction)) {
                            System.out.println("Заметки успешно отсортированы!");
                            break;
                        } else {
                            System.out.println("Заметки не отсортированы!");
                            break;
                        }
                    }
                }
                case 4 -> {
                    System.out.println("Выберите номер заметки для удаления (пустой ввод - отмена)");
                    int lastIndex = currentNoteDtos.size();
                    String errorMessage = String.format("Ошибка: введите число от 1 до %s!", lastIndex);
                    optionalChoice = NoteViewUtil.getNumericChoice(scanner, lastIndex, errorMessage);

                    if (optionalChoice.isEmpty()) continue;
                    choice = optionalChoice.get();

                    if (controller.deleteNote(currentNoteDtos.get(choice - 1).metadataDto().getId())) {
                        System.out.println("Заметка успешно удалена!");
                    } else {
                        System.out.println("Заметка не удалена!");
                    }
                }
                case 5 -> {
                    System.out.println("Выход из приложения.");
                    controller.close();
                    System.exit(0);
                }

                default -> {
                    System.err.println("Чот какато фигня!");
                }
            }
        }
    }

    private Optional<SortOrder> createSortingConfiguration() {
        System.out.println("Выберите сортировку:");
        System.out.println("1. По названию (A→Z)");
        System.out.println("2. По названию (Z→A)");
        System.out.println("3. По дате создания (сначала новые)");
        System.out.println("4. По дате создания (сначала старые)");
        System.out.println("5. По дате изменения (сначала новые)");
        System.out.println("6. По дате изменения (сначала старые)");
        System.out.println("7. По типу заметки");

        Optional<Integer> optionalChoice = NoteViewUtil.getNumericChoice(scanner, 7, "Ошибка: введите число от 1 до 7!");
        if (optionalChoice.isEmpty()) {
            System.out.println("Отмена сортировки.");
            return Optional.empty();
        }

        int choice = optionalChoice.get();

        return switch (choice) {
            case 1 -> Optional.of(new SortOrder(SortOrder.SortField.TITLE, SortOrder.SortDirection.ASC));
            case 2 -> Optional.of(new SortOrder(SortOrder.SortField.TITLE, SortOrder.SortDirection.DESC));
            case 3 -> Optional.of(new SortOrder(SortOrder.SortField.CREATED_AT, SortOrder.SortDirection.DESC));
            case 4 -> Optional.of(new SortOrder(SortOrder.SortField.CREATED_AT, SortOrder.SortDirection.ASC));
            case 5 -> Optional.of(new SortOrder(SortOrder.SortField.UPDATED_AT, SortOrder.SortDirection.DESC));
            case 6 -> Optional.of(new SortOrder(SortOrder.SortField.UPDATED_AT, SortOrder.SortDirection.ASC));
            case 7 -> Optional.of(new SortOrder(SortOrder.SortField.TYPE, SortOrder.SortDirection.ASC));
            default -> {
                System.err.println("Чот какато фигня при конфиге сортировки во вьюшке!");
                yield  Optional.empty();
            }
        };
    }

    private Optional<NoteDto> updateNote(NoteDto noteDto) {
        MetadataDto currentMetadataDto = noteDto.metadataDto();
        ContentDto currentContentDto = noteDto.contentDto();

        // Создаем копии для временного хранения изменений
        String newTitle = currentMetadataDto.getTitle();
        LocalDateTime newRemindAt = currentMetadataDto.getRemindAt();
        Boolean newPinned = currentMetadataDto.isPinned();
        boolean isUpdated = false;
        UpdateMetadataRequestDto updateMetadataRequestDto = new UpdateMetadataRequestDto();

        while (true) {
            // Создаем временный DTO для отображения
            MetadataDto tempMetadata = MetadataDto.builder()
                    .id(currentMetadataDto.getId())
                    .title(newTitle)
                    .createdAt(currentMetadataDto.getCreatedAt())
                    .remindAt(newRemindAt)
                    .updatedAt(currentMetadataDto.getUpdatedAt())
                    .pinned(newPinned)
                    .priority(currentMetadataDto.getPriority())
                    .status(currentMetadataDto.getStatus())
                    .type(currentMetadataDto.getType())
                    .build();

            renderNote(new NoteDto(tempMetadata, currentContentDto));
            System.out.println("Режим редактирования заметки (пустой ввод - выход)");

            System.out.println("Что изменить:");
            System.out.println("1.Изменить название");
            System.out.println("2.Поставить напоминание");
            System.out.println("3.Закрепить/Открепить заметку");
            System.out.println("4.Изменить содержимое");
            System.out.println("5.Назад");

            Optional<Integer> optionalChoice = NoteViewUtil.getNumericChoice(scanner, 5, "Ошибка: введите число от 1 до 5!");
            if (optionalChoice.isEmpty()) continue;
            int choice = optionalChoice.get();

            switch (choice) {
                case 1 -> {
                    while (true) {
                        System.out.printf("Текущий заголовок: %s", currentMetadataDto.getTitle());
                        System.out.println("\nВведите новый заголовок (пустой ввод - отмена): ");
                        String title = scanner.nextLine().trim();

                        if (title.isBlank()) {
                            System.out.println("Ввод отменен.");
                            break;
                        }
                        if (title.length() > AppConfig.MAX_TITLE_LENGTH) {
                            System.err.println("Ошибка: длина заголовка превышает максимально допустимую!");
                            continue;
                        }
                        updateMetadataRequestDto.setTitle(title);
                        System.out.println("Название изменено на: " + title);
                        newTitle = title;
                        break;
                    }
                }
                case 2 -> {
                    while (true) {
                        System.out.println("Введите дату напоминания и время (пустой ввод — отмена)"); //в формате ГГГГ-ММ-ДД ЧЧ:ММ
                        System.out.println("Введите год в формате YYYY: ");
                        String yyyy = scanner.nextLine().trim();
                        if (yyyy.isBlank()) {
                            System.out.println("Ввод отменен.");
                            break;
                        }
                        System.out.println("Введите месяц в формате MM: ");
                        String mm = scanner.nextLine().trim();
                        if (mm.isBlank()) {
                            System.out.println("Ввод отменен.");
                            break;
                        }
                        System.out.println("Введите число в формате DD: ");
                        String dd = scanner.nextLine().trim();
                        if (dd.isBlank()) {
                            System.out.println("Ввод отменен.");
                            break;
                        }
                        System.out.println("Введите часы в формате HH: ");
                        String hh = scanner.nextLine().trim();
                        if (hh.isBlank()) {
                            System.out.println("Ввод отменен.");
                            break;
                        }
                        System.out.println("Введите минуты в формате MM: ");
                        String minutes = scanner.nextLine().trim();
                        if (minutes.isBlank()) {
                            System.out.println("Ввод отменен.");
                            break;
                        }

                        // Собираем строку
                        String remindDateAndTime = String.format("%s-%s-%s %s:%s", yyyy, mm, dd, hh, minutes);

                        // Определяем формат
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                        try {
                            LocalDateTime remindDate = LocalDateTime.parse(remindDateAndTime, formatter);
                            updateMetadataRequestDto.setRemindAt(remindDate);
                            System.out.println("Напоминание установлено на: " + remindDate);
                            break;
                        } catch (DateTimeParseException e) {
                            System.err.println("Ошибка парсинга даты: неверный формат или несуществующая дата (например, 30 февраля).");
                        }
                    }
                }
                case 3 -> {
                    while (true) {
                        if (currentMetadataDto.isPinned()) {
                            System.out.println("Заметка сейчас закреплена, открепить? (да/нет)");
                            System.out.print("Ввод (пустой ввод - отмена): ");
                            String input = scanner.nextLine().trim();

                            if (input.isBlank()) {
                                System.out.println("Ввод отменен.");
                                break;
                            }

                            if ("да".equalsIgnoreCase(input)) {
                                updateMetadataRequestDto.setPinned(false);
                                newPinned = false;
                                System.out.println("Заметка откреплена.");
                                break;
                            } else if ("нет".equalsIgnoreCase(input)) {
                                System.out.println("Заметка остается закрепленной.");
                                break;
                            } else {
                                System.out.println("Ошибка: введите да или нет.");
                            }
                        } else {
                            updateMetadataRequestDto.setPinned(true);
                            newPinned = true;
                            System.out.println("Заметка закреплена.");
                            break;
                        }
                    }
                }
                case 4 -> {
                    ContentView<ContentDto> contentView = contentViewRegistry.getContentView(noteDto.metadataDto().getType());
                    ContentUpdateResponse contentUpdateResponse = contentView.updateContent(scanner, currentContentDto);
                    ContentUpdateResult contentUpdateResult = contentUpdateResponse.getResult();

                    if (contentUpdateResult == ContentUpdateResult.NOTE_SHOULD_BE_DELETED) {
                        UUID id = noteDto.metadataDto().getId();
                        if (controller.deleteNote(id)) {
                            System.out.println("Заметка удалена!");
                            return Optional.empty();
                        }
                    }
                    currentContentDto = contentUpdateResponse.getUpdatedContent();
                    updateMetadataRequestDto.setUpdatedAt(LocalDateTime.now());
                }
                case 5 -> {
                    currentMetadataDto = metadataView.updateMetadataDto(currentMetadataDto, updateMetadataRequestDto);
                    return Optional.of(new NoteDto(currentMetadataDto, currentContentDto));
                }
            }
        }
    }

    private Optional<CreateNewNoteRequestDto> createNote(NoteTypeEnum type) {
        CreateNewMetadataRequestDto createNewMetadataRequestDto = metadataView //здесь только title & type
                .createMetadataDto(scanner, type);

        Optional<ContentDto> contentDto = contentViewRegistry.getContentView(type)
                .createContentDto(scanner);
        if (contentDto.isPresent()) {
            CreateNewNoteRequestDto createNewNoteRequestDto = new CreateNewNoteRequestDto(createNewMetadataRequestDto, contentDto.get());

            return Optional.of(createNewNoteRequestDto);
        }
        return Optional.empty();
    }

    public void renderAllNotesPreview(List<NoteDto> noteDtos) {
        NoteViewUtil.renderHeaderWithDescription("Все заметки");
        for (int i = 0; i < noteDtos.size(); i++) {
            renderNotePreview(i, noteDtos.get(i));
            NoteViewUtil.renderGeneralDelimiter();
        }
    }

    private void renderNotePreview(int index, NoteDto noteDto) {
        MetadataDto metadataDto = noteDto.metadataDto();
        ContentDto contentDto = noteDto.contentDto();
        NoteTypeEnum noteType = metadataDto.getType();
        ContentView<ContentDto> contentView = contentViewRegistry.getContentView(noteType);

        String metadataPreview = metadataView.getMetadataPreview(index, metadataDto);
        int remainingWidth = TABLE_WIDTH - metadataPreview.length();
        String contentPreview = contentView.getContentPreview(contentDto, remainingWidth);
        String notePreview = metadataPreview + contentPreview;

        System.out.println(notePreview);
    }

    public void renderNote(NoteDto noteDto) {
        MetadataDto metadataDto = noteDto.metadataDto();
        ContentDto contentDto = noteDto.contentDto();
        NoteTypeEnum noteType = metadataDto.getType();

        renderNoteHeader(noteType, metadataDto);
        renderNoteBody(noteType, contentDto);
        renderNoteFooter(metadataDto);

        System.out.println();
    }

    private void renderNoteHeader(NoteTypeEnum type, MetadataDto metadataDto) {
        String typeDescription = type.getDescription();

        String listTitleAndAroundSymbols = AROUND_SYMBOL + typeDescription + AROUND_SYMBOL;
        int headerBordersLength = (TABLE_WIDTH - listTitleAndAroundSymbols.length()) / 2;

        String border = "-".repeat(headerBordersLength);

        //---------------=Текстовая заметка=---------------
        //Название: Заметка 1

        System.out.println(border + listTitleAndAroundSymbols + border);
        metadataView.renderMetadataForHeader(metadataDto);
    }

    private void renderNoteBody(NoteTypeEnum type, ContentDto contentDto) {
        ContentView<ContentDto> contentView = contentViewRegistry.getContentView(type);
        contentView.renderContent(contentDto);
    }

    public void renderNoteFooter(MetadataDto metadataDto) {
        metadataView.renderMetadataForFooter(metadataDto);
    }
}