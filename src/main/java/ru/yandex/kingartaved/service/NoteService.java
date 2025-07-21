package ru.yandex.kingartaved.service;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.model.Note;
import ru.yandex.kingartaved.dto.NoteDto;

public interface NoteService {

    //общие методы и метод executeCommand(CommandEnum command, int id)
    Note createNote(NoteDto noteDto);
   // void displayAllNotes(); //исходя из порядкового номера отображаемой заметки можно искать уже UUID id заметки, а по ней всю инфу по заметке.

}
