package ru.yandex.kingartaved.data.model;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;

import java.util.List;

public class CheckList extends AbstractNote {
    private List<CheckListItem> content;

    // Приватный конструктор
    private CheckList(CheckListBuilder builder) {
        super(builder);
        this.content = builder.content;
    }

    // Геттер для checkListItems
    public List<CheckListItem> getContent() {
        return content;
    }

    // Builder для CheckListNote
    public static class CheckListBuilder extends AbstractNoteBuilder<CheckListBuilder> {
        private List<CheckListItem> content;

        public CheckListBuilder setContent(List<CheckListItem> content) {
            this.content = content;
            return this;
        }

        @Override
        protected CheckListBuilder self() {
            return this;
        }

        @Override
        public CheckList build() {
            super.setType(NoteTypeEnum.CHECK_LIST);
            return new CheckList(this);
        }
    }

    @Override
    public String toString() {
        return super.toString() + " CheckList{" +
                "checkListItems=" + content +
                '}';
    }
}