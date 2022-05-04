package com.blessing333.restapi.domain.model.order;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class OrderItem {
    private final UUID id;
    private final UUID orderId;
    private final UUID itemId;
    private final long orderPrice;
    private final int itemCount;

}
