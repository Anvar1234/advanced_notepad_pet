package ru.yandex.kingartaved.controller.impl;

import ru.yandex.kingartaved.controller.NoteController;
import ru.yandex.kingartaved.data.mapper.NoteMapper;
import ru.yandex.kingartaved.data.mapper.impl.DefaultNoteMapper;
import ru.yandex.kingartaved.data.model.Note;
import ru.yandex.kingartaved.dto.NoteDto;
import ru.yandex.kingartaved.service.NoteService;

public class DefaultNoteController implements NoteController {

    private final NoteService noteService;


    public DefaultNoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @Override
    public Note createNote(NoteDto noteDto) {
        //todo: валидация, передача валидного ДТО в сервис.
      return noteService.createNote(noteDto);

    }


}
