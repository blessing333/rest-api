package com.blessing333.restapi.api.response;

import com.blessing333.restapi.domain.model.order.OrderDetail;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OrderResponse {
    private final OrderDetail orderDetail;
}
