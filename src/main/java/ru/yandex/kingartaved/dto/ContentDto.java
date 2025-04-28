package ru.yandex.kingartaved.dto;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;

public interface ContentDto {
    NoteTypeEnum getSupportedType();
}