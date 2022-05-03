package com.blessing333.restapi.domain.model.category;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(Throwable cause) {
        super(cause);
    }
}
