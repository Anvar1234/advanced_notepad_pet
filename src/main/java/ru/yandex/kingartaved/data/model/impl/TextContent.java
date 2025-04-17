package ru.yandex.kingartaved.data.model.impl;

import ru.yandex.kingartaved.data.model.NoteContent;

public class TextContent implements NoteContent {
    private final String text;

    public TextContent(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
