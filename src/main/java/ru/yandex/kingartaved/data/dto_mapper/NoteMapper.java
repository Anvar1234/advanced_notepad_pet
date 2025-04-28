package ru.yandex.kingartaved.data.mapper;

import ru.yandex.kingartaved.data.model.Note;

public interface NoteMapper<T extends Note, R extends NoteDto> {
    R mapEntityToDto(T note);
    T mapDtoToEntity(R noteDto);
}
