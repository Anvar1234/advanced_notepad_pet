package ru.yandex.kingartaved.service.metadata_service.impl;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.model.Metadata;
import ru.yandex.kingartaved.dto.MetadataDto;
import ru.yandex.kingartaved.dto.request.CreateNewMetadataRequestDto;
import ru.yandex.kingartaved.service.metadata_service.MetadataService;

public class DefaultMetadataService implements MetadataService {

    //todo: а здесь не нужно возвращать ДТО а не Метадата?
    @Override
    public Metadata createMetadata(CreateNewMetadataRequestDto createNewMetadataRequestDto) { //здесь все поля кроме title & type == null
        //todo: здесь маппинг, видимо и проверка на пустоту заголовка.
        return Metadata.builder()
                .title(createNewMetadataRequestDto.title())
                .type(createNewMetadataRequestDto.type())
                .build(); //а на этом моменте все поля кроме title & type инициализируются по умолчанию.
    }
}
