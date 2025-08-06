package ru.yandex.kingartaved.dto.request;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;

public record CreateNewMetadataRequestDto(String title, NoteTypeEnum type) {
}
