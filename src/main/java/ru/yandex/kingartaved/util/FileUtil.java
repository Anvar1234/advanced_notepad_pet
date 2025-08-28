package ru.yandex.kingartaved.util;

import ru.yandex.kingartaved.config.AppConfig;
import ru.yandex.kingartaved.exception.FileOperationException;
import ru.yandex.kingartaved.exception.constant.ErrorMessage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class FileUtil {

    private static final Logger LOGGER = LoggerUtil.getLogger(FileUtil.class);

    private FileUtil() {
        throw new UnsupportedOperationException(ErrorMessage.UTILITY_CLASS.getMessage());
    }

    public static void createFile(Path filePath) {

        try {
            // Проверяем, существует ли файл
            if (Files.exists(filePath)) {
                LOGGER.info(String.format("Файл \"%s\" уже существует.", filePath.getFileName()));
                return;
            }

            // Создаем файл
            Files.createFile(filePath);
            LOGGER.info(String.format("Файл \"%s\" создан.", filePath.getFileName()));
        } catch (IOException e) {
            String errorMessage = ErrorMessage.FILE_CREATION_ERROR.getMessage();
            String logMessage = errorMessage + ": " + filePath + "\nПричина: " + e.getMessage();
            LOGGER.log(Level.SEVERE, logMessage, e);
            throw new FileOperationException(errorMessage, e); //TODO: везде ниже выбрасывать именно это исключение, чтобы было понятно место возникновения .
        }
    }

    public static List<String> readAll(Path filePath) {
        try {
            List<String> lines = Files.readAllLines(filePath);
            LOGGER.info("Информация успешно прочитана из файла: " + filePath); // Логируем успех
            return lines;
        } catch (IOException e) {
            String errorMessage = ErrorMessage.FILE_READING_ERROR.getMessage();
            String logMessage = errorMessage + ": " + filePath + "\nПричина: " + e.getMessage(); // Логируем ошибку
            LOGGER.log(Level.SEVERE, logMessage, e);
            throw new FileOperationException(errorMessage, e);
        }
    }

    public static void saveAll(Path filePath, List<String> data) {
        try {
            Files.write(filePath, data); // Перезапись
            LOGGER.info("Данные записаны в файл: " + filePath);
        } catch (IOException e) {
            LOGGER.warning("Ошибка сохранения в файл: " + filePath + ". Error: " + e.getMessage());
        }
    }

    public static void appendAll(Path filePath, List<String> data) {
        try {
            Files.write(filePath, data, StandardOpenOption.APPEND); // Дозапись
            LOGGER.info("Data successfully appended to file: " + filePath);
        } catch (IOException e) {
            LOGGER.warning("Failed to append to file: " + filePath + ". Error: " + e.getMessage());
        }
    }


    public static void main(String[] args) { //TODO: удалить.
        Path path = Path.of(AppConfig.PATH_TO_DB_FILE);
        createFile(path);
        List<String> lines = FileUtil.readAll(path);
        for (String line : lines) {
            System.out.println(line);
        }
    }

}
