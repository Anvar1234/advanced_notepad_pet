package ru.yandex.kingartaved.view.metadata_view;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.dto.MetadataDto;
import ru.yandex.kingartaved.dto.request.CreateNewMetadataRequestDto;

import java.util.Scanner;

public interface MetadataView {

    CreateNewMetadataRequestDto createMetadataDto(Scanner scanner, NoteTypeEnum type);
    void renderMetadataForHeader(MetadataDto metadataDto);
    void renderMetadataForFooter(MetadataDto metadataDto);
    String getMetadataPreview(MetadataDto metadataDto);
}
