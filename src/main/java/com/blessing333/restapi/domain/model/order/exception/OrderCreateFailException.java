package com.blessing333.restapi.domain.model.order.exception;

public class OrderCreateFailException extends RuntimeException{
    public OrderCreateFailException(Throwable cause) {
        super(cause);
    }
}
