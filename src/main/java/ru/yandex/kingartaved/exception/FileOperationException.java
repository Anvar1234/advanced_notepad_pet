package ru.yandex.kingartaved.exception;

public class FileOperationException extends RuntimeException {
    public FileOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}