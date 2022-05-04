package com.blessing333.restapi.domain.model.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Order {
    private final UUID id;
    private final UUID buyerId;
    private OrderStatus orderStatus;
    private final LocalDateTime orderDate;
}
