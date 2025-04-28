package ru.yandex.kingartaved.data.mapper.impl;

import ru.yandex.kingartaved.data.mapper.NoteMapper;
import ru.yandex.kingartaved.data.model.Note;
import ru.yandex.kingartaved.dto.TextNoteDto;

public class TextNoteMapperImpl implements NoteMapper<TextNote, TextNoteDto> {
    public TextNoteDto mapEntityToDto(Note textNote) {
        TextNoteDto textNoteFlatDto = new TextNoteDto();
        textNoteFlatDto.setId(textNote.getId());
        textNoteFlatDto.setTitle(textNote.getTitle());
        textNoteFlatDto.setCreatedDateTime(textNote.getCreatedAt());
        textNoteFlatDto.setChangedDateTime(textNote.getUpdatedAt());
        textNoteFlatDto.setRemainderDate(textNote.getRemindAt());
        textNoteFlatDto.setPinned(textNote.isPinned());
        textNoteFlatDto.setPriority(textNote.getPriority());
//        textNoteDto.setTags(textNote.getTags());
        textNoteFlatDto.setStatus(textNote.getStatus());
        textNoteFlatDto.setType(textNote.getType());
        textNoteFlatDto.setContentDto(textNote.getContent());

        return textNoteFlatDto;
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
                .setContent(noteDto.getContentDto())
                .build();

        return textNote;
    }
}
