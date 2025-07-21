package ru.yandex.kingartaved.controller;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.model.Note;
import ru.yandex.kingartaved.view.content_view.ContentViewRegistry;

import java.util.Scanner;

public interface NoteController {

    Note createNote(Scanner scanner, NoteTypeEnum type);
}
