package ru.yandex.kingartaved.view.metadata_view.impl;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.dto.MetadataDto;
import ru.yandex.kingartaved.dto.request.CreateNewMetadataRequestDto;
import ru.yandex.kingartaved.dto.request.UpdateMetadataRequestDto;
import ru.yandex.kingartaved.view.metadata_view.MetadataView;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class DefaultMetadataView implements MetadataView {
    @Override
    public CreateNewMetadataRequestDto createMetadataDto(Scanner scanner, NoteTypeEnum type) {
        System.out.println("Введите заголовок заметки (пустой ввод - заголовок по умолчанию): ");
        String title = scanner.nextLine(); //todo: если пустой, то устанавливаем название по умолчанию.

        if (title.isBlank()) {
            if (type == NoteTypeEnum.TEXT_NOTE) {
                title = "Текстовая заметка";
            } else if (type == NoteTypeEnum.CHECKLIST) {
                title = "Список дел";
            }
        }
        return new CreateNewMetadataRequestDto(title, type);
    }

    @Override
    public MetadataDto updateMetadataDto(MetadataDto oldMetadataDto, UpdateMetadataRequestDto updateMetadataRequestDto) {
        MetadataDto.Builder metadatBuilder = MetadataDto.builder();
        if (updateMetadataRequestDto.getTitle() != null) {
            metadatBuilder.title(updateMetadataRequestDto.getTitle());
        } else {
            metadatBuilder.title(oldMetadataDto.getTitle());
        }
        if (updateMetadataRequestDto.getRemindAt() != null) {
            metadatBuilder.remindAt(updateMetadataRequestDto.getRemindAt());
        } else {
            metadatBuilder.remindAt(oldMetadataDto.getRemindAt());
        }
        if (updateMetadataRequestDto.getPinned() != null) {
            metadatBuilder.pinned(updateMetadataRequestDto.getPinned());
        } else {
            metadatBuilder.pinned(oldMetadataDto.isPinned());
        }
        metadatBuilder.id(oldMetadataDto.getId());
        metadatBuilder.createdAt(oldMetadataDto.getCreatedAt());
        metadatBuilder.priority(oldMetadataDto.getPriority());
        metadatBuilder.status(oldMetadataDto.getStatus());
        metadatBuilder.type(oldMetadataDto.getType());

        metadatBuilder.updatedAt(LocalDateTime.now());

        return metadatBuilder.build();
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
    public String getMetadataPreview(int index, MetadataDto metadataDto) {
        int maxTypeLength = Arrays.stream(NoteTypeEnum.values())
                .map(NoteTypeEnum::getDescription)
                .mapToInt(String::length)
                .max()
                .orElse(0);

        return String.format("%02d. %-" + maxTypeLength + "s|%s: ",
                index + 1,
                metadataDto.getType().getDescription(),
                metadataDto.getTitle());
    }
}
