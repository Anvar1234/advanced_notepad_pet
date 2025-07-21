package ru.yandex.kingartaved.service.metadata_service;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.model.Metadata;
import ru.yandex.kingartaved.dto.MetadataDto;

public interface MetadataService {

    Metadata createMetadata(MetadataDto validMetadataDto);
}

