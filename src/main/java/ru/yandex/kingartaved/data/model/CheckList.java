package ru.yandex.kingartaved.data.model;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;

import java.util.List;

public class CheckList extends AbstractNote {
    private List<CheckListItem> checkListItems;

    // Приватный конструктор
    private CheckList(CheckListNoteBuilder builder) {
        super(builder);
        this.checkListItems = builder.checkListItems;
    }

    // Геттер для checkListItems
    public List<CheckListItem> getCheckListItems() {
        return checkListItems;
    }

    // Builder для CheckListNote
    public static class CheckListNoteBuilder extends AbstractNote.NoteBuilder<CheckListNoteBuilder> {
        private List<CheckListItem> checkListItems;

        public CheckListNoteBuilder setCheckListItems(List<CheckListItem> checkListItems) {
            this.checkListItems = checkListItems;
            return this;
        }

        @Override
        protected CheckListNoteBuilder self() {
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
                "checkListItems=" + checkListItems +
                '}';
    }
}