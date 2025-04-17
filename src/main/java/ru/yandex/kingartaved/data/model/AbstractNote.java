package ru.yandex.kingartaved.data.model;

import ru.yandex.kingartaved.data.constant.NotePriorityEnum;
import ru.yandex.kingartaved.data.constant.NoteStatusEnum;
import ru.yandex.kingartaved.data.constant.NoteTypeEnum;

import java.time.LocalDateTime;
import java.util.UUID;


public abstract class AbstractNote {
    // TODO: В будущем можно добавить автора, если будет работать с несколькими пользователями, типа общие заметки.
    //TODO: это - метаданные:
    private UUID id;
    private String title; // nullable
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;
    private LocalDateTime remindAt; // nullable. Планируемая дата и время выполнения, напоминание выводится *условно* за час до времени.
    private boolean isPinned; //Закреп или не закреп. Заметки с закреп отображаются наверху списка и сортируются меж собой, а незакреп сортируются уже между собой.
    private NotePriorityEnum priority; // просто возможность сортировки.
//    private Set<String> tags; // Для категоризации и поиска.
    private NoteStatusEnum status; //ACTIVE, COMPLETED, POSTPONED, ARCHIVED - активные - это созданные и возвращенные из завершенных или архива, обычные заметки. Отложенные - убираются из списка, но периодически возвращаются в список активных и напоминают о себе и выводятся в список исходя из поля ремайндер - для null - условно раз в день (датавремя изменения статуса плюс один день), для не null - в указанное время. Завершенные - те которые завершены, отображаются внизу списка; пользователь может удалить ее или убрать в архив. Архив - если завершенная задача сохраняется в архивном разделе (отдельном списке) и убирается из завершенных.
    private NoteTypeEnum type; // Тип - список или просто заметка. TODO: потом можно сделать этот класс абстр, и 2 наследника - типа список и просто заметка.

    protected AbstractNote(AbstractNoteBuilder<?> abstractNoteBuilder) {
        this.id = abstractNoteBuilder.id;
        this.title = abstractNoteBuilder.title;
        this.createdAt = abstractNoteBuilder.createdAt;
        this.updatedAt = abstractNoteBuilder.changedAt;
        this.remindAt = abstractNoteBuilder.remindAt;
        this.isPinned = abstractNoteBuilder.isPinned;
        this.priority = abstractNoteBuilder.priority;
//        this.tags = abstractNoteBuilder.tags;
        this.status = abstractNoteBuilder.status;
        this.type = abstractNoteBuilder.type;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getRemindAt() {
        return remindAt;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public NotePriorityEnum getPriority() {
        return priority;
    }

//    public Set<String> getTags() {
//        return tags;
//    }

    public NoteStatusEnum getStatus() {
        return status;
    }

    public NoteTypeEnum getType() {
        return type;
    }

    // Абстрактный Builder
    public abstract static class AbstractNoteBuilder<T extends AbstractNoteBuilder<T>> {
        private UUID id = UUID.randomUUID(); // Генерация ID по умолчанию
        private String title; // nullable
        private LocalDateTime createdAt = LocalDateTime.now(); // Текущее время по умолчанию
        private LocalDateTime changedAt = LocalDateTime.now(); // Текущее время по умолчанию
        private LocalDateTime remindAt; // nullable
        private boolean isPinned = false; // По умолчанию не закреплено
        private NotePriorityEnum priority = NotePriorityEnum.BASE; // По умолчанию LOW
//        private Set<String> tags = new HashSet<>(); // Пустой набор по умолчанию
        private NoteStatusEnum status = NoteStatusEnum.ACTIVE; // По умолчанию ACTIVE
        private NoteTypeEnum type ; // Установка в наследниках.

        // Методы для установки значений
        public T setId(UUID id) {
            this.id = id;
            return self();
        }

        public T setTitle(String title) {
            this.title = title;
            return self();
        }

        public T setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return self();
        }

        public T setChangedAt(LocalDateTime changedAt) {
            this.changedAt = changedAt;
            return self();
        }

        public T setRemindAt(LocalDateTime remindAt) {
            this.remindAt = remindAt;
            return self();
        }

        public T setPinned(boolean isPinned) {
            this.isPinned = isPinned;
            return self();
        }

        public T setPriority(NotePriorityEnum priority) {
            this.priority = priority;
            return self();
        }

//        public T setTags(Set<String> tags) {
//            this.tags = tags;
//            return self();
//        }
//
//        public T addTag(String tag) { // TODO: возможно удалить.
//            this.tags.add(tag);
//            return self();
//        }

        public T setStatus(NoteStatusEnum status) {
            this.status = status;
            return self();
        }

        public T setType(NoteTypeEnum type){
            this.type = type;
            return self();
        }

        // Метод для возврата this (self-type)
        protected abstract T self();

        // Метод для создания объекта Note
        public abstract AbstractNote build();
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", name='" + title + '\'' +
                ", createdDateTime=" + createdAt +
                ", changedDateTime=" + updatedAt +
                ", remainderDate=" + remindAt +
                ", isPinned=" + isPinned +
                ", priority=" + priority +
//                ", tags=" + tags +
                ", status=" + status +
                ", type=" + type.getNoteType() +
                '}';
    }
}




