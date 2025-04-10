package ru.yandex.kingartaved.dto;

import ru.yandex.kingartaved.data.model.CheckListItem;

import java.util.List;

public class CheckListDto extends AbstractNoteDto {

    private List<CheckListItem> content;

    public List<CheckListItem> getContent() {
        return content;
    }

    public void setContent(List<CheckListItem> content) {
        this.content = content;
    }
}
