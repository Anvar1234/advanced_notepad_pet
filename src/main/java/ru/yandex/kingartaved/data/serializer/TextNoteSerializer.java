package ru.yandex.kingartaved.data.serializer;

import ru.yandex.kingartaved.data.constant.NotePriorityEnum;
import ru.yandex.kingartaved.data.constant.NoteStatusEnum;
import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.model.TextNote;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class TextNoteSerializer implements NoteSerializer<TextNote> {
    //TODO: добавить валидацию перед (де-)сериализацией.
    @Override
    public String serialize(TextNote note) {
        return String.join("|",
                note.getId().toString(),
                note.getTitle(),
                note.getCreatedDateTime().toString(),
                note.getChangedDateTime().toString(),
                note.getRemainderDate() != null ? note.getRemainderDate().toString() : "",
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