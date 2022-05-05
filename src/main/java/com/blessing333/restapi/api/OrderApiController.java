//package com.blessing333.restapi.api;
//
//import com.blessing333.restapi.api.payload.OrderPayload;
//import com.blessing333.restapi.domain.application.OrderService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//
//@Controller
//@RequiredArgsConstructor
//public class OrderApiController {
//    private final OrderService orderService;
//
//    @PostMapping("/api/v1/order")
//    ResponseEntity<String> createNewOrder(OrderPayload orderPayload){
//        orderService.createOrder()
//    }
//}
