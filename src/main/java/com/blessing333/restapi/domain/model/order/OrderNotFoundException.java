package com.blessing333.restapi.domain.model.order;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(Throwable cause) {
        super(cause);
    }
}
