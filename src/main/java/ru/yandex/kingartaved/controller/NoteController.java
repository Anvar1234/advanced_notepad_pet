package ru.yandex.kingartaved.controller;

import ru.yandex.kingartaved.dto.NoteDto;
import ru.yandex.kingartaved.dto.request.CreateNewNoteRequestDto;
import ru.yandex.kingartaved.service.sorting.SortOrder;

import java.util.List;
import java.util.UUID;

public interface NoteController {

    NoteDto createNote(CreateNewNoteRequestDto noteDto);
    List<NoteDto> readAll();
    boolean updateNote(NoteDto updatedNoteDto);
    boolean deleteNote(UUID id);
    void close();
    boolean setSortOrder(SortOrder.SortField sortField, SortOrder.SortDirection sortDirection);
}