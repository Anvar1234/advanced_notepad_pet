package ru.yandex.kingartaved.data.mapper;

import ru.yandex.kingartaved.data.model.Metadata;
import ru.yandex.kingartaved.dto.MetadataDto;
import ru.yandex.kingartaved.exception.constant.ErrorMessage;

public final class MetadataMapper {

    private MetadataMapper() {
        throw new UnsupportedOperationException(ErrorMessage.UTILITY_CLASS.getMessage());
    }

    public static MetadataDto mapEntityToDto(Metadata metadata) {
        return MetadataDto.builder()
                .id(metadata.getId())
                .title(metadata.getTitle())
                .createdAt(metadata.getCreatedAt())
                .updatedAt(metadata.getUpdatedAt())
                .remindAt(metadata.getRemindAt())
                .pinned(metadata.isPinned())
                .priority(metadata.getPriority())
                .status(metadata.getStatus())
                .type(metadata.getType())
                .build();
    }

    public static Metadata mapDtoToEntity(MetadataDto metadataDto) {
        return Metadata.builder()
                .id(metadataDto.getId())
                .title(metadataDto.getTitle())
                .createdAt(metadataDto.getCreatedAt())
                .updatedAt(metadataDto.getUpdatedAt())
                .remindAt(metadataDto.getRemindAt())
                .pinned(metadataDto.isPinned())
                .priority(metadataDto.getPriority())
                .status(metadataDto.getStatus())
                .type(metadataDto.getType())
                .build();
    }
}


