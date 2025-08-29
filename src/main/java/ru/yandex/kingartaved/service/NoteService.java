package ru.yandex.kingartaved.service;

import ru.yandex.kingartaved.dto.ContentDto;
import ru.yandex.kingartaved.dto.NoteDto;
import ru.yandex.kingartaved.dto.request.CreateNewNoteRequestDto;
import ru.yandex.kingartaved.service.sorting.SortOrder;

import java.util.List;
import java.util.UUID;

public interface NoteService {

    //общие методы и метод executeCommand(CommandEnum command, int id)
    NoteDto createNote(CreateNewNoteRequestDto createNewNoteRequestDto);
    List<NoteDto> readAllNotes(); //исходя из порядкового номера отображаемой заметки можно искать уже UUID id заметки, а по ней всю инфу по заметке.
    boolean updateNote(NoteDto updatedNoteDto);
    boolean deleteNote(UUID id);
    void close();
    boolean setSortOrder(SortOrder.SortField field, SortOrder.SortDirection direction);
//   void executeCommand(NoteServiceCommandEnum command, NoteDto noteDto);
}
