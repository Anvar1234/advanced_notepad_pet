package ru.yandex.kingartaved.dto;

import ru.yandex.kingartaved.data.constant.NotePriorityEnum;
import ru.yandex.kingartaved.data.constant.NoteStatusEnum;
import ru.yandex.kingartaved.data.constant.NoteTypeEnum;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public abstract class AbstractNoteDto {
    private UUID id;
    private String title;
    private LocalDateTime createdDateTime;
    private LocalDateTime changedDateTime;
    private LocalDateTime remainderDate;
    private boolean isPinned;
    private NotePriorityEnum priority;
    private NoteStatusEnum status;
    private NoteTypeEnum type;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public LocalDateTime getChangedDateTime() {
        return changedDateTime;
    }

    public void setChangedDateTime(LocalDateTime changedDateTime) {
        this.changedDateTime = changedDateTime;
    }

    public LocalDateTime getRemainderDate() {
        return remainderDate;
    }

    public void setRemainderDate(LocalDateTime remainderDate) {
        this.remainderDate = remainderDate;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public void setPinned(boolean pinned) {
        isPinned = pinned;
    }

    public NotePriorityEnum getPriority() {
        return priority;
    }

    public void setPriority(NotePriorityEnum priority) {
        this.priority = priority;
    }

    public NoteStatusEnum getStatus() {
        return status;
    }

    public void setStatus(NoteStatusEnum status) {
        this.status = status;
    }

    public NoteTypeEnum getType() {
        return type;
    }

    public void setType(NoteTypeEnum type) {
        this.type = type;
    }
}
