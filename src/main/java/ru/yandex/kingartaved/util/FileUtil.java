package ru.yandex.kingartaved.util;

import ru.yandex.kingartaved.config.AppConfig;
import ru.yandex.kingartaved.exception.constant.ErrorMessage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public final class FileUtil {

    private static final Logger logger = LoggerUtil.getLogger(FileUtil.class);

    private FileUtil() {
        throw new UnsupportedOperationException(ErrorMessage.UTILITY_CLASS.getMessage());
    }

    public static void createFile(Path filePath) {

        try {
            // Проверяем, существует ли файл
            if (Files.exists(filePath)) {
                logger.info(String.format("File \"%s\" already exist.", filePath.getFileName()));
                return;
            }

            // Создаем файл
            Files.createFile(filePath);
            logger.info(String.format("File \"%s\" created.", filePath.getFileName()));
        } catch (IOException e) {
            logger.warning("Failed to create file: " + filePath + ". Error: " + e.getMessage());
        }
    }

    public static List<String> readAll(Path filePath) {
        try {
            List<String> lines = Files.readAllLines(filePath);
            logger.info("Data successfully read from file: " + filePath); // Логируем успех
            return lines;
        } catch (IOException e) {
            logger.warning("Failed to read file: " + filePath + ". Error: " + e.getMessage()); // Логируем ошибку
            return Collections.emptyList();
        }
    }

    public static void saveAll(Path filePath, List<String> data) {
        try {
            Files.write(filePath, data); // Перезапись
            logger.info("Data successfully written to file: " + filePath);
        } catch (IOException e) {
            logger.warning("Failed to write to file: " + filePath + ". Error: " + e.getMessage());
        }
    }

    public static void appendAll(Path filePath, List<String> data) {
        try {
            Files.write(filePath, data, StandardOpenOption.APPEND); // Дозапись
            logger.info("Data successfully appended to file: " + filePath);
        } catch (IOException e) {
            logger.warning("Failed to append to file: " + filePath + ". Error: " + e.getMessage());
        }
    }


    public static void main(String[] args) { //TODO: удалить.
        Path path = Path.of(AppConfig.TEXT_NOTE_DB_TXT_FILE_PATH);
        createFile(path);
        List<String> lines = FileUtil.readAll(path);
        for (String line : lines) {
            System.out.println(line);
        }
    }

}
