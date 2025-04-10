package ru.yandex.kingartaved.data.mapper;

import ru.yandex.kingartaved.data.model.AbstractNote;
import ru.yandex.kingartaved.dto.AbstractNoteDto;

public interface NoteMapper<T extends AbstractNote, R extends AbstractNoteDto> {
    R mapEntityToDto(T note);
    T mapDtoToEntity(R noteDto);
}
