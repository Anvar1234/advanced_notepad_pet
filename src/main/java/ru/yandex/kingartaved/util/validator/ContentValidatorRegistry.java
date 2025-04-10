package ru.yandex.kingartaved.util.validator;

import ru.yandex.kingartaved.data.constant.NoteTypeEnum;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;

public class ContentValidatorRegistry {
    private final Map<NoteTypeEnum, ContentValidator> validators = new HashMap<>();

    public ContentValidatorRegistry() {
        ServiceLoader<ContentValidator> loader = ServiceLoader.load(ContentValidator.class);
        for (ContentValidator validator : loader) {
            validators.put(validator.getSupportedNoteType(), validator);
        }
    }

    public Optional<ContentValidator> getValidator(NoteTypeEnum type) {
        return Optional.ofNullable(validators.get(type));
    }
}
