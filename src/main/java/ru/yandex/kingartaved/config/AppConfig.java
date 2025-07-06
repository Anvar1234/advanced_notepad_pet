package ru.yandex.kingartaved.config;

import ru.yandex.kingartaved.data.constant.DbFileFormatEnum;

import java.util.HashMap;
import java.util.Map;

public final class AppConfig { //TODO: сделать неизменяемым (конструктор)
    // Путь к пакету для формирования маппера
    public static final String MAPPERS_PACKAGE = "ru.yandex.kingartaved.data.mapper.content_mapper.impl";
    public static final String ENTITY_CLASSES_PACKAGE = "ru.yandex.kingartaved.data.model";
    public static final String MAPPER_SUFFIX = "Mapper";

    // Пути к файлам базы данных и сопутствующие константы
    public static final String DB_FILE_FORMAT = DbFileFormatEnum.TXT.getFormat();
    public static final String PATH_TO_DB_FILE = DbFileFormatEnum.TXT.getStringPath();
    public static final String DB_FIELD_DELIMITER = "\\|";

    // Требования к контенту
    public static final int MIN_TEXT_LENGTH = 1;
    public static final int MAX_TEXT_LENGTH = 30;

    // Требования к заголовку (title)
    public static final int MAX_TITLE_LENGTH = 10;



    public static final String USER_DB_JSON_FILE = "users.json";
    public static final String ACCOUNT_DB_JSON_FILE = "accounts.json";
    public static final String TRANSACTION_DB_JSON_FILE = "transactions.json";

    // Лимиты и константы
    public static final double MIN_BALANCE = 0.0; //todo перенести это и макс сумму переводов в сервис
    public static final double MAX_PER_DAY_WITHDRAW_AMOUNT = 100000.0; // максимальная сумма переводов в сутки.

    // Другие настройки
    public static final int MAX_LOGIN_ATTEMPTS = 3;
}
