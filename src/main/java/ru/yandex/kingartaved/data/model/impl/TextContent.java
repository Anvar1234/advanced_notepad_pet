package ru.yandex.kingartaved.data.model.impl;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.model.Content;

public class TextNoteContent implements Content<String> {
    private final String text;

    public TextNoteContent(String text) {
        this.text = text;
    }

    @Override
    public NoteTypeEnum getSupportedType() {
        return NoteTypeEnum.TEXT_NOTE;
    }

    public String getContent() {
        return text;
    }

}
