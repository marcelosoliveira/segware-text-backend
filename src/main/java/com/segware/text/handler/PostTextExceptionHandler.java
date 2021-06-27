package com.segware.text.handler;

import com.segware.text.exception.PostDuplicateTextException;
import com.segware.text.exception.PostNotFoundException;
import com.segware.text.exception.UserNameExistsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class PostTextExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        PostProblem postProblem = new PostProblem();
        postProblem.setStatus(status.value());
        postProblem.setDateTime(LocalDateTime.now());
        postProblem.setTitle("Invalid field. Fill in correctly and try again");
        postProblem.setMessage(ex.getFieldError().getDefaultMessage());

        return handleExceptionInternal(ex, postProblem, headers, status, request);
    }

    @ExceptionHandler(PostDuplicateTextException.class)
        public ResponseEntity<Object> handlerPostDuplicateTextException(PostDuplicateTextException ex,
                                                                        WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;

        PostProblem postProblem = new PostProblem();
        postProblem.setStatus(status.value());
        postProblem.setDateTime(LocalDateTime.now());
        postProblem.setTitle("Invalid field. Fill in correctly and try again");
        postProblem.setMessage(ex.getMessage());

        return handleExceptionInternal(ex, postProblem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(PostNotFoundException.class)
        public ResponseEntity<Object> handlerPostNotFoundException(PostNotFoundException ex,
                                                                   WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        PostProblem postProblem = new PostProblem();
        postProblem.setStatus(status.value());
        postProblem.setDateTime(LocalDateTime.now());
        postProblem.setTitle("Invalid field. Fill in correctly and try again");
        postProblem.setMessage(ex.getMessage());

        return handleExceptionInternal(ex, postProblem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(UserNameExistsException.class)
        public ResponseEntity<Object> handlerUserNameExistsException(UserNameExistsException ex,
                                                                   WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;

        PostProblem postProblem = new PostProblem();
        postProblem.setStatus(status.value());
        postProblem.setDateTime(LocalDateTime.now());
        postProblem.setTitle("Invalid field. Fill in correctly and try again");
        postProblem.setMessage(ex.getMessage());

        return handleExceptionInternal(ex, postProblem, new HttpHeaders(), status, request);
    }

}
