package ru.yandex.kingartaved.util;

import ru.yandex.kingartaved.exception.constant.ErrorMessage;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

    // TODO: добавить в коде проверки if (logger.isLoggable(Level.FINE)).
public final class LoggerUtil {

    static {
        loadLogConfiguration();
    }
    private LoggerUtil() {
        throw new UnsupportedOperationException(ErrorMessage.UTILITY_CLASS.getMessage());
    }

    private static void loadLogConfiguration() {
        try (InputStream in = LoggerUtil.class.getClassLoader().getResourceAsStream("log.properties")) {
            if (in == null) {
                throw new FileNotFoundException("Log configuration file not found in classpath");
            }
            LogManager.getLogManager().readConfiguration(in);
        } catch (Exception e) {
            System.err.println("Failed to load log configuration: " + e.getMessage());
        }
    }

    public static Logger getLogger(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Class cannot be null");
        }
        return Logger.getLogger(clazz.getName());
    }
}