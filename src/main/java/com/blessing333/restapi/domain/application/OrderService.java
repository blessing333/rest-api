package com.blessing333.restapi.domain.application;

import com.blessing333.restapi.domain.application.commands.CreateOrderCommand;
import com.blessing333.restapi.domain.model.order.Order;

public interface OrderService {
    Order createOrder(CreateOrderCommand command);
}
