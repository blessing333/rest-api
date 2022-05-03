package com.blessing333.restapi.domain.model.order;

public class ItemNotFoundException extends RuntimeException{
    public ItemNotFoundException(Throwable cause) {
        super(cause);
    }
}
