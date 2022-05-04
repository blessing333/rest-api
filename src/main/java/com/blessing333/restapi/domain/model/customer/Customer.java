package com.blessing333.restapi.domain.model.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class Customer {
    private final UUID id;
    private String name;
    private String email;
    private String address;
    private final LocalDateTime createAt;
}
