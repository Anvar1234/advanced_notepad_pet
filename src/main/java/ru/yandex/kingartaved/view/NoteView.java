package ru.yandex.kingartaved.view;

import ru.yandex.kingartaved.controller.NoteController;
import ru.yandex.kingartaved.controller.impl.DefaultNoteController;
import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.model.Note;
import ru.yandex.kingartaved.view.content_view.ContentViewRegistry;

import java.util.Scanner;

public class NoteView {
    public static void main(String[] args) {
        ContentViewRegistry contentViewRegistry = new ContentViewRegistry();
        NoteController controller = new DefaultNoteController(contentViewRegistry);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Твои заметки: ");
        //контроллер - findAll();

        System.out.println("Выбери действие (пустой ввод - выход):");
        System.out.println("1.Создать заметку");
        System.out.println("2.Изменить заметку");

        int choice = scanner.nextInt();

        if (choice == 1) {
            System.out.println("Выбери тип заметки:");
            NoteTypeEnum[] values = NoteTypeEnum.values();

            for (int i = 0; i < values.length; i++) {
                System.out.printf("%s.%s", values[i].ordinal() + 1, values[i].name());
            }

            choice = scanner.nextInt();
            NoteTypeEnum type = values[choice];
            Note createdNote = controller.createNote(scanner, type); //todo: здесь срабатывают разные экраны для метадаты, контента от типа.
            System.out.println(createdNote.toString());//todo: сохраняем созданную заметку, отображаем заметку и меню для этой заметки, скорее всего из контроллера метод update(type),
            //экран контента опять выбирается из реестра.



        }

    }
}
