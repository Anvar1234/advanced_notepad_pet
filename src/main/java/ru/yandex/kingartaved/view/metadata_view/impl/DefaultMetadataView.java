package ru.yandex.kingartaved.view.metadata_view.impl;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.model.Metadata;
import ru.yandex.kingartaved.dto.MetadataDto;
import ru.yandex.kingartaved.view.metadata_view.MetadataView;

import java.util.Scanner;

public class DefaultMetadataView implements MetadataView {
    @Override
    public MetadataDto createMetadataDto(Scanner scanner, NoteTypeEnum type) {
        System.out.println("Введите заголовок заметки: ");
        String title = scanner.nextLine(); //todo: если пустой, то в NoteService берет данные из контента.

        return MetadataDto.builder()
                .type(type)
                .title(title)
                .build();
    }
}
