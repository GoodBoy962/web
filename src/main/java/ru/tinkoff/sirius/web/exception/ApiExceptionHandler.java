package ru.tinkoff.sirius.web.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.tinkoff.sirius.web.model.ApiErrorResponse;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@ResponseBody
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = NullPointerException.class)
    protected ResponseEntity<Object> handleNpe(NullPointerException ex, WebRequest request) {
        logger.error("NPE happened");
        return handleExceptionInternal(ex, ApiErrorResponse.builder()
                .errorCode("USER NOT FOUND")
                .errorMessage("User doesn't exists").build(),
            new HttpHeaders(),
            HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {NonUniqueUserPhoneException.class})
    protected ResponseEntity<Object> handleError(NonUniqueUserPhoneException ex, WebRequest request) {
        logger.error("Exception is occurred", ex);
        return handleExceptionInternal(ex, ApiErrorResponse.builder()
            .errorCode("VALIDATION_ERROR")
            .errorMessage("Phone is already exists")
            .build(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatus status,
                                                                  @NonNull WebRequest request) {
        List<ApiErrorResponse> errors = new ArrayList<>(ex.getFieldErrors().size());
        for (FieldError error : ex.getFieldErrors()) {
            errors.add(ApiErrorResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST.name())
                .errorMessage(String.format("Поле %s - %s", error.getField(), error.getDefaultMessage()))
                .build());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(errors);
    }
}
