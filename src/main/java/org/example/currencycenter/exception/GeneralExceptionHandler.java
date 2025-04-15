package org.example.currencycenter.exception;


import org.example.currencycenter.dto.ResponseMessage;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(InvalidPayload.class)
    public ResponseEntity<ResponseMessage> handleIncorrectPayloadException(InvalidPayload e){
        ResponseMessage message = new ResponseMessage(401, e.getMessage());
        return ResponseEntity.status(401).contentType(MediaType.APPLICATION_JSON).body(message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(errors);
    }


}
