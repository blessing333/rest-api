package com.blessing333.restapi.domain.model.order;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum OrderStatus {
    ACCEPTED,
    PAYMENT_CONFIRMED,
    READY_FOR_DELIVERY,
    SHIPPED,
    SETTLED,
    CANCELLED;

    private static final Map<String, OrderStatus> validOrderStatus = initValidOrderStatus();

    private static Map<String, OrderStatus> initValidOrderStatus() {
        return Collections.unmodifiableMap(
                Stream.of(OrderStatus.values())
                        .collect(Collectors.toMap(Object::toString,Function.identity()))
        );
    }

    public static OrderStatus fromString(String status){
        return validOrderStatus.get(status);
    }
}
