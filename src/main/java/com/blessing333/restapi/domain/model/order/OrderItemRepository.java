package com.blessing333.restapi.domain.model.order;

import java.util.List;
import java.util.UUID;

public interface OrderItemRepository {
    UUID save(OrderItem orderItem);
    List<OrderItem> findByOrder(UUID orderId);
}
