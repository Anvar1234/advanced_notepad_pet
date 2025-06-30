package ru.yandex.kingartaved.data.model;

//public record Note(Metadata metadata, Content content) {
//}

public final class Note {
    private final Metadata metadata;
    private final Content content;

    public Note(Metadata metadata, Content content) {
        this.metadata = metadata;
        this.content = content;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public Content getContent() {
        return content;
    }

//    public void preview(ыва){ //TODO: превью в общем списке
//
//    }
//
//    public void render(ыа){ //TODO: как отображается при выборе
//
//    }
}
