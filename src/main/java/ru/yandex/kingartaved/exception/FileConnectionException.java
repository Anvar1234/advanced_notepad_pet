package ru.yandex.kingartaved.exception;

public class FileConnectionException extends RuntimeException {
    public FileConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
