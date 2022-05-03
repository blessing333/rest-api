package com.blessing333.restapi.domain.model.category;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Category {
    private final UUID id;
    private String name;

    public Category(UUID id, String name){
        if(name.equals(""))
            throw new IllegalArgumentException("name is blank");
        this.id = id;
        this.name = name;
    }

    public void changeName(String name){
        this.name = name;
    }
}
