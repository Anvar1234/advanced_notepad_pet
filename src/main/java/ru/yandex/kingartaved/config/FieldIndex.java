package ru.yandex.kingartaved.config;

public enum FieldIndex {
    ID(0, "id"),
    TITLE(1, "title"),
    CREATED_AT(2, "createdAt"),
    UPDATED_AT(3, "updatedAt"),
    REMIND_AT(4, "remindAt"),
    PINNED(5, "pinned"),
    PRIORITY(6, "priority"),
    STATUS(7, "status"),
    TYPE(8, "type"),
    CONTENT(9, "content");

    private final int index;
    private final String fieldName;

    FieldIndex(int index, String fieldName) {
        this.index = index;
        this.fieldName = fieldName;
    }

    public int getIndex() {
        return index;
    }

    public String getFieldName() {
        return fieldName;
    }
}

//public enum NoteType { TODO: удалить
//    TEXT_NOTE(TextNoteValidator, TextNoteMapper, TextNoteSerializator),
//    CHECKLIST(ChecklistValidator, ChecklistMapper, ChecklistSerializator);
//  ;
//
//    private final Validator validator;
//    private final Mapper mapper;
//    private final Serializator serializator;
//
//    NoteType(Validator validator, Mapper mapper, Serializator serializator) {
//        this.validator = validator;
//        this.mapper = mapper;
//        this.serializator = serializator;
//    }
//
//    public Validator getValidator() {
//        return validator;
//    }
//
//    public Mapper getMapper() {
//        return mapper;
//    }
//
//    public Serializator getSerializator() {
//        return serializator;
//    }
//}