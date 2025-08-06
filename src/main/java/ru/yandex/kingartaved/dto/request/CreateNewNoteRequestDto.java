package ru.yandex.kingartaved.dto.request;

import ru.yandex.kingartaved.dto.ContentDto;

public record CreateNewNoteRequestDto(CreateNewMetadataRequestDto createNewMetadataRequestDto, ContentDto contentDto) {
}