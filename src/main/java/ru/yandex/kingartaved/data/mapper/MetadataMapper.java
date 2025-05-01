package ru.yandex.kingartaved.data.mapper;

import ru.yandex.kingartaved.data.model.Metadata;
import ru.yandex.kingartaved.dto.MetadataDto;

public interface MetadataMapper {
    MetadataDto mapEntityToDto(Metadata metadata);
    Metadata mapDtoToEntity(MetadataDto metadataDto);
}
