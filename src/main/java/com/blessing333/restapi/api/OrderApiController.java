package com.blessing333.restapi.api;

import com.blessing333.restapi.api.response.OrderCreateResponse;
import com.blessing333.restapi.domain.application.OrderService;
import com.blessing333.restapi.domain.application.commands.CreateOrderCommand;
import com.blessing333.restapi.domain.model.order.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderApiController {
    private final OrderService orderService;

    @PostMapping("/api/v1/orders")
    ResponseEntity<OrderCreateResponse> createNewOrder(@RequestBody CreateOrderCommand orderCommand){
        Order order = orderService.createOrder(orderCommand);
        return ResponseEntity.ok(new OrderCreateResponse(order.getId().toString()));
    }
}
