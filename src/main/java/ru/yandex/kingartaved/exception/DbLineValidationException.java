package ru.yandex.kingartaved.exception;

public class DbLineValidationException extends RuntimeException {
    public DbLineValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
