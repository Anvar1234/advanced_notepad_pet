package ru.yandex.kingartaved.dto.request;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.dto.ContentDto;

public record CreateNewNoteDto(NoteTypeEnum type, String title, ContentDto contentDto) {
    //TODO: добавить валидацию
}