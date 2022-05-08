package com.blessing333.restapi.infra.repository;

import com.blessing333.restapi.domain.model.item.Item;
import com.blessing333.restapi.domain.model.order.Order;
import com.blessing333.restapi.domain.model.order.OrderItem;
import com.blessing333.restapi.domain.model.order.OrderStatus;
import com.blessing333.restapi.domain.model.order.OrderedItem;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JDBCOrderItemRepositoryTest {
    private final static UUID itemId = UUID.randomUUID();
    private final static UUID orderId = UUID.randomUUID();
    private final static UUID customerId = UUID.randomUUID();
    private final static UUID categoryId = UUID.randomUUID();

    @Autowired
    JDBCCustomerRepository customerRepository;
    @Autowired
    JDBCOrderItemRepository orderItemRepository;
    @Autowired
    JDBCOrderRepository orderRepository;
    @Autowired
    JDBCItemRepository itemRepository;
    @Autowired
    JDBCCategoryRepository categoryRepository;

    @Autowired
    TestDataManager dataManager;

    @BeforeEach
    void initData() {
        dataManager.insertDefaultDataToDB(categoryId,customerId,orderId,itemId,UUID.randomUUID());
    }

    @AfterEach
    void deleteAllData(){
        dataManager.deleteAllData();
    }

    @DisplayName("새로운 Orderitem을 추가할 수 있어야한다")
    @Test
    void saveOrderItem() {
        UUID orderItemId = UUID.randomUUID();
        OrderItem orderItem = new OrderItem(orderItemId, orderId, itemId, 50000, 100);

        Assertions.assertDoesNotThrow(() -> orderItemRepository.save(orderItem));
    }

    @DisplayName("특정 Order를 참조하는 OrderItem을 찾을 수 있어야 한다")
    @Test
    void findByOrderId(){
        UUID firstId = UUID.randomUUID();
        UUID secondId = UUID.randomUUID();
        UUID thirdId = UUID.randomUUID();
        OrderItem first = new OrderItem(firstId, orderId, itemId, 50000, 100);
        OrderItem second = new OrderItem(secondId, orderId, itemId, 5000, 10);
        OrderItem third = new OrderItem(thirdId, orderId, itemId, 500, 1);

        orderItemRepository.save(first);
        orderItemRepository.save(second);
        orderItemRepository.save(third);

        List<OrderItem> found = orderItemRepository.findByOrder(orderId);
        assertThat(found).hasSize(4).contains(first,second,third);
    }

    @DisplayName("OrderItem이 참조하는 Order가 삭제될 경우 OrderItem도 같이 삭제된다")
    @Test
    void deleteForeignKey() {
        UUID newOrderId = UUID.randomUUID();
        Order order = new Order(newOrderId,customerId,OrderStatus.ACCEPTED,LocalDateTime.now());
        orderRepository.save(order);
        OrderItem orderItem = new OrderItem(newOrderId, orderId, itemId, 50000, 100);
        orderItemRepository.save(orderItem);
        orderRepository.delete(newOrderId);

        List<OrderItem> found = orderItemRepository.findByOrder(newOrderId);
        assertThat(found).isEmpty();
    }

    @DisplayName("orderItem join with Item")
    @Test
    void joinTest(){
        orderItemRepository.deleteAll();
        UUID id = UUID.randomUUID();
        UUID secondId = UUID.randomUUID();
        UUID secondItemId = UUID.randomUUID();
        Item firstItem = itemRepository.findById(itemId);
        Item secondItem = Item.createNewItem(secondItemId,categoryId,"second","second",2000,2,LocalDateTime.now());
        itemRepository.save(secondItem);
        orderItemRepository.save(new OrderItem(id,orderId,itemId, firstItem.getPrice()*5,5));
        orderItemRepository.save(new OrderItem(secondId,orderId,secondItemId,secondItem.getPrice()*20,20));

        List<OrderedItem> withItem = orderItemRepository.findWithItemByOrderId(orderId);
        assertThat(withItem).hasSize(2);
        OrderedItem firstFound = withItem.get(0);
        assertThat(firstFound.getItemName()).isEqualTo(firstItem.getName());
        assertThat(firstFound.getItemPrice()).isEqualTo(firstItem.getPrice());
        assertThat(firstFound.getItemDescription()).isEqualTo(firstItem.getDescription());
        assertThat(firstFound.getTotalItemPrice()).isEqualTo(firstItem.getPrice()*5);
        assertThat(firstFound.getItemCount()).isEqualTo(5);
        OrderedItem secondFound = withItem.get(1);
        assertThat(secondFound.getItemName()).isEqualTo(secondItem.getName());
        assertThat(secondFound.getItemPrice()).isEqualTo(secondItem.getPrice());
        assertThat(secondFound.getItemDescription()).isEqualTo(secondItem.getDescription());
        assertThat(secondFound.getTotalItemPrice()).isEqualTo(secondItem.getPrice()*20);
        assertThat(secondFound.getItemCount()).isEqualTo(20);
    }
}