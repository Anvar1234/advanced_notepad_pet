package ru.yandex.kingartaved.service.command_executor;

import ru.yandex.kingartaved.service.command_executor.constant.NoteServiceCommandEnum;

public interface CommandExecutor {
    void executeCommand(NoteServiceCommandEnum command);
}
