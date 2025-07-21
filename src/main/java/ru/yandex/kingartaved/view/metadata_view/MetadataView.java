package ru.yandex.kingartaved.view.metadata_view;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.dto.MetadataDto;

import java.util.Scanner;

public interface MetadataView {

    MetadataDto createMetadataDto(Scanner scanner, NoteTypeEnum type);
}
