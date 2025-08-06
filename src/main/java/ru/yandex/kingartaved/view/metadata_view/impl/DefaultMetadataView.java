package ru.yandex.kingartaved.view.metadata_view.impl;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.dto.MetadataDto;
import ru.yandex.kingartaved.dto.request.CreateNewMetadataRequestDto;
import ru.yandex.kingartaved.view.metadata_view.MetadataView;

import java.util.Scanner;

public class DefaultMetadataView implements MetadataView {
    @Override
    public CreateNewMetadataRequestDto createMetadataDto(Scanner scanner, NoteTypeEnum type) {
        System.out.println("Введите заголовок заметки: ");
        String title = scanner.nextLine(); //todo: если пустой, то в NoteService берет данные из контента.

        return new CreateNewMetadataRequestDto(title, type);
    }


    public void renderHeader(MetadataDto metadataDto, int tableWidth, String delimiterSymbol) {
//        System.out.println("Тип: " + metadataDto.getType().getDescription());
        System.out.println("Название: " + metadataDto.getTitle());
        String textHeaderAndBodyDelimiter = delimiterSymbol.repeat(tableWidth);
        System.out.println(textHeaderAndBodyDelimiter);
    }

    public void renderFooter(MetadataDto metadataDto) {
        System.out.printf
                ("""
                                Создана: %s
                                Изменена: %s
                                Статус: %s
                                """,
                        metadataDto.getCreatedAt(),
                        metadataDto.getUpdatedAt(),
                        metadataDto.getStatus().getDescription()
                );
    }


}
