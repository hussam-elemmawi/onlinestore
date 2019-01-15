package io.work.onlinestore.util.exception;

import io.work.onlinestore.util.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ConstraintViolationException.class)
    protected ResponseEntity<ApiResponse<String>> handleInvalidRequestPathParameter(RuntimeException ex, WebRequest request) {
        return new ResponseEntity(new ApiResponse<>(ex.getMessage(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RecordNotFoundException.class)
    protected ResponseEntity<ApiResponse<String>> handleRecordNotFound(RuntimeException ex, WebRequest request) {
        return new ResponseEntity(new ApiResponse<>(ex.getMessage(), ex.getMessage()), HttpStatus.NOT_FOUND);
    }
}
