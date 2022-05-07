package com.blessing333.restapi.domain.model.order;

import com.blessing333.restapi.domain.application.commands.OrderItemCommand;
import com.blessing333.restapi.infra.repository.TestDataManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@Transactional
@Rollback
class OrderCreateManagerTest {
    @Autowired
    private OrderItemRepository orderitemRepository;
    @Autowired
    private OrderCreateManager orderCreateManager;
    @Autowired
    private TestDataManager dataManager;
    private final UUID itemId = UUID.randomUUID();
    private final UUID customerId = UUID.randomUUID();
    private final UUID orderId = UUID.randomUUID();
    private final UUID orderItemId = UUID.randomUUID();
    private final UUID categoryId = UUID.randomUUID();

    @BeforeEach
    void initData() {
        dataManager.insertDefaultDataToDB(categoryId,customerId,orderId,itemId,orderItemId);
    }

    @AfterEach
    void deleteAllData(){
        dataManager.deleteAllData();
    }

    @DisplayName("새로운 주문을 생성할 수 있어야한다.")
    @Test
    void createOrder() {
        List<OrderItemCommand> items = List.of(new OrderItemCommand(itemId, 4));

        Order order = orderCreateManager.createOrder(customerId, items);

        Assertions.assertThat(order).isNotNull();
    }

    @DisplayName("주문에 포함된 아이템 종류만큼 OrderItem이 생성되어야한다.")
    @Test
    void createOrderShouldCreateOrderItem() {
        UUID secondItemId = UUID.randomUUID();
        UUID thirdItemId = UUID.randomUUID();
        dataManager.insertNewItemToDB(secondItemId, categoryId);
        dataManager.insertNewItemToDB(thirdItemId, categoryId);
        List<OrderItemCommand> items = List.of(
                new OrderItemCommand(itemId, 4),
                new OrderItemCommand(secondItemId, 4),
                new OrderItemCommand(thirdItemId, 4)
        );

        Order order = orderCreateManager.createOrder(customerId, items);

        List<OrderItem> byOrder = orderitemRepository.findByOrder(order.getId());
        Assertions.assertThat(order).isNotNull();
        Assertions.assertThat(byOrder).hasSize(3);
    }
}