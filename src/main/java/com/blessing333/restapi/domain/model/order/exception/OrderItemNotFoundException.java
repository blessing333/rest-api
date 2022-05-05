package com.blessing333.restapi.domain.model.order.exception;

public class OrderItemNotFoundException extends RuntimeException{
    public OrderItemNotFoundException(Throwable cause) {
        super(cause);
    }
}
