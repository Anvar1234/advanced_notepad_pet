package ru.yandex.kingartaved.data.model;

public final class Note {
    private final NoteMetadata metadata;
    private final NoteContent content;

    public Note(NoteMetadata metadata, NoteContent content) {
        this.metadata = metadata;
        this.content = content;
    }

    public NoteMetadata getMetadata() {
        return metadata;
    }

    public NoteContent getContent() {
        return content;
    }
}
