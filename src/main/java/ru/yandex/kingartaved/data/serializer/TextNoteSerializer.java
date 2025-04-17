package ru.yandex.kingartaved.data.serializer;

import ru.yandex.kingartaved.data.constant.NotePriorityEnum;
import ru.yandex.kingartaved.data.constant.NoteStatusEnum;
import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.validation.db_line_validator.DbLineValidator;

import java.time.LocalDateTime;
import java.util.UUID;

public class TextNoteSerializer implements NoteSerializer<TextNote> {
    //TODO: добавить валидацию перед (де-)сериализацией.
    private TextNoteSerializer instance;
    private final DbLineValidator validator;

    private TextNoteSerializer(DbLineValidator validator) {
        this.validator = validator;
    }

    public TextNoteSerializer getInstance(DbLineValidator validator) { //TODO: это не singletone - удалить или изменить.
        if (instance == null) {
            return new TextNoteSerializer(validator);
        }
        return this;
    }

    @Override
    public String serialize(TextNote note) {
        return String.join("|",
                note.getId().toString(),
                note.getTitle(),
                note.getCreatedAt().toString(),
                note.getUpdatedAt().toString(),
                note.getRemindAt() != null ? note.getRemindAt().toString() : "",
                String.valueOf(note.isPinned()),
                note.getPriority().name(),
//                String.join(",", note.getTags()),
                note.getStatus().name(),
                note.getType().name(),
                note.getContent()
        );
    }

    @Override
    public TextNote deserialize(String line) {

        validator.validate(line);

        String[] parts = line.split("\\|", -1);  // -1 сохраняет пустые значения

        return new TextNote.TextNoteBuilder()
                .setId(UUID.fromString(parts[0]))                 // id
                .setTitle(parts[1])                                  // title
                .setCreatedAt(LocalDateTime.parse(parts[2]))          // createdAt
                .setChangedAt(LocalDateTime.parse(parts[3]))  // changedAt
                .setRemindAt(parts[4].isEmpty() ? null : LocalDateTime.parse(parts[4])) // deadline
                .setPinned(Boolean.parseBoolean(parts[5])) // isPinned
                .setPriority(NotePriorityEnum.valueOf(parts[6])) // priority
//                .setTags(Set.of(parts[7].split(","))) // tags
                .setStatus(NoteStatusEnum.valueOf(parts[8])) // status
                .setType(NoteTypeEnum.valueOf(parts[9])) // type
                .setContent(parts[10]) // content
                .build();
    }
}