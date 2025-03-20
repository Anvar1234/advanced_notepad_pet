package ru.yandex.kingartaved.service;

import ru.yandex.kingartaved.data.model.AbstractNote;

public interface TextNoteService<T extends AbstractNote> {

    void displayAllNotes(); //исходя из порядкового номера отображаемой заметки можно искать уже UUID id заметки, а по ней всю инфу по заметке.

}
