package ru.yandex.kingartaved.service;

import ru.yandex.kingartaved.dto.NoteDto;
import ru.yandex.kingartaved.dto.request.CreateNewNoteRequestDto;

public interface NoteService {

    //общие методы и метод executeCommand(CommandEnum command, int id)
    NoteDto createNote(CreateNewNoteRequestDto createNewNoteRequestDto);
   // void displayAllNotes(); //исходя из порядкового номера отображаемой заметки можно искать уже UUID id заметки, а по ней всю инфу по заметке.
//   void executeCommand(ServiceCommandEnum command, NoteDto noteDto);
}
