package ru.yandex.kingartaved.data.mapper.impl;

import ru.yandex.kingartaved.data.mapper.NoteMapper;
import ru.yandex.kingartaved.data.model.TextNote;
import ru.yandex.kingartaved.dto.TextNoteDto;

public class TextNoteMapperImpl implements NoteMapper<TextNote, TextNoteDto> {
    public TextNoteDto mapEntityToDto(TextNote textNote) {
        TextNoteDto textNoteDto = new TextNoteDto();
        textNoteDto.setId(textNote.getId());
        textNoteDto.setTitle(textNote.getTitle());
        textNoteDto.setCreatedDateTime(textNote.getCreatedAt());
        textNoteDto.setChangedDateTime(textNote.getUpdatedAt());
        textNoteDto.setRemainderDate(textNote.getRemindAt());
        textNoteDto.setPinned(textNote.isPinned());
        textNoteDto.setPriority(textNote.getPriority());
//        textNoteDto.setTags(textNote.getTags());
        textNoteDto.setStatus(textNote.getStatus());
        textNoteDto.setType(textNote.getType());
        textNoteDto.setContent(textNote.getContent());

        return textNoteDto;
    }

    public TextNote mapDtoToEntity(TextNoteDto noteDto) {
        TextNote textNote = new TextNote.TextNoteBuilder()
                .setId(noteDto.getId())
                .setTitle(noteDto.getTitle())
                .setCreatedAt(noteDto.getCreatedDateTime())
                .setChangedAt(noteDto.getChangedDateTime())
                .setRemindAt(noteDto.getRemainderDate())
                .setPinned(noteDto.isPinned())
                .setPriority(noteDto.getPriority())
//                .setTags(noteDto.getTags())
                .setStatus(noteDto.getStatus())
                .setType(noteDto.getType())
                .setContent(noteDto.getContent())
                .build();

        return textNote;
    }
}
