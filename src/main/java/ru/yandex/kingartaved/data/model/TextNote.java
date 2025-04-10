package ru.yandex.kingartaved.data.model;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;

public class TextNote extends AbstractNote {
    private String content;

    // Приватный конструктор
    private TextNote(TextNoteBuilder builder) {
        super(builder);
        this.content = builder.content;
    }

    // Геттер для content
    public String getContent() {
        return content;
    }

    // Builder для TextNote
    public static class TextNoteBuilder extends AbstractNoteBuilder<TextNoteBuilder> {
        private String content;

        public TextNoteBuilder setContent(String content) {
            this.content = content;
            return this;
        }

        @Override
        protected TextNoteBuilder self() {
            return this;
        }

        @Override
        public TextNote build() {
            super.setType(NoteTypeEnum.TEXT_NOTE);
            return new TextNote(this);
        }
    }

    @Override
    public String toString() {
        return super.toString() + " TextNote{" +
                "content='" + content + '\'' +
                '}';
    }
}