package ru.yandex.kingartaved.data.mapper.impl;

import ru.yandex.kingartaved.data.mapper.MetadataMapper;
import ru.yandex.kingartaved.data.model.Metadata;
import ru.yandex.kingartaved.dto.MetadataDto;

public class MetadataMapperImpl implements MetadataMapper {
    @Override
    public MetadataDto mapEntityToDto(Metadata metadata) {
        return MetadataDto.builder()
                .id()

    @Override
    public Metadata mapDtoToEntity(MetadataDto metadataDto) {
        return null;
    }
}
