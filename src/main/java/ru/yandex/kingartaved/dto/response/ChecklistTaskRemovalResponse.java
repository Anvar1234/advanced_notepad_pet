package ru.yandex.kingartaved.dto.response;

import ru.yandex.kingartaved.dto.ChecklistTaskDto;

import java.util.List;

public class ChecklistTaskRemovalResponse {
    private final TaskRemovalResult result;
    private final List<ChecklistTaskDto> updatedTasks;

    public ChecklistTaskRemovalResponse(TaskRemovalResult result,
                                        List<ChecklistTaskDto> updatedTasks) {
        this.result = result;
        this.updatedTasks = List.copyOf(updatedTasks);
    }

    public List<ChecklistTaskDto> getUpdatedTasks() {
        return updatedTasks;
    }

    public TaskRemovalResult getResult() {
        return result;
    }

    public enum TaskRemovalResult {
        TASK_REMOVED("Задача удалена"),
        ALL_TASKS_REMOVED("Все задачи удалены (заметка должна быть удалена)"),
        OPERATION_CANCELLED("Операция отменена"),
        EMPTY_CHECKLIST("Чек-лист пуст. Удаление задач невозможно");

        private final String description;

        TaskRemovalResult(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}