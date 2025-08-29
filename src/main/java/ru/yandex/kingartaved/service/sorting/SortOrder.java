package ru.yandex.kingartaved.service.sorting;

import ru.yandex.kingartaved.data.model.Note;

import java.util.Comparator;

public record SortOrder(
        SortField field,
        SortDirection direction
) {

    public enum SortField {
        TITLE,
        CREATED_AT,
        UPDATED_AT,
        TYPE;
    }

    public enum SortDirection {
        ASC, DESC
    }

    public Comparator<Note> toComparator() {
        Comparator<Note> firstPinnedComparator = Comparator.comparing(note -> note.getMetadata().isPinned(), Comparator.reverseOrder());

        Comparator<Note> secondaryComparator = switch (field) {
            case TITLE -> Comparator.comparing(note -> note.getMetadata().getTitle());
            case CREATED_AT -> Comparator.comparing(note -> note.getMetadata().getCreatedAt());
            case UPDATED_AT -> Comparator.comparing(note -> note.getMetadata().getUpdatedAt());
            case TYPE -> Comparator.comparing(note -> note.getMetadata().getType());
        };
        secondaryComparator = direction == SortDirection.ASC ? secondaryComparator : secondaryComparator.reversed();

        return firstPinnedComparator.thenComparing(secondaryComparator);
    }
}
