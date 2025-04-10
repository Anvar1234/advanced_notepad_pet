package ru.yandex.kingartaved.dto;

//TODO: имплементировать интерфейс Serializible?
public class TextNoteDto extends AbstractNoteDto {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
