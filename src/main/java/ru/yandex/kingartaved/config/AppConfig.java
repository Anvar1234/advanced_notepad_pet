package ru.yandex.kingartaved.config;

public class AppConfig {
    // Путь к пакету для формирования маппера
    public static final String MAPPERS_PACKAGE = "ru.yandex.kingartaved.data.mapper.client_mapper_impl";
    public static final String ENTITY_CLASSES_PACKAGE = "ru.yandex.kingartaved.data.model.client.client_impl";
    public static final String MAPPER_SUFFIX = "Mapper";

    // Пути к файлам базы данных
    public static final String TEXT_NOTE_DB_TXT_FILE_PATH = "src/main/resources/text_note_db.txt";
    public static final String CHECK_LIST_DB_TXT_FILE_PATH = "src/main/resources/check_list_db.txt";
    public static final String DELIMITER = "|";

    public static final String USER_DB_JSON_FILE = "users.json";
    public static final String ACCOUNT_DB_JSON_FILE = "accounts.json";
    public static final String TRANSACTION_DB_JSON_FILE = "transactions.json";

    // Лимиты и константы
    public static final double MIN_BALANCE = 0.0; //todo перенести это и макс сумму переводов в сервис
    public static final double MAX_PER_DAY_WITHDRAW_AMOUNT = 100000.0; // максимальная сумма переводов в сутки.

    // Другие настройки
    public static final int MAX_LOGIN_ATTEMPTS = 3;
}
