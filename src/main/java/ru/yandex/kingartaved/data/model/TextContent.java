package ru.yandex.kingartaved.data.model;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;

public final class TextContent extends Content {
    private final String text;

    public TextContent(String text) {
        this.text = text;
    }

    public String text() {
        return text;
    }

    @Override
    public NoteTypeEnum getSupportedType() {
        return NoteTypeEnum.TEXT_NOTE;
    }
}
