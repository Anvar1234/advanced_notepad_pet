package ru.yandex.kingartaved.util.validator;

import ru.yandex.kingartaved.data.constant.NotePriorityEnum;
import ru.yandex.kingartaved.data.constant.NoteStatusEnum;
import ru.yandex.kingartaved.data.constant.NoteTypeEnum;
import ru.yandex.kingartaved.util.LoggerUtil;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbLineValidator {
    private static final Logger LOGGER = LoggerUtil.log(DbLineValidator.class.getName());
    private static final int EXPECTED_FIELDS_COUNT = 10;
    private final ContentValidatorRegistry contentValidatorRegistry;

    public DbLineValidator(ContentValidatorRegistry registry) {
        this.contentValidatorRegistry = registry;
    }

    /**
     * Проверяет строку из БД на соответствие формату заметки.
     * Делит строку по символу '|', проверяет количество и валидность полей.
     * <p>
     * Формат строки (10 полей через '|'):
     * 0: UUID, 1: заголовок, 2-3: даты, 4: дата напоминания (или "null"),
     * 5: закреплена (true/false), 6-8: приоритет, статус, тип, 9: содержимое.
     * <p>
     * Автоматически удаляет пробелы вокруг значений перед проверкой.
     *
     * @param lineFromDb Строка из БД в формате "id|title|createdAt|...|content".
     * @return false если строка невалидна (ошибки логируются).
     */
    public boolean validateParts(String lineFromDb) {
        String[] parts = new String[0];

        try {
            //проверяем строку на null и пустоту
            validateLineFromDb(lineFromDb);
            parts = lineFromDb.split("\\|", -1);
            //проверяем кол-во элементов
            validatePartsCount(parts);
            //вычищаем от лишних пробелов
            cleanInputParts(parts);
            //проверяем, что все элементы не "null", кроме nullable элементов
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
            assert parts != null;
            LOGGER.log(Level.SEVERE, "Ошибка при валидации строки:\n {0}\n {1}", new Object[]{String.join("|", parts), e});
            return false;
        }
    }

    private static void validateLineFromDb(String lineFromDb) { //TODO: доделать метод
        if (lineFromDb == null || lineFromDb.isBlank()) {
            throw new IllegalArgumentException("Строка не может быть null или пустой");
        }
    }

    private static void validatePartsCount(String[] parts) {
        if (parts.length != EXPECTED_FIELDS_COUNT) {
            throw new IllegalArgumentException("Неверное количество полей после split: ожидалось: " + EXPECTED_FIELDS_COUNT + ", пришло: " + parts.length);
        }
    }

    /**
     * Модифицирует исходный массив, удаляя ведущие и завершающие пробелы у всех элементов массива.
     *
     * @param parts Массив строк для очистки (не может быть null).
     * @throws NullPointerException если массив или любой его элемент равен null.
     */
    private static void cleanInputParts(String[] parts) {
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
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
    private static void validateNoNullPartsValues(String[] parts, int... excludedIndexes) {
        for (int i = 0; i < parts.length; i++) {
            if (contains(excludedIndexes, i)) continue; // TODO: Пропускаем исключённые индексы
            if (parts[i].equalsIgnoreCase("null")) {
                throw new IllegalArgumentException("Поле с индексом " + i + " содержит null");
            }
        }
    }

    /**
     * Проверяет, содержится ли значение в массиве исключаемых из проверки целых чисел.
     *
     * @param excludedIndexes Массив целых чисел для проверки (например, [4] или [2, 4]).
     * @param inputIndex      Искомое значение (например, индекс поля i).
     * @return true, если значение найдено в массиве.
     */
    private static boolean contains(int[] excludedIndexes, int inputIndex) {
        for (int item : excludedIndexes) {
            if (item == inputIndex) {
                return true;
            }
        }
        return false;
    }

    private static void requireUuid(String[] parts, int index, String label) {
        try {
            UUID.fromString(parts[index]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Поле '" + label + "' (index " + index + ") содержит недопустимый UUID: " + parts[index], e);
        }
    }

    private static void requireDate(String[] parts, int index, String label) {
        try {
            LocalDateTime.parse(parts[index]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Поле '" + label + "' (index " + index + ") содержит недопустимую дату: " + parts[index], e);
        }
    }

    private static void requireNotEmpty(String[] parts, int index, String label) {
        if (parts[index].isEmpty()) {
            throw new IllegalArgumentException("Поле '" + label + "' (index " + index + ") не может быть пустым");
        }
    }

    private static void requireBoolean(String[] parts, int index, String label) {
        if (!"true".equalsIgnoreCase(parts[index]) && !"false".equalsIgnoreCase(parts[index])) {
            throw new IllegalArgumentException("Поле '" + label + "' (index " + index + ") должно быть true/false, но пришло: " + parts[index]);
        }
    }

    private static <E extends Enum<E>> void requireEnum(String[] parts, int index, String label, Class<E> enumClass) {
        try {
            Enum.valueOf(enumClass, parts[index]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Поле '" + label + "' (index " + index + ") содержит недопустимое enum-значение: " + parts[index], e);
        }
    }

    private void requireContent(String[] parts, int noteTypeIndex, int contentIndex, String noteTypeLabel, String contentLabel) {
        requireEnum(parts, noteTypeIndex, noteTypeLabel, NoteTypeEnum.class);

        NoteTypeEnum noteTypeEnum = Enum.valueOf(NoteTypeEnum.class, parts[noteTypeIndex]);

        ContentValidator contentValidator = contentValidatorRegistry
                .getValidator(noteTypeEnum)
                .orElseThrow(() -> new IllegalArgumentException("Нет валидатора для заметок типа: " + noteTypeEnum));

        if (!contentValidator.isValidContent(parts[contentIndex])) {
            throw new IllegalArgumentException("Поле '" + contentLabel + "' (index " + contentIndex + ") содержит невалидную строку: \"" + parts[contentIndex] + "\"");
        }
    }

    public static void main(String[] args) {
        DbLineValidator dbLineValidator = new DbLineValidator(new ContentValidatorRegistry());
        //remindDate == null, content equals "null"
        String vvod1 = "f47ac10b-58cc-4372-a567-0e02b2c3d479|Заметка 1|2023-10-01T12:34:56|2023-10-01T15:34:56|null|true|HIGH|ACTIVE|TEXT_NOTE|null";
        System.out.println(dbLineValidator.validateParts(vvod1));
        //лишние пробелы
        String vvod2 = "a1b2c3d4-e5f6-7890-abcd-ef1234567890|Заметка 3|2023-10-10T18:45:22|2023-10-10T20:45:22|2023-10-15T18:45:22|true|HIGH |POSTPONED|TEXT_NOTE|222Текстовая заметка с отложенным статусом";
        System.out.println(dbLineValidator.validateParts(vvod2));
        //пустое значение title (isEmpty)
        String vvod3 = "a1b2c3d4-e5f6-7890-abcd-ef1234567890|     |2023-10-10T18:45:22|2023-10-10T20:45:22|2023-10-15T18:45:22|true|HIGH|POSTPONED|TEXT_NOTE|333Текстовая заметка с отложенным статусом";
        System.out.println(dbLineValidator.validateParts(vvod3));
        //content is empty
        String vvod4 = "f47ac10b-58cc-4372-a567-0e02b2c3d479|Заметка 1|2023-10-01T12:34:56|2023-10-01T15:34:56|null|true|HIGH|ACTIVE|TEXT_NOTE|";
        System.out.println(dbLineValidator.validateParts(vvod4));
        //количество полей меньше ожидаемого (убрал контент)
        String vvod5 = "f47ac10b-58cc-4372-a567-0e02b2c3d479|Заметка 1|2023-10-01T12:34:56|2023-10-01T15:34:56|null|true|HIGH|ACTIVE|TEXT_NOTE";
        System.out.println(dbLineValidator.validateParts(vvod5));
    }
}