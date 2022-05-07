package com.blessing333.restapi.domain.application;

import com.blessing333.restapi.domain.application.commands.CreateOrderCommand;
import com.blessing333.restapi.domain.model.order.Order;
import com.blessing333.restapi.domain.model.order.OrderDetail;

import java.util.UUID;

public interface OrderService {
    Order createOrder(CreateOrderCommand command);
    OrderDetail loadOrderData(UUID orderId);
}
