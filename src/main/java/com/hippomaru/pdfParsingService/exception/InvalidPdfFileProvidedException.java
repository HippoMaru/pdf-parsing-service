package com.hippomaru.pdfParsingService.exception;

public class InvalidPdfFileProvidedException extends RuntimeException{
    public InvalidPdfFileProvidedException(String message) {
        super(message);
    }

    public InvalidPdfFileProvidedException(String message, Throwable cause) {
        super(message, cause);
    }
}