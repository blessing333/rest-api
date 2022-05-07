package com.blessing333.restapi.domain.model.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class OrderDetail {
    private final UUID orderId;
    private final List<OrderedItem> orderItems;
    private final long orderPrice;
}
