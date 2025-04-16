package org.example.currencycenter.exception;


import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.example.currencycenter.dto.ResponseMessage;
import org.example.currencycenter.model.Currency;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(InvalidPayload.class)
    public ResponseEntity<ResponseMessage> handleIncorrectPayloadException(InvalidPayload e){
        ResponseMessage message = new ResponseMessage(401, e.getMessage());
        return ResponseEntity.status(401).contentType(MediaType.APPLICATION_JSON).body(message);
    }
    @ExceptionHandler(CurrencyNotFoundException.class)
    public ResponseEntity<ResponseMessage> handleCurrencyNotFoundException(CurrencyNotFoundException ex){
        ResponseMessage message = new ResponseMessage(401, ex.getMessage());
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
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex){
        ResponseMessage message;
        if(ex.getCause() instanceof InvalidFormatException){
            InvalidFormatException ife = (InvalidFormatException) ex.getCause();

            if (ife.getTargetType().isEnum()) {
                message = new ResponseMessage(404, "Incorrect value : "+ife.getValue()+", " +
                        "allow value are: "+Arrays.toString(ife.getTargetType().getEnumConstants()));
            }
            else{
                message = new ResponseMessage(404, ex.getMessage());
            }
        }
        else{
            message = new ResponseMessage(404, ex.getMessage());
        }

        return ResponseEntity.status(403).contentType(MediaType.APPLICATION_JSON).body(message);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseMessage> handleGeneralException(Exception ex){
        ResponseMessage message = new ResponseMessage(403, ex.getMessage());
        return ResponseEntity.status(403).contentType(MediaType.APPLICATION_JSON).body(message);
    }

}
