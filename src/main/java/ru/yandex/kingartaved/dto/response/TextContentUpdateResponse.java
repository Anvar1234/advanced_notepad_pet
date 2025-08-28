package ru.yandex.kingartaved.dto.response;

import ru.yandex.kingartaved.dto.ChecklistContentDto;
import ru.yandex.kingartaved.dto.TextContentDto;

public final class TextContentUpdateResponse extends ContentUpdateResponse {
    private final ContentUpdateResult result;
    private final TextContentDto updatedContent;

    public TextContentUpdateResponse(ContentUpdateResult result, TextContentDto updatedContent) {
        this.result = result;
        this.updatedContent = updatedContent;
    }

    @Override
    public ContentUpdateResult getResult() {
        return result;
    }

    @Override
    public TextContentDto getUpdatedContent() {
        return updatedContent;
    }
}
