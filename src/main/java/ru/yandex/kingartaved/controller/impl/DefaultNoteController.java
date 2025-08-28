package ru.yandex.kingartaved.controller.impl;

import ru.yandex.kingartaved.controller.NoteController;
import ru.yandex.kingartaved.dto.NoteDto;
import ru.yandex.kingartaved.dto.request.CreateNewNoteRequestDto;
import ru.yandex.kingartaved.service.NoteService;

import java.util.List;
import java.util.UUID;

public class DefaultNoteController implements NoteController {

    private final NoteService noteService;

//    private final

    public DefaultNoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @Override
    public NoteDto createNote(CreateNewNoteRequestDto createNewNoteRequestDto) {
        //todo: валидация dto и передача валидного entity в сервис после маппинга.
        return noteService.createNote(createNewNoteRequestDto);

    }

    @Override
    public List<NoteDto> readAll() {
        return noteService.readAllNotes();
    }

    @Override
    public boolean updateNote(NoteDto updatedNoteDto) {
        return noteService.updateNote(updatedNoteDto);
    }

    @Override
    public boolean deleteNote(UUID id) {
        return noteService.deleteNote(id);
    }

    @Override
    public void close() {
        noteService.close();
    }


}
