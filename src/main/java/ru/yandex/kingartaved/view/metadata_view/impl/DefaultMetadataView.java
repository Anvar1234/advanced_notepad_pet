package ru.yandex.kingartaved.view.metadata_view.impl;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.dto.MetadataDto;
import ru.yandex.kingartaved.dto.request.CreateNewMetadataRequestDto;
import ru.yandex.kingartaved.view.metadata_view.MetadataView;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class DefaultMetadataView implements MetadataView {
    @Override
    public CreateNewMetadataRequestDto createMetadataDto(Scanner scanner, NoteTypeEnum type) {
        System.out.println("Введите заголовок заметки: ");
        String title = scanner.nextLine(); //todo: если пустой, то в NoteService берет данные из контента.

        return new CreateNewMetadataRequestDto(title, type);
    }

    public void noteMetadataMenu() {

    }

    @Override
    public void renderMetadataForHeader(MetadataDto metadataDto) {
//        System.out.println("Тип: " + metadataDto.getType().getDescription());
        System.out.println("Название: " + metadataDto.getTitle());
//        String textHeaderAndBodyDelimiter = delimiterSymbol.repeat(tableWidth);
//        System.out.println(textHeaderAndBodyDelimiter);
    }

    @Override
    public void renderMetadataForFooter(MetadataDto metadataDto) {
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
    //todo: type + title не будет больше "текстовая заметка"+"10 символов заголовка", но возможно нужно проверять чтобы это было не больше TABLE_WIDTH
    @Override
    public String getMetadataPreview(MetadataDto metadataDto) {
        int maxTypeLength = Arrays.stream(NoteTypeEnum.values())
                .map(NoteTypeEnum::getDescription)
                .mapToInt(String::length)
                .max()
                .orElse(0);

        return String.format("%" + maxTypeLength + "s|%s: ",
                metadataDto.getType().getDescription(),
                metadataDto.getTitle());
    }


}
