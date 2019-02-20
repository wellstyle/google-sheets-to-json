package com.joonee.googlesheetstojson.web.exception;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.HttpStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class RestControllerExceptionHandler {
    /**
     * 400 Bad Request
     * This exception is thrown when argument annotated with @Valid @RequestParam or @ModelAttributes failed validation:
     */
    @ExceptionHandler(value = {BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleBindException(BindException ex) {
        return new ApiErrorResponse("invalid_request_parameter", getErrors(ex.getBindingResult()));
    }

    /**
     * 400 Bad Request
     * This exception is thrown when argument annotated with @Valid @RequestParam or @ModelAttributes failed validation:
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleConstraintViolationException(ConstraintViolationException ex) {
        return new ApiErrorResponse("invalid_request_parameter", getErrors(ex.getConstraintViolations()));
    }

    /**
     * 400 Bad Request
     * This exception is thrown when @RequestParam missing
     */
    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        return new ApiErrorResponse("invalid_request_parameter", ex.getParameterName() + ": " + ex.getMessage());
    }

    /**
     * 400 Bad Request
     * This exception is thrown when argument annotated with @Valid @RequestBody failed validation:
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return new ApiErrorResponse("invalid_request_body", getErrors(ex.getBindingResult()));
    }

    /**
     * 400 Bad Request
     * This exception is thrown when json parsing failed:
     */
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleNotReadableException() {
        return new ApiErrorResponse("invalid_request_body", "Invalid JSON payload received.");
    }

    /**
     * 400 Bad Request
     * This exception is thrown when ...:
     */
    @ExceptionHandler(value ={MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return new ApiErrorResponse("type_mismatch_request_parameter_or_body", ex.getMessage());
    }

    /**
     * 405 Method not allowed
     */
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ApiErrorResponse handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return new ApiErrorResponse("not_allowed_method", ex.getMessage());
    }

    /**
     * 400 Bad Request
     * This exception is thrown when
     */
    @ExceptionHandler(value = {IndexOutOfBoundsException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleIndexOutOfBoundsException() {
        return new ApiErrorResponse("not_found_sheet", "Make sure you entered the sheet number correctly");
    }

    /**
     * jsoup HttpStatusException
     */
    @ExceptionHandler(value = {HttpStatusException.class})
    public ResponseEntity<ApiErrorResponse> handleHttpStatusException(HttpStatusException ex) {
        return new ResponseEntity<>(new ApiErrorResponse("failed_to_fetch_data", ex.getMessage()),
            HttpStatus.valueOf(ex.getStatusCode()));
    }

    /**
     * jsoup SocketTimeoutException
     */
    @ExceptionHandler(value = {SocketTimeoutException.class})
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    public ApiErrorResponse handleSocketTimeoutException(SocketTimeoutException ex) {
        return new ApiErrorResponse("request_timeout", ex.getMessage());
    }

    /**
     * jsoup IOException
     */
    @ExceptionHandler(value = {IOException.class})
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    public ApiErrorResponse handleIOException(IOException ex) {
        return new ApiErrorResponse("io_exception", ex.getMessage());
    }

    /**
     * 500 Internal server error
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleInternalServerError(Exception ex) {
        log.error("### handleInternalServerError {}", ex);
        return new ApiErrorResponse(ex.getClass().getSimpleName(), ex.getMessage());
    }

    private String getErrors(BindingResult bindingResult) {

        List<String> errors = bindingResult
            .getFieldErrors()
            .stream()
            .map(f -> f.getField() + ": " + f.getDefaultMessage())
            .collect(Collectors.toList());
        errors.addAll(bindingResult
            .getGlobalErrors()
            .stream()
            .map(o -> o.getObjectName() + ": " + o.getDefaultMessage())
            .collect(Collectors.toList()));
        return errors.toString();
    }

    private String getErrors(Set<ConstraintViolation<?>> constraintViolations) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : constraintViolations) {
            errors.add(violation.getPropertyPath() + ": " + violation.getMessage());
        }
        return errors.toString();
    }


}
