package com.joosangah.paymentservice.common.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joosangah.paymentservice.common.domain.ExceptionResponse;
import feign.FeignException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.apache.http.impl.execchain.RequestAbortedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper;

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        List<String> errorMessages = new ArrayList<>();

        for (FieldError fieldError : fieldErrors) {
            String errorMessage = fieldError.getField() + ": " + fieldError.getDefaultMessage();
            errorMessages.add(errorMessage);
        }

        ExceptionResponse response = new ExceptionResponse(errorMessages.toString(),
                status);

        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(RequestAbortedException.class)
    public ResponseEntity<Object> handleRequestAbortedException(RequestAbortedException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ExceptionResponse response = new ExceptionResponse(e.getMessage(),
                status);
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleException(AccessDeniedException e) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        ExceptionResponse response = new ExceptionResponse(
                e.getMessage().isBlank() ? "Access denied" : e.getMessage(),
                status);
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleException(NoSuchElementException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ExceptionResponse response = new ExceptionResponse(
                e.getMessage().isBlank() ? "No such element" : e.getMessage(),
                status);
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ExceptionResponse response = new ExceptionResponse(e.getMessage(),
                status);
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity feignExceptionHandler(FeignException e) throws JsonProcessingException {
        String responseJson = e.contentUTF8();
        Map<String, String> responseMap = objectMapper.readValue(responseJson, Map.class);

        return new ResponseEntity<>(responseMap, HttpStatus.valueOf(e.status()));
    }
}
