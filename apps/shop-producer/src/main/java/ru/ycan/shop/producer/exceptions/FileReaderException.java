package ru.ycan.shop.producer.exceptions;

public class FileReaderException extends RuntimeException {
    public FileReaderException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
