package ru.yandex.kingartaved.service.content_service;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.model.Content;

public interface ContentService {

    Content createContent(NoteTypeEnum type);
//    T update(T content, ContentUpdateCommand command);
    NoteTypeEnum getSupportedType();


}
