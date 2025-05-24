package ru.yandex.kingartaved.validation.db_line_validator;

import ru.yandex.kingartaved.config.AppConfig;
import ru.yandex.kingartaved.data.constant.NotePriorityEnum;
import ru.yandex.kingartaved.data.constant.NoteStatusEnum;
import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.util.LoggerUtil;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Валидатор строк из базы данных, проверяющий их соответствие формату заметок.
 * Осуществляет разбор строки по разделителю '|', проверку количества полей и их валидность.
 */
public class DbLineValidator {
    private static final Logger LOGGER = LoggerUtil.getLogger(DbLineValidator.class);
    private static final int EXPECTED_FIELDS_COUNT = 10;
    private static final String DB_FIELD_DELIMITER = AppConfig.DB_FIELD_DELIMITER;
    private final ContentValidatorRegistry contentValidatorRegistry;

    public DbLineValidator(ContentValidatorRegistry contentValidatorRegistry) {
        this.contentValidatorRegistry = contentValidatorRegistry;
    }


    /**
     * Проверяет строку из БД на соответствие формату заметки.
     * <p>
     * Формат строки (10 полей через '|'):
     * 0: UUID, 1: заголовок, 2: дата создания, 3: дата изменения,
     * 4: дата напоминания (или "null"), 5: закреплена (true/false),
     * 6: приоритет, 7: статус, 8: тип, 9: содержимое.
     * <p>
     * Процесс валидации:<p>
     * 1. Проверка строки на null/пустоту <p>
     * 2. Разделение по символу '|'<p>
     * 3. Проверка количества полей<p>
     * 4. Удаление пробелов вокруг значений<p>
     * 5. Проверка значений полей<p>
     *
     * @param lineFromDb Строка из БД в формате "id|title|createdAt|...|content".
     * @return true если строка валидна, false в противном случае (ошибки логируются).
     */
    public boolean validate(String lineFromDb) {

        String[] parts = new String[0];

        try {
            validateLineNotEmpty(lineFromDb);

            parts = lineFromDb.split(DB_FIELD_DELIMITER, -1);

            validateDbLineStructure(parts);
            validateNoNullPartsValues(parts, 4);

            requireUuid(parts, 0, "id");
            requireNotEmpty(parts, 1, "title");
            requireDate(parts, 2, "createdAt");
            requireDate(parts, 3, "modifiedAt");
            if (!"null".equalsIgnoreCase(parts[4])) {
                requireDate(parts, 4, "remindAt");
            }
            requireBoolean(parts, 5, "pinned");
            requireEnum(parts, 6, "priority", NotePriorityEnum.class);
            requireEnum(parts, 7, "status", NoteStatusEnum.class);
            requireEnum(parts, 8, "type", NoteTypeEnum.class);
            requireContent(parts, 8, 9, "type", "content");
            return true;
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, "Ошибка при валидации строки:\n {0}\n {1}", new Object[]{String.join("|", parts), e});
            return false;
        }
    }

    /**
     * Проверяет, что строка из БД не является null или пустой.
     *
     * @param lineFromDb строка для проверки
     * @throws IllegalArgumentException если строка null или пустая (blank)
     */
    private void validateLineNotEmpty(String lineFromDb) {
        if (lineFromDb == null || lineFromDb.isBlank()) {
            throw new IllegalArgumentException("Строка не может быть null или пустой");
        }
    }

    /**
     * Проверяет структурную целостность строки из БД:
     * <ul>
     *      <li>количество полей после разделения соответствует {@link #EXPECTED_FIELDS_COUNT};
     *      <li>отсутствие лишних пробелов вокруг разделителей (кроме исключений).
     * </ul>
     *
     * @throws IllegalArgumentException если структура строки нарушена.
     */
    void validateDbLineStructure(String[] parts, int... excludedIndexes) {// TODO: По окончании работ убрать excludedIndexes, если не понадобится.
        validatePartsCount(parts); //
        for (int i = 0; i < parts.length; i++) {
            if (contains(excludedIndexes, i)) continue;
            if (!parts[i].equals(parts[i].trim())) {
                throw new IllegalArgumentException(String.format("Обнаружены лишние пробелы в поле с индексом: %d, содержание поля: '%s'", i, parts[i]));
            }
        }
    }

    /**
     * Проверяет, что количество полей соответствует ожидаемому.
     *
     * @param parts массив полей для проверки
     * @throws IllegalArgumentException если parts.length != {@link #EXPECTED_FIELDS_COUNT}
     */
    void validatePartsCount(String[] parts) {
        if (parts.length != EXPECTED_FIELDS_COUNT) {
            throw new IllegalArgumentException("Неверное количество полей после split: ожидалось: " + EXPECTED_FIELDS_COUNT + ", пришло: " + parts.length);
        }
    }

    /**
     * Проверяет, что ни один из элементов массива (кроме исключённых индексов) не равен строке "null" (без учёта регистра).
     *
     * @param parts           Массив строк для проверки (не может быть null).
     * @param excludedIndexes Индексы элементов, которые следует игнорировать (например, 2, 4).
     * @return true если все проверяемые элементы не содержат "null".
     * @throws NullPointerException если массив равен null.
     * @see #contains(int[], int)
     */
    void validateNoNullPartsValues(String[] parts, int... excludedIndexes) {
        for (int i = 0; i < parts.length; i++) {
            if (contains(excludedIndexes, i)) continue; // TODO: Пропускаем исключённые индексы
            if (parts[i].equalsIgnoreCase("null")) {
                throw new IllegalArgumentException("Поле с индексом " + i + " содержит null");
            }
        }
    }

    /**
     * Проверяет наличие значения в массиве исключаемых из проверки целых чисел.
     *
     * @param excludedIndexes Массив целых чисел для проверки (например, [4] или [2, 4]).
     * @param inputIndex      Искомое значение (например, индекс поля i).
     * @return true, если значение найдено в массиве.
     */
    private boolean contains(int[] excludedIndexes, int inputIndex) {
        for (int item : excludedIndexes) {
            if (item == inputIndex) {
                return true;
            }
        }
        return false;
    }

    /**
     * Проверяет, что указанное поле содержит валидный UUID.
     */
    private void requireUuid(String[] parts, int index, String label) {
        try {
            UUID.fromString(parts[index]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Поле '" + label + "' (index " + index + ") содержит недопустимый UUID: " + parts[index], e);
        }
    }

    /**
     * Проверяет, что указанное поле содержит валидную дату.
     */
    private void requireDate(String[] parts, int index, String label) {
        try {
            LocalDateTime.parse(parts[index]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Поле '" + label + "' (index " + index + ") содержит недопустимую дату: '" + parts[index]+"'", e);
        }
    }

    /**
     * Проверяет, что указанное поле не пустое.
     */
    private void requireNotEmpty(String[] parts, int index, String label) {
        if (parts[index].isEmpty()) {
            throw new IllegalArgumentException("Поле '" + label + "' (index " + index + ") не может быть пустым");
        }
    }

    /**
     * Проверяет, что указанное поле содержит булево значение.
     */
     void requireBoolean(String[] parts, int index, String label) {
        if (!"true".equalsIgnoreCase(parts[index]) && !"false".equalsIgnoreCase(parts[index])) {
            throw new IllegalArgumentException("Поле '" + label + "' (index " + index + ") должно быть true/false, но пришло: " + parts[index]);
        }
    }

    /**
     * Проверяет, что указанное поле содержит допустимое значение перечисления.
     */
      <E extends Enum<E>> void requireEnum(String[] parts, int index, String label, Class<E> enumClass) {
        try {
            Enum.valueOf(enumClass, parts[index]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Поле '" + label + "' (index " + index + ") содержит недопустимое enum-значение (" + parts[index] + ") для перечисления " + enumClass.getSimpleName(), e);
        }
    }

    /**
     * Проверяет валидность содержимого заметки в зависимости от её типа.
     */
     void requireContent(String[] parts, int noteTypeIndex, int contentIndex, String noteTypeLabel, String contentLabel) {
        requireEnum(parts, noteTypeIndex, noteTypeLabel, NoteTypeEnum.class);

        NoteTypeEnum noteTypeEnum = Enum.valueOf(NoteTypeEnum.class, parts[noteTypeIndex]);

        ContentValidator contentValidator = contentValidatorRegistry.getValidator(noteTypeEnum);

        if (!contentValidator.isValidContent(parts[contentIndex])) {
            throw new IllegalArgumentException("Поле '" + contentLabel + "' (index " + contentIndex + ") содержит невалидную строку: \"" + parts[contentIndex] + "\"");
        }
    }
}