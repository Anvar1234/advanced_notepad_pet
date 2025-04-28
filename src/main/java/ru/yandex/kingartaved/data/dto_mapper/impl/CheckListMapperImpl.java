package ru.yandex.kingartaved.data.dto_mapper.impl;

import ru.yandex.kingartaved.data.dto_mapper.NoteMapper;

public class CheckListMapperImpl implements NoteMapper<CheckList, ChecklistDto> {
    public ChecklistDto mapEntityToDto(CheckList checkList) {
        ChecklistDto checkListFlatDto = new ChecklistDto();
        checkListFlatDto.setId(checkList.getId());
        checkListFlatDto.setTitle(checkList.getTitle());
        checkListFlatDto.setCreatedDateTime(checkList.getCreatedAt());
        checkListFlatDto.setChangedDateTime(checkList.getUpdatedAt());
        checkListFlatDto.setRemainderDate(checkList.getRemindAt());
        checkListFlatDto.setPinned(checkList.isPinned());
        checkListFlatDto.setPriority(checkList.getPriority());
        checkListFlatDto.setStatus(checkList.getStatus());
        checkListFlatDto.setType(checkList.getType());
        checkListFlatDto.setContent(checkList.getContent());

        return checkListFlatDto;
    }

    public CheckList mapDtoToEntity(ChecklistDto checkListFlatDto) {
        return new CheckList.CheckListBuilder()
                .setId(checkListFlatDto.getId())
                .setTitle(checkListFlatDto.getTitle())
                .setCreatedAt(checkListFlatDto.getCreatedDateTime())
                .setChangedAt(checkListFlatDto.getChangedDateTime())
                .setRemindAt(checkListFlatDto.getRemainderDate())
                .setPinned(checkListFlatDto.isPinned())
                .setPriority(checkListFlatDto.getPriority())
                .setStatus(checkListFlatDto.getStatus())
                .setType(checkListFlatDto.getType())
                .setContent(checkListFlatDto.getContent())
                .build();
    }
}
