package com.blessing333.restapi.domain.application.impl;

import com.blessing333.restapi.domain.application.OrderService;
import com.blessing333.restapi.domain.application.commands.CreateOrderCommand;
import com.blessing333.restapi.domain.model.order.Order;
import com.blessing333.restapi.domain.model.order.OrderCreateManager;
import com.blessing333.restapi.domain.model.order.OrderDetail;
import com.blessing333.restapi.domain.model.order.OrderInquiryManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderCreateManager orderCreateManager;
    private final OrderInquiryManager orderInquiryManager;

    @Override
    public Order createOrder(CreateOrderCommand command) {
        return orderCreateManager.createOrder(command.getBuyerId(),command.getOrderItems());
        //TODO 주문하고나서 주문된 아이템의 수량이 감소해야한다.(이벤트 발행)
    }

    @Override
    public OrderDetail loadOrderData(UUID orderId) {
        //TODO 주문에 대해 조회할 권한이 있는지 인증 인가 기능 필요
        return orderInquiryManager.loadOrderWithItemData(orderId);
    }
}
