package com.blessing333.restapi.domain.model.order.events;

import com.blessing333.restapi.domain.common.event.DomainEvent;
import com.blessing333.restapi.domain.model.order.Order;
import lombok.Getter;

@Getter
public class OrderCreateEvent extends DomainEvent {
    private final Order order;
    public OrderCreateEvent(Object source, Order order){
        super(source);
        this.order = order;
    }
}
