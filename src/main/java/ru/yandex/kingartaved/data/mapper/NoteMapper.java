package ru.yandex.kingartaved.data.mapper;

import ru.yandex.kingartaved.data.model.Note;
import ru.yandex.kingartaved.dto.NoteDto;

public interface NoteMapper {

    NoteDto mapEntityToDto(Note note);

    Note mapDtoToEntity(NoteDto noteDto);
}
