package ru.yandex.kingartaved.controller;

import ru.yandex.kingartaved.dto.NoteDto;
import ru.yandex.kingartaved.dto.request.CreateNewNoteRequestDto;

public interface NoteController {

    NoteDto createNote(CreateNewNoteRequestDto noteDto);
}
