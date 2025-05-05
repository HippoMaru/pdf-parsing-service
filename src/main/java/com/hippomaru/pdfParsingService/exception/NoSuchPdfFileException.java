package com.hippomaru.pdfParsingService.exception;

public class NoSuchPdfFileException extends RuntimeException {
    public NoSuchPdfFileException(String message) {
        super(message);
    }

    public NoSuchPdfFileException(String message, Throwable cause) {
        super(message, cause);
    }
}