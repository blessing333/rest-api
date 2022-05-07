package com.blessing333.restapi.domain.model.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
@Component
@RequiredArgsConstructor
public class OrderInquiryManager {
    private final OrderItemRepository orderItemRepository;

    public OrderDetail loadOrderWithItemData(UUID orderId){
        List<OrderedItem> orderedItems = orderItemRepository.findWithItemByOrderId(orderId);
        long orderPrice = 0;
        for (OrderedItem item : orderedItems) {
            orderPrice = item.getTotalItemPrice();
        }
        return new OrderDetail(orderId,orderedItems,orderPrice);
    }
}
