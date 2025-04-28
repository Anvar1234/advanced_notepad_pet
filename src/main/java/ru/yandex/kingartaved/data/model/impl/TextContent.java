package ru.yandex.kingartaved.data.model.impl;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.model.Content;

public final class TextContent implements Content {
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
