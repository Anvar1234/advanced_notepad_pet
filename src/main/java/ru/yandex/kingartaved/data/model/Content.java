package ru.yandex.kingartaved.data.model;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;

public interface Content {
    NoteTypeEnum getSupportedType();
}