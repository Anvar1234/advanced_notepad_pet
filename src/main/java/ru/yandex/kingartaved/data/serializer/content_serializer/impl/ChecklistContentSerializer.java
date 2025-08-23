package ru.yandex.kingartaved.data.serializer.content_serializer.impl;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.data.model.ChecklistTask;
import ru.yandex.kingartaved.data.model.Content;
import ru.yandex.kingartaved.data.model.ChecklistContent;
import ru.yandex.kingartaved.data.serializer.content_serializer.ContentSerializer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ChecklistContentSerializer implements ContentSerializer {
    @Override
    public NoteTypeEnum getSupportedType() {
        return NoteTypeEnum.CHECKLIST;
    }

    @Override
    public String serializeContent(Content content) {
        if(content.getSupportedType() != getSupportedType()){
            String errorMessage = String.format("Неподдерживаемый тип контента: %s для сериалайзера заметок типа %s", content.getClass(), getSupportedType());
            throw new IllegalArgumentException(errorMessage);
        }
        return  ((ChecklistContent) content).tasks().stream() //List<ChecklistTask>
                .map(checklistItem ->
                        String.format("%s:%s", checklistItem.getText(), checklistItem.isDone()))
                .collect(Collectors.joining(";"));
    }

    @Override
    public Content deserializeContent(String[] parts) {
        List<ChecklistTask> tasks = Arrays.stream(parts)
                .map(itemPart -> itemPart.split(":"))
                .map(items -> new ChecklistTask(items[0], Boolean.parseBoolean(items[1])))
                .toList();
        return new ChecklistContent(tasks);
    }
}
