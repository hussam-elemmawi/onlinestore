package io.work.onlinestore.util.exception;

import io.work.onlinestore.util.response.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.NestedServletException;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ConstraintViolationException.class)
    protected ResponseEntity<ApiResponse<String>> handleInvalidRequestPathParameter(RuntimeException ex, WebRequest request) {
        return new ResponseEntity(new ApiResponse<>(ex.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RecordNotFoundException.class)
    protected ResponseEntity<ApiResponse<String>> handleRecordNotFound(RuntimeException ex, WebRequest request) {
        return new ResponseEntity(new ApiResponse<>(ex.getMessage(), null), HttpStatus.NOT_FOUND);
    }
}
