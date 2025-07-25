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

    public void getHeader(MetadataDto metadataDto){
        System.out.println("Тип: " + metadataDto.getType().getDescription());
        System.out.println("Название: " + metadataDto.getTitle());
    }

    public void getFooter(MetadataDto metadataDto){
        System.out.printf
                ("Создано: %s | Изменено: %s | Статус: %s",
                        metadataDto.getCreatedAt(),
                        metadataDto.getUpdatedAt(),
                        metadataDto.getStatus().getDescription()
                );
    }


}
