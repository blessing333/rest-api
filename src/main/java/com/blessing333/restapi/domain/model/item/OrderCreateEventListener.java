package com.blessing333.restapi.domain.model.item;

import com.blessing333.restapi.domain.model.order.events.OrderCreateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderCreateEventListener {

    @EventListener(OrderCreateEvent.class)
    public void handleOrderCreate(OrderCreateEvent event){
        log.info("order create! reducing item quantity");
        //TODO: itemService.reduceItemQuantity();
    }
}
