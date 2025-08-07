package ru.yandex.kingartaved.data.model;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;

public sealed abstract class Content permits TextContent, ChecklistContent { //- Sealed класс определяет ограниченную иерархию
//    Content getContent();
    public abstract NoteTypeEnum getSupportedType();
}