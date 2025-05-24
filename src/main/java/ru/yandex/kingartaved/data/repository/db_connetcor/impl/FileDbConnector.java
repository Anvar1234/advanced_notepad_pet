package ru.yandex.kingartaved.data.repository.db_connetcor.impl;

import ru.yandex.kingartaved.config.AppConfig;
import ru.yandex.kingartaved.data.repository.db_connetcor.DbConnector;
import ru.yandex.kingartaved.util.FileUtil;

import java.nio.file.Path;

public enum FileDbConnector implements DbConnector {

    INSTANCE;

    @Override
    public void connectToDb() {
        Path path = Path.of(AppConfig.PATH_TO_DB_FILE);
        FileUtil.createFile(path);
    }
}
