package ru.yandex.kingartaved.dto;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;

import java.util.Objects;

public final class TextContentDto extends ContentDto {
    private final String text;

    public TextContentDto(String text) {
        this.text = text;
    }

    @Override
    public NoteTypeEnum getSupportedType() {
        return NoteTypeEnum.TEXT_NOTE;
    }

    public String text() {
        return text;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (TextContentDto) obj;
        return Objects.equals(this.text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public String toString() {
        return "TextContentDto[" +
                "text=" + text + ']';
    }

}
