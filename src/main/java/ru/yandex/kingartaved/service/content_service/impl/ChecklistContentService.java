package ru.yandex.kingartaved.service.content_service.impl;

import ru.yandex.kingartaved.data.model.ChecklistItem;
import ru.yandex.kingartaved.data.model.Content;
import ru.yandex.kingartaved.data.model.impl.ChecklistContent;
import ru.yandex.kingartaved.dto.ContentDto;
import ru.yandex.kingartaved.dto.impl.ChecklistContentDto;
import ru.yandex.kingartaved.service.constant.ServiceCommandEnum;
import ru.yandex.kingartaved.service.content_service.ContentService;

import java.util.List;

public class ChecklistContentService implements ContentService<ChecklistContent> {

//    private Map<ServiceCommandEnum, методы этого класса> commandsAndActionsRegistry;

    @Override
    public ChecklistContent createContent(ContentDto contentDto) {
        if (!(contentDto instanceof ChecklistContentDto dto)) {
            throw new IllegalArgumentException("Ожидался ChecklistContentDto");
        }

        return new ChecklistContent(dto.items().stream()
                .map(itemDto -> new ChecklistItem(
                        itemDto.text(),
                        itemDto.isDone()))
                .toList());
    }


//    @Override
//    public void removeContent() {
//
//    }
//
//    @Override
//    public void executeCommand(ServiceCommandEnum command, int id) {
//        if (ServiceCommandEnum.CREATE == command) {
//            createContent();
//        } else if (ServiceCommandEnum.REMOVE == command) {
//            removeContent();
//        }
//    }
//
//    @Override
//    public ChecklistContent update(ChecklistContent content, ContentUpdateCommand command) {
//        return new ChecklistContent(List.of());
//    }

//    public Map<ServiceCommandEnum,методы> getCommandsAndActionsRegistry() {
//        return this.commandsAndActionsRegistry;
//    }
}
