package ru.yandex.kingartaved.service.content_service.impl;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.model.ChecklistItem;
import ru.yandex.kingartaved.data.model.impl.ChecklistContent;
import ru.yandex.kingartaved.dto.impl.ChecklistContentDto;
import ru.yandex.kingartaved.service.content_service.ContentService;

public class ChecklistContentService implements ContentService<ChecklistContentDto> {

//    private Map<ServiceCommandEnum, методы этого класса> commandsAndActionsRegistry;

    @Override
    public ChecklistContent createContent(ChecklistContentDto validContentDto) {

        return new ChecklistContent(
                validContentDto.tasks().stream()
                .map(itemDto -> new ChecklistItem(
                        itemDto.text(),
                        itemDto.isDone()))
                .toList());
    }

    @Override
    public NoteTypeEnum getSupportedType() {
        return NoteTypeEnum.CHECKLIST;
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
