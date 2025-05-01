package ru.yandex.kingartaved.data.mapper.impl;

import ru.yandex.kingartaved.data.mapper.NoteMapper;
import ru.yandex.kingartaved.data.model.Metadata;
import ru.yandex.kingartaved.data.model.Note;
import ru.yandex.kingartaved.dto.NoteDto;

public class NoteMapperImpl implements NoteMapper {



    @Override
    public NoteDto mapEntityToDto(Note note) {
        Metadata metadata = note.getMetadata();

    }

    @Override
    public Note mapDtoToEntity(NoteDto noteDto) {



    }
}
