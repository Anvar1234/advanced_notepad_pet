package ru.yandex.kingartaved.data.mapper.impl;

import ru.yandex.kingartaved.data.mapper.NoteMapper;
import ru.yandex.kingartaved.data.model.CheckList;
import ru.yandex.kingartaved.dto.CheckListDto;

public class CheckListMapperImpl implements NoteMapper<CheckList, CheckListDto> {
    public CheckListDto mapEntityToDto(CheckList checkList) {
        CheckListDto checkListDto = new CheckListDto();
        checkListDto.setId(checkList.getId());
        checkListDto.setTitle(checkList.getTitle());
        checkListDto.setCreatedDateTime(checkList.getCreatedDateTime());
        checkListDto.setChangedDateTime(checkList.getChangedDateTime());
        checkListDto.setRemainderDate(checkList.getRemainderDate());
        checkListDto.setPinned(checkList.isPinned());
        checkListDto.setPriority(checkList.getPriority());
        checkListDto.setTags(checkList.getTags());
        checkListDto.setStatus(checkList.getStatus());
        checkListDto.setType(checkList.getType());
        checkListDto.setContent(checkList.getContent());

        return checkListDto;
    }

    public CheckList mapDtoToEntity(CheckListDto checkListDto) {
        CheckList checkList = new CheckList.CheckListBuilder()
                .setId(checkListDto.getId())
                .setTitle(checkListDto.getTitle())
                .setCreatedDateTime(checkListDto.getCreatedDateTime())
                .setChangedDateTime(checkListDto.getChangedDateTime())
                .setRemainderDate(checkListDto.getRemainderDate())
                .setPinned(checkListDto.isPinned())
                .setPriority(checkListDto.getPriority())
                .setTags(checkListDto.getTags())
                .setStatus(checkListDto.getStatus())
                .setType(checkListDto.getType())
                .setContent(checkListDto.getContent())
                .build();

        return checkList;
    }
}
