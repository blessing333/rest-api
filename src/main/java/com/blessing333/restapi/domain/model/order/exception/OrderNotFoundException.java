package com.blessing333.restapi.domain.model.order.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(Throwable cause) {
        super(cause);
    }
}
