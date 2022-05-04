package com.blessing333.restapi.domain.application.impl;

import com.blessing333.restapi.domain.application.OrderService;
import com.blessing333.restapi.domain.application.commands.CreateOrderCommand;
import com.blessing333.restapi.domain.application.commands.OrderItemCommand;
import com.blessing333.restapi.domain.model.order.ItemNotFoundException;
import com.blessing333.restapi.infra.repository.TestDataManager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@Transactional
class OrderServiceImplTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private TestDataManager testDataManager;
    private final UUID itemId = UUID.randomUUID();
    private final UUID customerId = UUID.randomUUID();
    private final UUID orderId = UUID.randomUUID();
    private final UUID orderItemId = UUID.randomUUID();
    private final UUID categoryId = UUID.randomUUID();

    @BeforeEach
    void createDefaultData(){
        testDataManager.createDefaultData(categoryId,customerId,orderId,itemId,orderItemId);
    }

    @AfterEach
    void deleteAllData(){
        testDataManager.deleteAllData();
    }

    @DisplayName("존재하지 않는 아이템에 대해 주문을 시도하면 ItemNotFoundException을 발생시킨다.")
    @Test
    void createOrderWithInvalidItem() {
        UUID invalidItemId = UUID.randomUUID();
        CreateOrderCommand orderCommand = new CreateOrderCommand(customerId, List.of(new OrderItemCommand(invalidItemId,5)));
        Assertions.assertThrows(ItemNotFoundException.class,()->orderService.createOrder(orderCommand));
    }

//    @DisplayName("주문된 상품은 주문 수량만큼 재고가 감소되어야한다.")
}