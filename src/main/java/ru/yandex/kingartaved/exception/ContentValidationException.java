package ru.yandex.kingartaved.exception;

public class ContentValidationException extends Exception {
    public ContentValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}