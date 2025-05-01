package ru.yandex.kingartaved.data.mapper;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.model.Content;
import ru.yandex.kingartaved.dto.ContentDto;

public interface ContentMapper<C extends Content, D extends ContentDto> {
    D mapEntityToDto(C content);
    C mapDtoToEntity(D contentDto);
    NoteTypeEnum getSupportedType();
}
