package com.blessing333.restapi.domain.model.order;

import java.util.UUID;

public interface OrderRepository {
    UUID save(Order order);

    Order findById(UUID id);

    void delete(UUID id);

}
