package com.segware.text.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PostDuplicateTextException extends RuntimeException {
    public PostDuplicateTextException(String message) {
        super(message);
    }
}
