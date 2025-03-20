package ru.yandex.kingartaved.util.mapper;

import ru.yandex.kingartaved.data.model.AbstractNote;

public interface NoteMapper {
    String toInput(AbstractNote note);
    AbstractNote toEntity(String str);
}
