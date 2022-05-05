package com.blessing333.restapi.domain.model.order;

import com.blessing333.restapi.domain.application.commands.OrderItemCommand;
import com.blessing333.restapi.domain.model.order.exception.ItemNotFoundException;
import com.blessing333.restapi.domain.model.order.exception.OrderCreateFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderCreateManager {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;

    public Order createOrder(UUID buyerId, List<OrderItemCommand> items){
        UUID orderId = UUID.randomUUID();
        Order order = new Order(orderId,buyerId,OrderStatus.ACCEPTED, LocalDateTime.now());
        orderRepository.save(order);
        for(var itemCommand : items){
            createOrderItem(itemCommand,orderId);
        }
        return order;
    }

    private void createOrderItem(OrderItemCommand command,UUID orderId){
        try {
            UUID id = UUID.randomUUID();
            UUID itemId = command.getItemId();
            Item found = itemRepository.findById(itemId);
            orderItemRepository.save(new OrderItem(id, orderId, itemId, command.getItemCount() * found.getPrice(), command.getItemCount()));
        } catch (ItemNotFoundException e){
            throw new OrderCreateFailException(e);
        }
    }
}
