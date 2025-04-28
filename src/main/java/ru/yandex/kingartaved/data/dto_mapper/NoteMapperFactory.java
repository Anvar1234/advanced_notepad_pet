package ru.yandex.kingartaved.data.dto_mapper;

import ru.yandex.kingartaved.config.AppConfig;
import ru.yandex.kingartaved.exception.MapperRegistrationException;
import ru.yandex.kingartaved.util.LoggerUtil;
import ru.yandex.kingartaved.util.PackageScanner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static ru.yandex.kingartaved.util.ClassGetter.getClassByNameFromList;

public class NoteMapperFactory {
    private static final Logger logger = LoggerUtil.getLogger(NoteMapperFactory.class);
    /**
     * Мапа для хранения соответствий между классами сущностей и их мапперами.
     */
    private static final Map<Class<? extends AbstractNote>, NoteMapper<?, ?>> mappers = new HashMap<>();

    /**
     * Статический блок для инициализации и регистрации мапперов при загрузке класса.
     */
    static {
        registerMappers();
    }

    /**
     * Автоматически регистрирует мапперы для всех классов клиентов и мапперов.
     */
    private static void registerMappers() {
        try {
            // Находим все классы клиентов.
            List<Class<?>> noteClasses = PackageScanner.findClasses(AppConfig.ENTITY_CLASSES_PACKAGE);
            // Находим все классы мапперов.
            List<Class<?>> mapperClasses = PackageScanner.findClasses(AppConfig.MAPPERS_PACKAGE);

            // Извлекаем простые имена доменных классов и проверяем принадлежность к классу AbstractNote.
            Set<String> noteClassNames = noteClasses.stream()
                    .filter(AbstractNote.class::isAssignableFrom)
                    .map(Class::getSimpleName)
                    .collect(Collectors.toSet());

            // Регистрируем мапперы в соответствии с именем доменного класса клиента.
            for (Class<?> mapperClass : mapperClasses) {
                if (isValidMapperClass(mapperClass)) {
                    // Получаем имя доменного класса на основе маппера.
                    String entityClassName = getEntityClassName(mapperClass);

                    // Проверяем, существует ли доменный класс с таким именем.
                    if (noteClassNames.contains(entityClassName)) {
                        // Создаем экземпляр маппера и добавляем его в коллекцию мапперов.
                        NoteMapper<?, ?> mapper = (NoteMapper<?, ?>) mapperClass.getDeclaredConstructor().newInstance();
                        Class<?> clazz = getClassByNameFromList(entityClassName, noteClasses);
                        if (AbstractNote.class.isAssignableFrom(clazz)) {
                            mappers.put((Class<? extends AbstractNote>) clazz, mapper);
                            logger.info("Добавлен маппер: " + entityClassName + " -> " + mapperClass);
                        } else {
                            logger.warning("Class " + entityClassName + " is not a subclass of AbstractNote");
                        }
                    }
                }
            }
        } catch (Exception e) {
            String errorMessage = "Ошибка при регистрации мапперов";
            logger.log(Level.SEVERE, errorMessage, e);
            throw new MapperRegistrationException(errorMessage);
        }
    }

    /**
     * Проверяет, является ли указанный класс допустимым маппером.
     *
     * @param clazz Класс для проверки маппер ли он и допустимый ли.
     * @return true, если класс является маппером; false в противном случае.
     */
    private static boolean isValidMapperClass(Class<?> clazz) { //TODO: возможно перенести в утильную валидацию.
        return NoteMapper.class.isAssignableFrom(clazz) && clazz.getSimpleName().endsWith(AppConfig.MAPPER_SUFFIX);
    }

    /**
     * Получает имя доменного класса на основе имени класса маппера.
     *
     * @param mapperClass Класс маппера.
     * @return String имя доменного класса.
     */
    private static String getEntityClassName(Class<?> mapperClass) {
        return mapperClass.getSimpleName().replace(AppConfig.MAPPER_SUFFIX, "");
    }

    /**
     * Возвращает маппер для указанного класса клиента.
     *
     * @param noteClass Класс клиента.
     * @return Маппер для указанного класса клиента.
     * @throws IllegalArgumentException если маппер не найден.
     */
    @SuppressWarnings("unchecked")
    public <T extends AbstractNote, R extends AbstractNoteDto> NoteMapper<T, R> getMapper(Class<T> noteClass) {
        if (noteClass == null) {
            String errorMessage = "Note class cannot be null";
            logger.warning(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        NoteMapper<?, ?> mapper = mappers.get(noteClass);
        if (mapper == null) {
            String errorMessage = String.format("Маппер для класса %s не найден", noteClass.getSimpleName());
            logger.warning(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        return (NoteMapper<T, R>) mapper;
    }

    @Override
    public String toString() { //TODO удалить
        return "NoteMapperFactory{" +
                mappers +
                '}';
    }

}
