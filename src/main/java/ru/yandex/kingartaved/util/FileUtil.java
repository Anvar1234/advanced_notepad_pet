package ru.yandex.kingartaved.util;

import ru.yandex.kingartaved.config.AppConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;

public final class FileUtil {

    private FileUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static void createFile(Path filePath) {

        try {
            // Проверяем, существует ли файл
            if (Files.exists(filePath)) {
                return; // Файл уже существует, ничего не делаем
            }

            // Создаем файл
            Files.createFile(filePath);
        } catch (IOException e) {
            // Обработка исключения (можно выбросить RuntimeException или обработать иначе)
            throw new RuntimeException("Failed to create file: " + e.getMessage(), e);
        }
    }

    public static List<String> readAll(Path filePath) {
        try {
            return Files.readAllLines(filePath);
        } catch (IOException e) {
            System.err.println("Failed to read file: " + filePath);
            return Collections.emptyList();
        }
    }

    public static void saveAll(Path filePath, List<String> data) {
        try {
            Files.write(filePath, data); //перезапись
        } catch (IOException e) {
            System.err.println("Failed to write to file: " + filePath);
        }
    }

    public static void appendAll(Path filePath, List<String> data) {
        try {
            Files.write(filePath, data, StandardOpenOption.APPEND); //дозапись
        } catch (IOException e) {
            System.err.println("Failed to append to file: " + filePath);
        }
    }


    public static void main(String[] args) {
        Path path = Path.of(AppConfig.TEXT_NOTE_DB_TXT_FILE_PATH);
        createFile(path);
        List<String> lines = FileUtil.readAll(path);
        for (String line : lines) {
            System.out.println(line);
        }
    }

}
