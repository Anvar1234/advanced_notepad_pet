package ru.yandex.kingartaved.service;

import ru.yandex.kingartaved.dto.NoteDto;
import ru.yandex.kingartaved.dto.request.CreateNewNoteRequestDto;

import java.util.List;

public interface NoteService {

    //общие методы и метод executeCommand(CommandEnum command, int id)
    NoteDto createNote(CreateNewNoteRequestDto createNewNoteRequestDto);
    List<NoteDto> readAllNotes(); //исходя из порядкового номера отображаемой заметки можно искать уже UUID id заметки, а по ней всю инфу по заметке.
//   void executeCommand(NoteServiceCommandEnum command, NoteDto noteDto);
}
