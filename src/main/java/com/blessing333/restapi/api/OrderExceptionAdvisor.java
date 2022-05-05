package com.blessing333.restapi.api;

import com.blessing333.restapi.domain.model.order.exception.OrderCreateFailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class OrderExceptionAdvisor {
    @ExceptionHandler(OrderCreateFailException.class)
    public ResponseEntity<String> handleIllegalArgumentException(OrderCreateFailException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("order create fail");
    }
}
