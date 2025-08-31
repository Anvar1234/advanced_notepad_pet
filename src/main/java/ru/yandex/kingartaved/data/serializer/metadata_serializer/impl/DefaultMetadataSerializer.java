package ru.yandex.kingartaved.data.serializer.metadata_serializer.impl;

import ru.yandex.kingartaved.data.constant.FieldIndex;
import ru.yandex.kingartaved.data.constant.NotePriorityEnum;
import ru.yandex.kingartaved.data.constant.NoteStatusEnum;
import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.model.Metadata;
import ru.yandex.kingartaved.data.serializer.metadata_serializer.MetadataSerializer;
import ru.yandex.kingartaved.validation.FieldValidationUtil;

import java.time.LocalDateTime;
import java.util.UUID;

public class DefaultMetadataSerializer implements MetadataSerializer {

    @Override
    public String serializeMetadata(Metadata metadata) {
        return String.join("|",
                metadata.getId().toString(),
                metadata.getTitle(),
                metadata.getCreatedAt().toString(),
                metadata.getUpdatedAt().toString(),
                metadata.getRemindAt() != null ? metadata.getRemindAt().toString() : "null",
                String.valueOf(metadata.isPinned()),
                metadata.getPriority().name(),
                metadata.getStatus().name(),
                metadata.getType().name()
        );
    }

    @Override
    public Metadata deserializeMetadata(String[] parts) {
        return Metadata.builder()
                .id(UUID.fromString(parts[FieldIndex.ID.getIndex()]))
                .title(parts[FieldIndex.TITLE.getIndex()])
                .createdAt(LocalDateTime.parse(parts[FieldIndex.CREATED_AT.getIndex()]))
                .updatedAt(LocalDateTime.parse(parts[FieldIndex.UPDATED_AT.getIndex()]))
                .remindAt(FieldValidationUtil.isNotStringNull(parts[FieldIndex.REMIND_AT.getIndex()])
                        ? LocalDateTime.parse(parts[FieldIndex.REMIND_AT.getIndex()])
                        : null)
                .pinned(Boolean.parseBoolean(parts[FieldIndex.PINNED.getIndex()]))
                .priority(Enum.valueOf(NotePriorityEnum.class, parts[FieldIndex.PRIORITY.getIndex()]))
                .status(Enum.valueOf(NoteStatusEnum.class, parts[FieldIndex.STATUS.getIndex()]))
                .type(Enum.valueOf(NoteTypeEnum.class, parts[FieldIndex.TYPE.getIndex()]))
                .build();
    }
}
