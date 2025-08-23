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
import ru.yandex.kingartaved.view.NoteViewUtil;
import ru.yandex.kingartaved.view.content_view.ContentView;
import ru.yandex.kingartaved.view.content_view.ContentViewRegistry;
import ru.yandex.kingartaved.view.metadata_view.MetadataView;
import ru.yandex.kingartaved.view.metadata_view.impl.DefaultMetadataView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class DefaultNoteView {
    private static final int TABLE_WIDTH = AppConfig.TABLE_WIDTH;
    private static final String DELIMITER_SYMBOL = AppConfig.DELIMITER_SYMBOL;
    private static final String AROUND_SYMBOL = "=";

    MetadataView metadataView;
    ContentViewRegistry contentViewRegistry;
    NoteController controller;
    Scanner scanner;

    public DefaultNoteView(MetadataView metadataView, ContentViewRegistry contentViewRegistry, Scanner scanner) {
        this.metadataView = metadataView;
        this.contentViewRegistry = contentViewRegistry;
//        this.controller = controller;
        this.scanner = scanner;
    }

    public void start() {

        //контроллер - findAll();
//        renderAllNotesPreview();
        while (true) {

            System.out.println("Выбери действие (пустой ввод - выход):");
            System.out.println("1.Создать заметку");
            System.out.println("2.Редактировать заметку"); //todo: здесь подменю конкретных заметок от типа: закрепить, удалить, отредактировать и тд.
            System.out.println("3.Отсортировать заметки"); //todo: по разным полям: приоритет, тип и тд.
            System.out.println("4.Удалить заметку");

            int choice;

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Ошибка: введите число от 1 до 3!");
                continue;
            }

            if (choice == 1) { //todo: это метод createNote().
                System.out.println("Выбери тип заметки:");
                NoteTypeEnum[] values = NoteTypeEnum.values();

                for (int i = 0; i < values.length; i++) {
                    System.out.printf("%s.%s", values[i].ordinal() + 1, values[i].name());
                }

                while (true) {
                    try {
                        choice = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.err.println("Ошибка: введите число!");
                        break;
                    }
                }

                NoteTypeEnum type = values[choice];

//            NoteDto createdNoteDto = createNote(type);
                //todo: отображаем заметку (подробная инфа) и переходим в метод, где меню для этой заметки: изменить, удалить и тд,
                // скорее всего из контроллера метод update(type).
//            renderNote(createdNoteDto);


                if (choice == 2) {

                }

                if (choice == 3) {

                }


//            System.out.println(createdNoteDto.toString());//отображаем созданную заметку
//            controller.readNote(NoteDto noteDto)  //экран контента опять выбирается из реестра.


            }
        }
    }


    public void previewNote() { //TODO: превью в общем списке

    }

    private void updateNote(NoteDto noteDto) {
        System.out.println("Что изменить:");
        System.out.println("1.Изменить название");
        System.out.println("2.Поставить напоминание");
        System.out.println("3.Закрепить заметку");
        System.out.println("4.Изменить содержимое");
        System.out.println("5.Назад");

        int choice = 0;
//
//        try {
//            choice = Integer.parseInt(scanner.nextLine());
//        } catch (NumberFormatException e) {
//            System.err.println("Ошибка: введите число от 1 до 3!");
//            br  ;
//        }

        if (choice == 4) {
            ContentView<ContentDto> contentView = contentViewRegistry.getContentView(noteDto.metadataDto().getType());
            ContentDto contentDto = noteDto.contentDto();
            contentView.updateContent(scanner, contentDto);
        }

    }

    private void readNote(NoteDto noteDto) {
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

    public void renderNotePreview(NoteDto noteDto) {
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

        DefaultNoteView noteView = new DefaultNoteView(metadataView, contentViewRegistry, scanner);
        for (NoteDto noteDto : noteDtos) {
            noteView.renderNote(noteDto);
        }

        noteView.renderAllNotesPreview(noteDtos);

        for (NoteDto dto : noteDtos) {
            noteView.renderNote(dto);
        }
    }

}