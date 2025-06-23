package ru.yandex.kingartaved.exception.constant;

public enum ErrorMessage {

    //TODO удалить енамы, которые используются по одному разу.

    CLASS_NOT_FOUND("Класс не найден"),

    PACKAGE_NOT_FOUND("Пакет не найден"),

    FILE_NOT_FOUND("Файл не найден"),

    FILE_CREATION_ERROR("Ошибка при создании файла"),

    FILE_READING_ERROR("Ошибка при чтении файла"),

    PATH_ERROR("Ошибка пути"),

    UTILITY_CLASS("Утилитный класс"),

    METADATA_VALIDATION_ERROR("Ошибка валидации метаданных"),

    CONTENT_VALIDATION_ERROR("Ошибка валидации контента"),

    DB_LINE_VALIDATION_ERROR("Ошибка при валидации строки БД"),

    FILE_CONNECTION_ERROR("Ошибка подключения к файлу"),

    FILE_OPERATION_ERROR("Ошибка при работе с файлом"),



    BAD_REQUEST("Неверный запрос"),

    NOT_FOUND("Страница не найдена"),

    CLIENT_NOT_FOUND("Клиент не найден"),

    ACCOUNT_TYPE_NOT_FOUND("Тип счета не найдена"),

    DOCUMENT_NOT_FOUND("Документ не найден"),

    STATUS_NOT_EXIST("Такой статус не существует"),

    KAFKA_SEND_ERROR("Ошибка отправки сообщения в брокер Kafka"),

    SERVER_EXCEPTION("Internal Server Error,  Try again later"),

    CLIENT_STATUS_EXCEPTION("Текущий статус для клиента не найден"),

    ADDRESS_NOT_FOUND("Адрес не найден"),

    ACCOUNT_NOT_FOUND("Аккаунт не найден"),

    MAXIMUM_FILE_UPLOAD_LIMIT_REACHED("Количество документов должно быть от 1 до 5"),

    MAX_FILE_UPLOAD_LIMIT_EXCEEDED("Больше 20и файлов загрузить нельзя"),

    UNSUPPORTED_FILE_TYPE("Тип файла должен быть pdf, jpg или png"),

    MAX_FILE_SIZE_EXCEEDED("Размер файла не должен превышать 16 мб"),

    INVALID_CURRENCY_CODE("Ошибка: значение поля CurrencyCode не входит в список доступных значений"),

    INVALID_ACCOUNT_TYPE("Ошибка: Тип счета не доступен"),

    DATABASE_CONSTRAINT_VIOLATION("Произошла ошибка при сохранении данных. Пожалуйста, проверьте введенные значения.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
