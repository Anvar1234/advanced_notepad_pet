package ru.yandex.kingartaved.service.content_service.impl;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.model.ChecklistItem;
import ru.yandex.kingartaved.data.model.ChecklistContent;
import ru.yandex.kingartaved.dto.ChecklistContentDto;
import ru.yandex.kingartaved.service.content_service.ContentService;

public class ChecklistContentService implements ContentService<ChecklistContentDto> {

//    private Map<NoteServiceCommandEnum, методы этого класса> commandsAndActionsRegistry;

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
//    public void executeCommand(NoteServiceCommandEnum command, int id) {
//        if (NoteServiceCommandEnum.CREATE == command) {
//            createContent();
//        } else if (NoteServiceCommandEnum.REMOVE == command) {
//            removeContent();
//        }
//    }
//
//    @Override
//    public ChecklistContent update(ChecklistContent content, ContentUpdateCommand command) {
//        return new ChecklistContent(List.of());
//    }

//    public Map<NoteServiceCommandEnum,методы> getCommandsAndActionsRegistry() {
//        return this.commandsAndActionsRegistry;
//    }
}
