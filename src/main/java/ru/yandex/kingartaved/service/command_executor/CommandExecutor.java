package ru.yandex.kingartaved.service.command_executor;

import ru.yandex.kingartaved.service.command_executor.constant.ServiceCommandEnum;

public interface CommandExecutor {
    void executeCommand(ServiceCommandEnum command);
}
