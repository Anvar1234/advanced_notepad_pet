package ru.yandex.kingartaved.dto;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;

public sealed abstract class ContentDto permits TextContentDto, ChecklistContentDto {
   public abstract NoteTypeEnum getSupportedType();
}