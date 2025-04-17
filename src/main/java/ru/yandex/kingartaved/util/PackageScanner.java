package ru.yandex.kingartaved.util;

import ru.yandex.kingartaved.exception.PackageScanningException;
import ru.yandex.kingartaved.exception.constant.ErrorMessage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public final class PackageScanner {

    private static final Logger logger = LoggerUtil.getLogger(PackageScanner.class);

    private PackageScanner() {
        throw new UnsupportedOperationException(ErrorMessage.UTILITY_CLASS.getMessage());
    }

    /**
     * Находит все классы в указанном пакете.
     *
     * @param packageName Имя пакета (например, "ru.yandex.kingartaved.data.model.client").
     * @return Список найденных классов.
     */
    public static List<Class<?>> findClasses(String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        try {
            // Получаем URL пакета
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            String path = packageName.replace('.', '/');
            System.out.println("path : " + path); //todo
            URL resource = classLoader.getResource(path);
            System.out.println("URL : " + resource); //todo

            System.out.println("WHAT " + System.getProperty("java.class.path")); //TODO

            if (resource == null) {
                String errorMessage = ErrorMessage.PACKAGE_NOT_FOUND.getMessage() + ": " + packageName; //TODO возможно заменить енам на просто строку.
                logger.warning(errorMessage);
                throw new IllegalArgumentException(errorMessage);
            }

            // Сканируем директорию
            File directory = new File(resource.getFile());
            if (!directory.exists() || !directory.isDirectory()) {
                String errorMessage = ErrorMessage.FILE_NOT_FOUND.getMessage() + ": " + directory; //TODO возможно заменить енам на просто строку.
                logger.warning(errorMessage);
                throw new IllegalArgumentException(errorMessage);
            }

            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".class")) {
                        // Убираем расширение .class и добавляем имя класса
                        String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                        Class<?> clazz = Class.forName(className);
                        classes.add(clazz);
                    }
                }
            }
        } catch (Exception e) {
            String errorMessage = "Ошибка при сканировании пакета: " + packageName;
            logger.severe(errorMessage);
            throw new PackageScanningException(errorMessage, e);
        }
        return Collections.unmodifiableList(classes); // Возвращаем неизменяемый список
    }
}