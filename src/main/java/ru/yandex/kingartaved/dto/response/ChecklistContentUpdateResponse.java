package ru.yandex.kingartaved.dto.response;

import ru.yandex.kingartaved.dto.ChecklistContentDto;

public final class ChecklistContentUpdateResponse extends ContentUpdateResponse {
    private final ContentUpdateResult result;
    private final ChecklistContentDto updatedContent;

    public ChecklistContentUpdateResponse(ContentUpdateResult result,
                                          ChecklistContentDto updatedContent) {
        this.result = result;
        this.updatedContent = updatedContent;
    }

    public ChecklistContentDto getUpdatedContent() {
        return updatedContent;
    }

    public ContentUpdateResult getResult() {
        return result;
    }
}