package ru.yandex.kingartaved.controller.impl;

import ru.yandex.kingartaved.controller.NoteController;
import ru.yandex.kingartaved.dto.NoteDto;
import ru.yandex.kingartaved.dto.request.CreateNewNoteRequestDto;
import ru.yandex.kingartaved.service.NoteService;

import java.util.List;

public class DefaultNoteController implements NoteController {

    private final NoteService noteService;

//    private final

    public DefaultNoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @Override
    public NoteDto createNote(CreateNewNoteRequestDto createNewNoteRequestDto) {
        //todo: валидация, передача валидного ДТО в сервис.
        return noteService.createNote(createNewNoteRequestDto);

    }

    @Override
    public List<NoteDto> readAll() {
        return noteService.readAllNotes();
    }


}
