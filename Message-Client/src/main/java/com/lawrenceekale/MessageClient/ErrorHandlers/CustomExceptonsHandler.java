package com.lawrenceekale.MessageClient.ErrorHandlers;

import com.lawrenceekale.MessageClient.model.MessageSendErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomExceptonsHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageSendErrors> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        List<String> errorMessages = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        //create the response
        MessageSendErrors errorResponse = MessageSendErrors.builder()
                .errors(errorMessages)
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .build();

        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
