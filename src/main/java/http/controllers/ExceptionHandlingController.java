package http.controllers;

import exceptions.AlreadyExistException;
import http.json.ErrorInfo;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.NoSuchElementException;
import java.util.Set;

@RestControllerAdvice
public class ExceptionHandlingController {

    private static final Set<String> MISSING_PARAMETERS_MAPPINGS = Set.of("/authors/", "/books/", "/readers/");

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorInfo handleNoSuchElementException(Exception exception) {
        return new ErrorInfo(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInfo handleMethodValidationException(HandlerMethodValidationException exception) {
        var error = exception.getAllErrors().getFirst();

        return new ErrorInfo(HttpStatus.BAD_REQUEST.value(), error.getDefaultMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInfo handleArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        var parameterName = exception.getName();

        return new ErrorInfo(HttpStatus.BAD_REQUEST.value(), "Invalid " + parameterName + " parameter");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInfo handleArgumentNotValidException(MethodArgumentNotValidException exception) {
        var error = exception.getAllErrors().getFirst();

        return new ErrorInfo(HttpStatus.BAD_REQUEST.value(), error.getDefaultMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ErrorInfo handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {
        var parameterName = exception.getParameterName();

        return new ErrorInfo(HttpStatus.BAD_REQUEST.value(), "Missing " + parameterName + " parameter");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorInfo> handleNoHandlerFoundException(NoHandlerFoundException exception) {
        var requestURL = exception.getRequestURL();

        ErrorInfo errorInfo;

        if (urlContainsMissingParameters(requestURL)) {
            errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST.value(), "Missing parameters");

            return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
        }

        errorInfo = new ErrorInfo(HttpStatus.NOT_FOUND.value(), exception.getMessage());

        return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
    }

    private boolean urlContainsMissingParameters(String url) {
        return MISSING_PARAMETERS_MAPPINGS.stream()
                .anyMatch(url::contains);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ErrorInfo handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        return new ErrorInfo(HttpStatus.METHOD_NOT_ALLOWED.value(), exception.getMessage());
    }

    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorInfo handleConstraintViolationException(AlreadyExistException exception) {
        return new ErrorInfo(HttpStatus.CONFLICT.value(), exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorInfo handleException() {
        return new ErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred while request processing");
    }
}
