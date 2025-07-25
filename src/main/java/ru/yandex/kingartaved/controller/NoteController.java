package ru.yandex.kingartaved.controller;

import ru.yandex.kingartaved.dto.NoteDto;

public interface NoteController {

    NoteDto createNote(NoteDto noteDto);
}
