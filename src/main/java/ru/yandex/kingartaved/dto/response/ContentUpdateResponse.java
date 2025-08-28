package ru.yandex.kingartaved.dto.response;

import ru.yandex.kingartaved.dto.ChecklistContentDto;
import ru.yandex.kingartaved.dto.ContentDto;

public sealed abstract class ContentUpdateResponse permits ChecklistContentUpdateResponse, TextContentUpdateResponse  {

    public abstract ContentDto getUpdatedContent();

    public abstract ContentUpdateResult getResult();
}
