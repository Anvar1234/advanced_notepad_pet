package ru.yandex.kingartaved.controller;

import ru.yandex.kingartaved.dto.NoteDto;
import ru.yandex.kingartaved.dto.request.CreateNewNoteRequestDto;

import java.util.List;

public interface NoteController {

    NoteDto createNote(CreateNewNoteRequestDto noteDto);
    List<NoteDto> readAll();
}
