package ru.yandex.kingartaved.view.impl;

import ru.yandex.kingartaved.config.AppConfig;
import ru.yandex.kingartaved.controller.NoteController;
import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.mapper.NoteMapper;
import ru.yandex.kingartaved.data.mapper.content_mapper.ContentMapperRegistry;
import ru.yandex.kingartaved.data.mapper.impl.DefaultNoteMapper;
import ru.yandex.kingartaved.data.mapper.metadata_mapper.MetadataMapper;
import ru.yandex.kingartaved.data.mapper.metadata_mapper.impl.DefaultMetadataMapper;
import ru.yandex.kingartaved.data.model.*;
import ru.yandex.kingartaved.data.model.ChecklistTask;
import ru.yandex.kingartaved.dto.ContentDto;
import ru.yandex.kingartaved.dto.MetadataDto;
import ru.yandex.kingartaved.dto.NoteDto;
import ru.yandex.kingartaved.dto.request.CreateNewMetadataRequestDto;
import ru.yandex.kingartaved.dto.request.CreateNewNoteRequestDto;
import ru.yandex.kingartaved.dto.request.UpdateMetadataRequestDto;
import ru.yandex.kingartaved.dto.response.ContentUpdateResponse;
import ru.yandex.kingartaved.dto.response.ContentUpdateResult;
import ru.yandex.kingartaved.view.NoteViewUtil;
import ru.yandex.kingartaved.view.content_view.ContentView;
import ru.yandex.kingartaved.view.content_view.ContentViewRegistry;
import ru.yandex.kingartaved.view.metadata_view.MetadataView;
import ru.yandex.kingartaved.view.metadata_view.impl.DefaultMetadataView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class DefaultNoteView {
    private static final int TABLE_WIDTH = AppConfig.TABLE_WIDTH;
    private static final String DELIMITER_SYMBOL = AppConfig.DELIMITER_SYMBOL;
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

    public void start() {

        List<NoteDto> currentNoteDtos = controller.readAll();

        while (true) {
            renderAllNotesPreview(currentNoteDtos);
            System.out.println("Выбери действие (пустой ввод - выход):");
            System.out.println("1.Создать заметку");
            System.out.println("2.Редактировать заметку"); //todo: здесь подменю конкретных заметок от типа: закрепить, удалить, отредактировать и тд.
            System.out.println("3.Отсортировать заметки"); //todo: по разным полям: приоритет, тип и тд.
            System.out.println("4.Удалить заметку");
            System.out.println("5.Выйти из приложения");

            Optional<Integer> optionalChoice = NoteViewUtil.getNumericChoice(scanner, "Ошибка: введите число от 1 до 4!");
            if (optionalChoice.isEmpty()) continue;
            int choice = optionalChoice.get();

            switch (choice) {
                case 1 -> { //todo: createNote().
                    System.out.println("Выбор типа создаваемой заметки (пустой ввод - отмена)");
                    NoteTypeEnum[] typeEnums = NoteTypeEnum.values();

                    for (int i = 0; i < typeEnums.length; i++) {
                        System.out.printf("%s.%s", typeEnums[i].ordinal() + 1, typeEnums[i].name());
                    }

                    String errorMessage = String.format("Ошибка: введите число от 1 до %s!", typeEnums.length);
                    optionalChoice = NoteViewUtil.getNumericChoice(scanner, errorMessage);
                    if (optionalChoice.isEmpty()) continue;
                    choice = optionalChoice.get();

                    NoteTypeEnum type = typeEnums[choice];

                    Optional<NoteDto> optionalNoteDto = createNote(type);
                    if (optionalNoteDto.isEmpty()) continue;

                    NoteDto createdNoteDto = optionalNoteDto.get();
                    renderNote(createdNoteDto);
                    currentNoteDtos.add(createdNoteDto);
                }
                case 2 -> {
                    System.out.println("Режим редактирования заметки (пустой ввод - выход)");

                }

                case 3 -> {

                }
                case 4 -> {

                }

                case 5 -> {

                }

                default -> {

                }
            }

//            System.out.println(createdNoteDto.toString());//отображаем созданную заметку
//            controller.readNote(NoteDto noteDto)  //экран контента опять выбирается из реестра.


        }
    }


    public void previewNote() { //TODO: превью в общем списке

    }

    private Optional<NoteDto> updateNote(NoteDto noteDto) {
        MetadataDto currentMetadataDto = noteDto.metadataDto();
        ContentDto currentContentDto = noteDto.contentDto();
        UpdateMetadataRequestDto updateMetadataRequestDto = new UpdateMetadataRequestDto();

        while (true) {
            System.out.println();
            renderNote(noteDto);

            System.out.println("Что изменить:");
            System.out.println("1.Изменить название");
            System.out.println("2.Поставить напоминание");
            System.out.println("3.Закрепить/Открепить заметку");
            System.out.println("4.Изменить содержимое");
            System.out.println("5.Назад");

            Optional<Integer> optionalChoice = NoteViewUtil.getNumericChoice(scanner, "Ошибка: введите число от 1 до 5!");
            if (optionalChoice.isEmpty()) continue;
            int choice = optionalChoice.get();

            switch (choice) {
                case 1 -> {

                    System.out.printf("Текущий заголовок: %s", currentMetadataDto.getTitle());
                    System.out.println("Введите новый заголовок (пустой ввод - отмена): ");
                    String title = scanner.nextLine().trim();

                    if (title.isBlank()) {
                        System.out.println("Ввод отменен.");
                        continue;
                    }
                    updateMetadataRequestDto.setTitle(title);
                }
                case 2 -> {
                    System.out.println("Введите дату напоминания и время (пустой ввод — отмена)"); //в формате ГГГГ-ММ-ДД ЧЧ:ММ
                    while (true) {
                        System.out.println("Введите год в формате YYYY: ");
                        String yyyy = scanner.nextLine().trim();
                        if (yyyy.isBlank()) {
                            System.out.println("Ввод отменен.");
                            continue;
                        }
                        System.out.println("Введите месяц в формате MM: ");
                        String mm = scanner.nextLine().trim();
                        if (mm.isBlank()) {
                            System.out.println("Ввод отменен.");
                            continue;
                        }
                        System.out.println("Введите число в формате DD: ");
                        String dd = scanner.nextLine().trim();
                        if (dd.isBlank()) {
                            System.out.println("Ввод отменен.");
                            continue;
                        }
                        System.out.println("Введите часы в формате HH: ");
                        String hh = scanner.nextLine().trim();
                        if (hh.isBlank()) {
                            System.out.println("Ввод отменен.");
                            continue;
                        }
                        System.out.println("Введите минуты в формате MM: ");
                        String minutes = scanner.nextLine().trim();
                        if (minutes.isBlank()) {
                            System.out.println("Ввод отменен.");
                            continue;
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
                    if (currentMetadataDto.isPinned()) {
                        System.out.println("Заметка сейчас закреплена, открепить? (да/нет)");
                        System.out.print("Ввод (пустой ввод - отмена): ");
                        String input = scanner.nextLine().trim();

                        if (input.isBlank()) {
                            System.out.println("Ввод отменен.");
                            continue;
                        }

                        if ("да".equalsIgnoreCase(input)) {
                            updateMetadataRequestDto.setPinned(false);
                            System.out.println("Заметка откреплена.");
                        } else if ("нет".equalsIgnoreCase(input)) {
                            System.out.println("Заметка остается закрепленной.");
                        } else {
                            System.out.println("Ошибка: введите да или нет.");
                        }
                    } else{
                    updateMetadataRequestDto.setPinned(true);
                    System.out.println("Заметка закреплена.");
                }
            }
            case 4 -> {
                ContentView<ContentDto> contentView = contentViewRegistry.getContentView(noteDto.metadataDto().getType());
                ContentDto contentDto = noteDto.contentDto();
                ContentUpdateResponse updateResponse = contentView.updateContent(scanner, contentDto);
                ContentUpdateResult result = updateResponse.getResult();

                if (result == ContentUpdateResult.NOTE_SHOULD_BE_DELETED) {
                    UUID id = noteDto.metadataDto().getId();
                    if (controller.deleteNote(id)) {
                        System.out.println("Заметка удалена!");
                        return Optional.empty();
                    }
                }
                currentContentDto = updateResponse.getUpdatedContent();
            }
            case 5 -> {
                return Optional.of(new NoteDto(currentMetadataDto, currentContentDto));
            }
        }
    }

}

private void serlectNote(NoteDto noteDto) { //todo: этот метод уже отображает заметку полностью, не превью.
    while (true) {
        System.out.println();
        renderNote(noteDto);

        System.out.println("Что делать с заметкой:");
        System.out.println("1.Редактировать");
        System.out.println("2.Отобразить общий список заметок"); //todo: уже с новой заметкой

        Optional<Integer> optionalChoice = NoteViewUtil.getNumericChoice(scanner, "Ошибка: введите число от 1 до 2!");
        if (optionalChoice.isEmpty()) continue;
        int choice = optionalChoice.get();

        if (choice == 1) {
            updateNote(noteDto);
        }
        if (choice == 2) {
            start();
        }
    }
}

private Optional<NoteDto> createNote(NoteTypeEnum type) {
    CreateNewMetadataRequestDto createNewMetadataRequestDto = metadataView //здесь только title & type
            .createMetadataDto(scanner, type);

    Optional<ContentDto> contentDto = contentViewRegistry.getContentView(type)
            .createContentDto(scanner);
    if (contentDto.isPresent()) {
        CreateNewNoteRequestDto createNewNoteRequestDto = new CreateNewNoteRequestDto(createNewMetadataRequestDto, contentDto.get());

        return Optional.of(controller.createNote(createNewNoteRequestDto));
    }
    return Optional.empty();
}

public void renderAllNotesPreview(List<NoteDto> noteDtos) {
    NoteViewUtil.renderHeaderWithDescription("Все заметки");
    noteDtos.forEach(noteDto -> {
        renderNotePreview(noteDto);
        NoteViewUtil.renderGeneralDelimiter();
    });
//        controller.readAll().forEach(this::renderNotePreview);
}

private void renderNotePreview(NoteDto noteDto) {
    MetadataDto metadataDto = noteDto.metadataDto();
    ContentDto contentDto = noteDto.contentDto();
    NoteTypeEnum noteType = metadataDto.getType();
    ContentView<ContentDto> contentView = contentViewRegistry.getContentView(noteType);

    String metadataPreview = metadataView.getMetadataPreview(metadataDto);
    int remainingWidth = TABLE_WIDTH - metadataPreview.length();
    String contentPreview = contentView.getContentPreview(contentDto, remainingWidth);
    String notePreview = metadataPreview + contentPreview;

    System.out.println(notePreview);
}

public void renderNote(NoteDto noteDto) { //TODO: как отображается при выборе конкретной заметки
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
//        renderGeneralDelimiter();
}

private void renderNoteBody(NoteTypeEnum type, ContentDto contentDto) {
    ContentView<ContentDto> contentView = contentViewRegistry.getContentView(type);
    contentView.renderContent(contentDto);
}

public void renderNoteFooter(MetadataDto metadataDto) {
    metadataView.renderMetadataForFooter(metadataDto);
}

public static void main(String[] args) {
    MetadataMapper metadataMapper = new DefaultMetadataMapper();
    ContentMapperRegistry contentMapperRegistry = new ContentMapperRegistry();
    NoteMapper noteMapper = new DefaultNoteMapper(metadataMapper, contentMapperRegistry);

    Metadata metadata1 = Metadata.builder()
            .title("Моя текст. заметка")
            .type(NoteTypeEnum.TEXT_NOTE)
            .build();

    Content content1 = new TextContent("Текст обычной текстовой заметки достаточно большой длины, чтобы посмотреть разделение и вывод.");

    Note note1 = new Note(metadata1, content1);

    Metadata metadata2 = Metadata.builder()
            .title("Мой чек-лист")
            .type(NoteTypeEnum.CHECKLIST)
            .build();

    List<ChecklistTask> items = new ArrayList<>();
    items.add(new ChecklistTask("Корот                                                         d", false));
    items.add(new ChecklistTask("Короткая подзадачка", true));
    items.add(new ChecklistTask("Это новый текст подзадачки чек-листа пробный для посмотреть такой длинный текст вроде бы должен корректно отобразиться", false));


    Content content2 = new ChecklistContent(items);

    Note note2 = new Note(metadata2, content2);

    List<Note> notes = new ArrayList<>();
    notes.add(note1);
    notes.add(note2);

    List<NoteDto> noteDtos = notes.stream()
            .map(noteMapper::mapEntityToDto)
            .toList();

    MetadataView metadataView = new DefaultMetadataView();
    ContentViewRegistry contentViewRegistry = new ContentViewRegistry();
    NoteController controller;
    Scanner scanner = new Scanner(System.in);

    DefaultNoteView noteView = new DefaultNoteView(metadataView, contentViewRegistry, scanner, controller);
    for (NoteDto noteDto : noteDtos) {
        noteView.renderNote(noteDto);
    }

    noteView.renderAllNotesPreview(noteDtos);

    for (NoteDto dto : noteDtos) {
        noteView.renderNote(dto);
    }
}

}