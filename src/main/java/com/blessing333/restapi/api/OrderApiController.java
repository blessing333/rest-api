package com.blessing333.restapi.api;

import com.blessing333.restapi.api.response.OrderCreateResponse;
import com.blessing333.restapi.api.response.OrderResponse;
import com.blessing333.restapi.domain.application.OrderService;
import com.blessing333.restapi.domain.application.commands.CreateOrderCommand;
import com.blessing333.restapi.domain.model.order.Order;
import com.blessing333.restapi.domain.model.order.OrderDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderApiController {
    private final OrderService orderService;

    @GetMapping("/api/v1/orders/{orderId}")
    ResponseEntity<OrderResponse> loadOrder(@PathVariable UUID orderId){
        OrderDetail orderDetail = orderService.loadOrderData(orderId);
        return ResponseEntity.ok(new OrderResponse(orderDetail));
    }

    @PostMapping("/api/v1/orders")
    ResponseEntity<OrderCreateResponse> createNewOrder(@RequestBody CreateOrderCommand orderCommand){
        Order order = orderService.createOrder(orderCommand);
        return ResponseEntity.ok(new OrderCreateResponse(order.getId().toString()));
    }
}
