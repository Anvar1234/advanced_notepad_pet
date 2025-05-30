package ru.yandex.kingartaved.data.model;

import ru.yandex.kingartaved.data.constant.NotePriorityEnum;
import ru.yandex.kingartaved.data.constant.NoteStatusEnum;
import ru.yandex.kingartaved.data.constant.NoteTypeEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public final class Metadata {
    private final UUID id;
    private final String title;
    private final LocalDateTime createdAt;
    private final LocalDateTime remindAt;
    private final LocalDateTime updatedAt;
    private final boolean pinned;
    private final NotePriorityEnum priority;
    private final NoteStatusEnum status;
    private final NoteTypeEnum type;

    private Metadata(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.createdAt = builder.createdAt;
        this.remindAt = builder.remindAt;
        this.updatedAt = builder.updatedAt;
        this.pinned = builder.pinned;
        this.priority = builder.priority;
        this.status = builder.status;
        this.type = builder.type;
    }

    public static Builder builder(){
        return new Builder();
    }

    //TODO: переделать, некоторые поля должны быть инициализированы по умолчанию.
    public static class Builder {
        private UUID id;
        private String title;
        private LocalDateTime createdAt;
        private LocalDateTime remindAt;
        private LocalDateTime updatedAt;
        private boolean pinned;
        private NotePriorityEnum priority;
        private NoteStatusEnum status;
        private NoteTypeEnum type;

        private Builder(){}

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder remindAt(LocalDateTime remindAt) {
            this.remindAt = remindAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Builder pinned(boolean pinned) {
            this.pinned = pinned;
            return this;
        }

        public Builder priority(NotePriorityEnum priority) {
            this.priority = priority;
            return this;
        }

        public Builder status(NoteStatusEnum status) {
            this.status = status;
            return this;
        }

        public Builder type(NoteTypeEnum type) {
            this.type = type;
            return this;
        }

        public Metadata build() {
            return new Metadata(this);
        }
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

    public LocalDateTime getRemindAt() {
        return remindAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public boolean isPinned() {
        return pinned;
    }

    public NotePriorityEnum getPriority() {
        return priority;
    }

    public NoteStatusEnum getStatus() {
        return status;
    }

    public NoteTypeEnum getType() {
        return type;
    }
}