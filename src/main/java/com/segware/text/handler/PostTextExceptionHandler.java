package com.segware.text.handler;

import com.segware.text.exception.PostDuplicateTextException;
import com.segware.text.exception.PostNotFoundException;
import com.segware.text.exception.UserNameExistsException;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@AllArgsConstructor
public class PostTextExceptionHandler extends ResponseEntityExceptionHandler {

    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<PostProblem.Field> fields = new ArrayList<>();

        for (ObjectError error: ex.getBindingResult().getAllErrors()) {
            String name = ((FieldError) error).getField();
            String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());

            fields.add(new PostProblem.Field(name, message));
        }

        PostProblem postProblem = new PostProblem();
        postProblem.setStatus(status.value());
        postProblem.setDateTime(LocalDateTime.now());
        postProblem.setTitle("Invalid field. Fill in correctly and try again");
        postProblem.setFields(fields);

        return handleExceptionInternal(ex, postProblem, headers, status, request);
    }

    @ExceptionHandler(PostDuplicateTextException.class)
        public ResponseEntity<Object> handlerPostDuplicateTextException(PostDuplicateTextException ex,
                                                                        WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;

        PostProblem postProblem = new PostProblem();
        postProblem.setStatus(status.value());
        postProblem.setDateTime(LocalDateTime.now());
        postProblem.setTitle(ex.getMessage());

        return handleExceptionInternal(ex, postProblem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(PostNotFoundException.class)
        public ResponseEntity<Object> handlerPostNotFoundException(PostNotFoundException ex,
                                                                   WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        PostProblem postProblem = new PostProblem();
        postProblem.setStatus(status.value());
        postProblem.setDateTime(LocalDateTime.now());
        postProblem.setTitle(ex.getMessage());

        return handleExceptionInternal(ex, postProblem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(UserNameExistsException.class)
        public ResponseEntity<Object> handlerUserNameExistsException(UserNameExistsException ex,
                                                                   WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;

        PostProblem postProblem = new PostProblem();
        postProblem.setStatus(status.value());
        postProblem.setDateTime(LocalDateTime.now());
        postProblem.setTitle(ex.getMessage());

        return handleExceptionInternal(ex, postProblem, new HttpHeaders(), status, request);
    }

}
