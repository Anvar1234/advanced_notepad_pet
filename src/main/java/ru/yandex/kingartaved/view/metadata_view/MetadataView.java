package ru.yandex.kingartaved.view.metadata_view;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.dto.MetadataDto;
import ru.yandex.kingartaved.dto.request.CreateNewMetadataRequestDto;
import ru.yandex.kingartaved.dto.request.UpdateMetadataRequestDto;

import java.util.Scanner;

public interface MetadataView {

    CreateNewMetadataRequestDto createMetadataDto(Scanner scanner, NoteTypeEnum type);

    MetadataDto updateMetadataDto(MetadataDto oldMetadataDto, UpdateMetadataRequestDto updateMetadataRequestDto);

    void renderMetadataForHeader(MetadataDto metadataDto);

    void renderMetadataForFooter(MetadataDto metadataDto);

    String getMetadataPreview(int id, MetadataDto metadataDto);
}
