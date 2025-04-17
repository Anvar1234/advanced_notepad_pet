package ru.yandex.kingartaved.dto;

import ru.yandex.kingartaved.data.model.ChecklistItem;

import java.util.List;

public class CheckListDto extends AbstractNoteDto {

    private List<ChecklistItem> content;

    public List<ChecklistItem> getContent() {
        return content;
    }

    public void setContent(List<ChecklistItem> content) {
        this.content = content;
    }
}
