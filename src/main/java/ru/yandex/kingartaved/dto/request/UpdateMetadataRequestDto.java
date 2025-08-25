package ru.yandex.kingartaved.dto.request;

import ru.yandex.kingartaved.data.constant.NotePriorityEnum;
import ru.yandex.kingartaved.data.constant.NoteStatusEnum;

import java.time.LocalDateTime;

public class UpdateMetadataRequestDto {
    private String title;
    private LocalDateTime remindAt;
    private LocalDateTime updatedAt;
    private Boolean pinned;
    private NotePriorityEnum priority;
    private NoteStatusEnum status;

    public UpdateMetadataRequestDto() {
    }

    public UpdateMetadataRequestDto(String title, LocalDateTime createdAt, LocalDateTime remindAt, LocalDateTime updatedAt, boolean pinned, NotePriorityEnum priority, NoteStatusEnum status) {
        this.title = title;
        this.remindAt = remindAt;
        this.updatedAt = updatedAt;
        this.pinned = pinned;
        this.priority = priority;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getRemindAt() {
        return remindAt;
    }

    public void setRemindAt(LocalDateTime remindAt) {
        this.remindAt = remindAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getPinned() {
        return pinned;
    }

    public void setPinned(Boolean pinned) {
        this.pinned = pinned;
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
}
