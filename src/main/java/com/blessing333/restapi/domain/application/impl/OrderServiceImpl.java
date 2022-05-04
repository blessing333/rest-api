package com.blessing333.restapi.domain.application.impl;

import com.blessing333.restapi.domain.application.OrderService;
import com.blessing333.restapi.domain.application.commands.CreateOrderCommand;
import com.blessing333.restapi.domain.model.order.Order;
import com.blessing333.restapi.domain.model.order.OrderCreateManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderCreateManager orderCreateManager;

    @Override
    public Order createOrder(CreateOrderCommand command) {
        Order createdOrder = orderCreateManager.createOrder(command.getBuyerId(),command.getOrderItems());

        //TODO 주문하고나서 주문된 아이템의 수량이 감소해야한다.(이벤트 발행)
        return createdOrder;
    }
}
