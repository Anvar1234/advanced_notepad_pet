package ru.yandex.kingartaved.data.mapper;

import ru.yandex.kingartaved.data.model.TextNote;
import ru.yandex.kingartaved.dto.TextNoteDto;

public class TextNoteMapperImpl implements NoteMapper<TextNote, TextNoteDto> {
    public TextNoteDto entityToDto(TextNote textNote) {
        TextNoteDto textNoteDto = new TextNoteDto();
        textNoteDto.setId(textNote.getId());
        textNoteDto.setTitle(textNote.getTitle());
        textNoteDto.setCreatedDateTime(textNote.getCreatedDateTime());
        textNoteDto.setChangedDateTime(textNote.getChangedDateTime());
        textNoteDto.setRemainderDate(textNote.getRemainderDate());
        textNoteDto.setPinned(textNote.isPinned());
        textNoteDto.setPriority(textNote.getPriority());
        textNoteDto.setTags(textNote.getTags());
        textNoteDto.setStatus(textNote.getStatus());
        textNoteDto.setType(textNote.getType());
        textNoteDto.setContent(textNote.getContent());

        return textNoteDto;
    }

    public TextNote dtoToEntity(TextNoteDto noteDto) {
        TextNote textNote = new TextNote.TextNoteBuilder()
                .setId(noteDto.getId())
                .setTitle(noteDto.getTitle())
                .setCreatedDateTime(noteDto.getCreatedDateTime())
                .setChangedDateTime(noteDto.getChangedDateTime())
                .setRemainderDate(noteDto.getRemainderDate())
                .setPinned(noteDto.isPinned())
                .setPriority(noteDto.getPriority())
                .setTags(noteDto.getTags())
                .setStatus(noteDto.getStatus())
                .setType(noteDto.getType())
                .setContent(noteDto.getContent())
                .build();

        return textNote;
    }
}
