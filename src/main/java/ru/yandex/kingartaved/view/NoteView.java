package ru.yandex.kingartaved.view;

import ru.yandex.kingartaved.controller.NoteController;
import ru.yandex.kingartaved.controller.impl.DefaultNoteController;
import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.model.Note;
import ru.yandex.kingartaved.dto.ContentDto;
import ru.yandex.kingartaved.dto.MetadataDto;
import ru.yandex.kingartaved.dto.NoteDto;
import ru.yandex.kingartaved.view.content_view.ContentView;
import ru.yandex.kingartaved.view.content_view.ContentViewRegistry;
import ru.yandex.kingartaved.view.metadata_view.MetadataView;

import java.util.Scanner;

public class NoteView {

    MetadataView metadataView;
    ContentViewRegistry contentViewRegistry;
    NoteController controller;
    Scanner scanner;

    public NoteView(MetadataView metadataView, ContentViewRegistry contentViewRegistry, NoteController controller, Scanner scanner) {
        this.metadataView = metadataView;
        this.contentViewRegistry = contentViewRegistry;
        this.controller = controller;
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

            NoteDto createdNoteDto = controller.createNote(noteDto);

            //todo: сохраняем созданную заметку (в кеш репозитория в сервисе),
            // отображаем заметку (подробная инфа) и переходим в метод read(), где меню для этой заметки: изменить, удалить и тд,
            // скорее всего из контроллера метод update(type).

            System.out.println(createdNoteDto.toString());//отображаем созданную заметку
//            controller.readNote(NoteDto noteDto)  //экран контента опять выбирается из реестра.


        }
    }


    public void previewNote(){ //TODO: превью в общем списке

    }

    public void renderNote() { //TODO: как отображается при выборе
        System.out.println("---==Заметка==---");


        System.out.println();



    }

}