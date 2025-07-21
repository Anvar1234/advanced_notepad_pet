package ru.yandex.kingartaved.dto;

import java.util.List;

public record NoteDto(MetadataDto metadataDto, ContentDto contentDto) {

    public void preview(List<NoteSummaryDto>){ //TODO: превью в общем списке

    }

    public void render(ыа){ //TODO: как отображается при выборе

    }
}
