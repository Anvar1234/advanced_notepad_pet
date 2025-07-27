package ru.yandex.kingartaved.view.impl;

import ru.yandex.kingartaved.controller.NoteController;
import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.mapper.NoteMapper;
import ru.yandex.kingartaved.data.mapper.content_mapper.ContentMapperRegistry;
import ru.yandex.kingartaved.data.mapper.impl.DefaultNoteMapper;
import ru.yandex.kingartaved.data.mapper.metadata_mapper.MetadataMapper;
import ru.yandex.kingartaved.data.mapper.metadata_mapper.impl.DefaultMetadataMapper;
import ru.yandex.kingartaved.data.model.ChecklistItem;
import ru.yandex.kingartaved.data.model.Content;
import ru.yandex.kingartaved.data.model.Metadata;
import ru.yandex.kingartaved.data.model.Note;
import ru.yandex.kingartaved.data.model.impl.ChecklistContent;
import ru.yandex.kingartaved.data.model.impl.TextContent;
import ru.yandex.kingartaved.dto.ContentDto;
import ru.yandex.kingartaved.dto.MetadataDto;
import ru.yandex.kingartaved.dto.NoteDto;
import ru.yandex.kingartaved.view.NoteView;
import ru.yandex.kingartaved.view.content_view.ContentView;
import ru.yandex.kingartaved.view.content_view.ContentViewRegistry;
import ru.yandex.kingartaved.view.metadata_view.MetadataView;
import ru.yandex.kingartaved.view.metadata_view.impl.DefaultMetadataView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DefaultNoteView {

    MetadataView metadataView;
    ContentViewRegistry contentViewRegistry;
//    NoteController controller;
    Scanner scanner;

    public DefaultNoteView(MetadataView metadataView, ContentViewRegistry contentViewRegistry,Scanner scanner){//, NoteController controller) {
        this.metadataView = metadataView;
        this.contentViewRegistry = contentViewRegistry;
//        this.controller = controller;
        this.scanner = scanner;
    }

    public void start() {

        System.out.println("Твои заметки: ");
        //контроллер - findAll();

        System.out.println("Выбери действие (пустой ввод - выход):");
        System.out.println("1.Создать заметку");
        System.out.println("2.Выбрать заметку"); //todo: здесь подменю конкретных заметок от типа: закрепить, удалить, отредактировать и тд.
        System.out.println("3.Отсортировать заметки"); //todo: по разным полям: приоритет, тип и тд.


        int choice = scanner.nextInt();


        if (choice == 1) { //todo: это метод createNote().
            System.out.println("Выбери тип заметки:");
            NoteTypeEnum[] values = NoteTypeEnum.values();

            for (int i = 0; i < values.length; i++) {
                System.out.printf("%s.%s", values[i].ordinal() + 1, values[i].name());
            }

            choice = scanner.nextInt();
            NoteTypeEnum type = values[choice];

            MetadataDto metadataDto = metadataView
                    .createMetadataDto(scanner, type);

            ContentDto contentDto = contentViewRegistry.getContentView(type)
                    .createContentDto(scanner);

            NoteDto noteDto = new NoteDto(metadataDto, contentDto);

//            NoteDto createdNoteDto = controller.createNote(noteDto);

            //todo: сохраняем созданную заметку (в кеш репозитория в сервисе),
            // отображаем заметку (подробная инфа) и переходим в метод read(), где меню для этой заметки: изменить, удалить и тд,
            // скорее всего из контроллера метод update(type).

//            System.out.println(createdNoteDto.toString());//отображаем созданную заметку
//            controller.readNote(NoteDto noteDto)  //экран контента опять выбирается из реестра.


        }
    }


    public void previewNote() { //TODO: превью в общем списке

    }

    public void renderNote(NoteDto noteDto) { //TODO: как отображается при выборе конкретной заметки
        MetadataDto metadataDto = noteDto.metadataDto();
        ContentDto contentDto = noteDto.contentDto();
        NoteTypeEnum noteType = metadataDto.getType();
        ContentView<ContentDto> contentView = contentViewRegistry.getContentView(noteType);

        System.out.printf("--------------==%s==---------------\n", metadataDto.getType().getDescription());
        metadataView.renderHeader(metadataDto);
        contentView.renderContent(contentDto);
        metadataView.renderFooter(metadataDto);

        System.out.println();

    }

    public void renderAllNotes() { //TODO: как отображается при выборе конкретной заметки
        System.out.println("------------==Все заметки==-------------");


        System.out.println();


    }

    public static void main(String[] args) {
        MetadataMapper metadataMapper = new DefaultMetadataMapper();
        ContentMapperRegistry contentMapperRegistry = new ContentMapperRegistry();
        NoteMapper noteMapper = new DefaultNoteMapper(metadataMapper, contentMapperRegistry);

        Metadata metadata1 = Metadata.builder()
                .title("Заметка 1")
                .type(NoteTypeEnum.TEXT_NOTE)
                .build();

        Content content1 = new TextContent("Текст обычной текстовой заметки достаточно большой длины, чтобы посмотреть разделение и вывод.");

        Note note1 = new Note(metadata1, content1);

        Metadata metadata2 = Metadata.builder()
                .title("Заметка 2")
                .type(NoteTypeEnum.CHECKLIST)
                .build();

        List<ChecklistItem> items = new ArrayList<>();
        items.add(new ChecklistItem("Это новый текст заметки чек-листа пробный для посмотреть такой длинный текст вроде бы должен корректно отобразиться", false));
        items.add(new ChecklistItem("Короткая заметка", true));

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
        for (NoteDto noteDto : noteDtos){
            noteView.renderNote(noteDto);
        }
    }

}