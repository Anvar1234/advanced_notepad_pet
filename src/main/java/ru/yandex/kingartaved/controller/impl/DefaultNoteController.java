package ru.yandex.kingartaved.controller.impl;

import ru.yandex.kingartaved.controller.NoteController;
import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.model.Note;
import ru.yandex.kingartaved.dto.ContentDto;
import ru.yandex.kingartaved.dto.MetadataDto;
import ru.yandex.kingartaved.dto.NoteDto;
import ru.yandex.kingartaved.service.NoteService;
import ru.yandex.kingartaved.view.content_view.ContentView;
import ru.yandex.kingartaved.view.content_view.ContentViewRegistry;
import ru.yandex.kingartaved.view.metadata_view.MetadataView;

import java.util.Scanner;

public class DefaultNoteController implements NoteController {
    private final MetadataView metadataView;
    private final ContentViewRegistry contentViewRegistry;
    private final NoteService noteService;

    public DefaultNoteController(MetadataView metadataView, ContentViewRegistry contentViewRegistry, NoteService noteService) {
        this.metadataView = metadataView;
        this.contentViewRegistry = contentViewRegistry;
        this.noteService = noteService;
    }

    @Override
    public Note createNote(Scanner scanner, NoteTypeEnum type) {
        MetadataDto metadataDto = metadataView.createMetadataDto(scanner, type);//todo: если бы использовали дто, то собиралось бы не в Metadata и Content, а в MetadataDto и ContentDto
        ContentView contentView = contentViewRegistry.getContentView(type);
        ContentDto contentDto = contentView.createContentDto(scanner);
        NoteDto noteDto = new NoteDto(metadataDto, contentDto);
        return noteService.createNote(noteDto);

    }


}
