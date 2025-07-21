package ru.yandex.kingartaved.service.content_service;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.model.Content;
import ru.yandex.kingartaved.dto.ContentDto;

public interface ContentService<T extends ContentDto> {

    Content createContent(T validContentDto);
//    T update(T content, ContentUpdateCommand command);
    NoteTypeEnum getSupportedType();


}
