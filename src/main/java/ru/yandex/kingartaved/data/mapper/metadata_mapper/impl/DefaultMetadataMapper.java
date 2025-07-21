package ru.yandex.kingartaved.data.mapper.metadata_mapper.impl;

import ru.yandex.kingartaved.data.mapper.metadata_mapper.MetadataMapper;
import ru.yandex.kingartaved.data.model.Metadata;
import ru.yandex.kingartaved.dto.MetadataDto;
import ru.yandex.kingartaved.exception.constant.ErrorMessage;

public class DefaultMetadataMapper implements MetadataMapper {

    private DefaultMetadataMapper() {
        throw new UnsupportedOperationException(ErrorMessage.UTILITY_CLASS.getMessage());
    }

    @Override
    public MetadataDto mapEntityToDto(Metadata metadata) {
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

    @Override
    public Metadata mapDtoToEntity(MetadataDto metadataDto) {
        Metadata.Builder builder = Metadata.builder();

        if (metadataDto.getId() != null) {
            builder.id(metadataDto.getId());
        }

        if (metadataDto.getTitle() != null) {
            builder.title(metadataDto.getTitle());
        }

        if (metadataDto.getCreatedAt() != null) {
            builder.createdAt(metadataDto.getCreatedAt());
        }

        if (metadataDto.getUpdatedAt() != null) {
            builder.updatedAt(metadataDto.getUpdatedAt());
        }

        builder.remindAt(metadataDto.getRemindAt()); // todo: remindAt устанавливается всегда, даже если null, так как может быть null.

        builder.pinned(metadataDto.isPinned()); //todo: устанавливается всегда: если заметка новая, то установится по умолчанию, если придет true/false - тоже установится.

        if (metadataDto.getPriority() != null) {
            builder.priority(metadataDto.getPriority());
        }

        if (metadataDto.getStatus() != null) {
            builder.status(metadataDto.getStatus());
        }

        if (metadataDto.getType() != null) {
            builder.type(metadataDto.getType());
        }

        return builder.build();
    }
}


