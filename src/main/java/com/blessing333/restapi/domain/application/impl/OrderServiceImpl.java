package com.blessing333.restapi.domain.application.impl;

import com.blessing333.restapi.domain.application.OrderService;
import com.blessing333.restapi.domain.application.commands.CreateOrderCommand;
import com.blessing333.restapi.domain.common.event.DomainEventPublisher;
import com.blessing333.restapi.domain.model.order.Order;
import com.blessing333.restapi.domain.model.order.OrderCreateManager;
import com.blessing333.restapi.domain.model.order.OrderDetail;
import com.blessing333.restapi.domain.model.order.OrderInquiryManager;
import com.blessing333.restapi.domain.model.order.events.OrderCreateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderCreateManager orderCreateManager;
    private final OrderInquiryManager orderInquiryManager;
    private final DomainEventPublisher eventPublisher;

    @Transactional
    @Override
    public Order createOrder(CreateOrderCommand command) {
        Order order = orderCreateManager.createOrder(command.getBuyerId(), command.getOrderItems());
        eventPublisher.publish(new OrderCreateEvent(this,order));
        //TODO OrderCreate 이벤트 생성 시, Item : 재고 감소  , Customer : 포인트 적립
        return order;
    }

    @Override
    public OrderDetail loadOrderData(UUID orderId) {
        //TODO 주문에 대해 조회할 권한이 있는지 인증 기능 필요
        return orderInquiryManager.loadOrderWithItemData(orderId);
    }
}
